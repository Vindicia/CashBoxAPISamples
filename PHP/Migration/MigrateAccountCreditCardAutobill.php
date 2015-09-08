<?php

require_once("../../150/Vindicia/Soap/Vindicia.php");
require_once("../../150/Vindicia/Soap/Const.php");

$merchantAccountId = Step1CreateAccount();
Step3MigrateVideoMember($merchantAccountId);

function get_unique_value()
{
    $nowPT = new \DateTime( 'now',  new \DateTimeZone( 'America/Los_Angeles' ) );
    return $nowPT->format('Y-m-d_H_i_s');
}

function fail_if_merchant_transaction_id_too_long($merchantTransactionId)
{
    $transactionIdMaxLength = 21;
    $length = strlen($merchantTransactionId);
    if ($length > $transactionIdMaxLength) {
        print "Merchant Transaction Id too long.\n";
        return true;
    }
    return false;
}

function Step2CreateCreditCard($merchantAccountId)
{
//    $account = get_account_by_merchantAccountId($merchantAccountId);
    //To save a soap call, you can use sparse objects.
    $account = new Account();
    $account->merchantAccountId = $merchantAccountId;

    $merchantPaymentMethodId = $merchantAccountId;

    $paymentMethod = new PaymentMethod();
    $cc = new CreditCard();

    $paymentMethod->setBillingAddress($account->shippingAddress);
    $paymentMethod->setMerchantPaymentMethodId($merchantPaymentMethodId);

    // Use Test cards only in ProdTest.  Use Real cards only in Production.
    $cc->setAccount("4112344112344113");
    $cc->setExpirationDate("201811");
    $paymentMethod->setType('CreditCard');
    $paymentMethod->setCreditCard($cc);

    // Do not check AVS, CVN.  Do not validate.
    $replaceOnAutoBills = true;
    $updateBehavior = "Update";
    $ignoreAvsPolicy = true;
    $ignoreCvnPolicy = true;
    $srd = '';

    $response = $account->updatePaymentMethod($srd, $paymentMethod, $replaceOnAutoBills,
        $updateBehavior, $ignoreAvsPolicy, $ignoreCvnPolicy);
    // Log soap id for each API call.
//    $log->addDebug('Method = Account.updatePaymentMethod' . PHP_EOL);
//    $log->addDebug('Soap Id = ' . $response['data']->return->soapId . PHP_EOL);
//    $log->addDebug('Return Code = ' . $response['returnCode'] . PHP_EOL);
//    $log->addDebug('Return String = ' . $response['returnString'] . PHP_EOL);
    if ($response['returnCode'] == 200) {
        print "Call succeeded" . PHP_EOL;
    } else {
        print "Call failed" . PHP_EOL;
    }
}

function get_account_by_merchantAccountId($merchantAccountId)
{
    $account = new Account();
    $response = $account->fetchByMerchantAccountId($merchantAccountId);
    if ($response['returnCode'] == 200) {
        print "Call succeeded" . PHP_EOL;
    } else {
        print "Call failed" . PHP_EOL;
    }
    return $response['data']->account;
}

function Step1CreateAccount()
{
    $uniqueValue = get_unique_value();
    $email = $uniqueValue . '@gmail.com';
    $merchantAccountId = $uniqueValue;

    //Step 1
    CreateAccount($merchantAccountId, $email);

    //Step 2 (Vindicia Engineer generally does this if you do not have the credit cards on file)
    //Once the payment method is added to the account via Step 2,
    //you will be able to access the PaymentMethod array item 0.
    Step2CreateCreditCard($merchantAccountId);

    return $merchantAccountId;
}

function Step3MigrateVideoMember($merchantAccountId)
{
    $uniqueValue = $merchantAccountId;

    //Step 3
    //Assume that this subscription is for Video access for one year, and was paid in full 6 months ago.
    //As such it will be up for renewal in 6 months.
    //Generally, these dates are retrieved from your datastore.
    $sixmonthsagoPT = new \DateTime( '-6 months',  new \DateTimeZone( 'America/Los_Angeles' ) );
    $sixmonthsfromnowPT = new \DateTime( '6 months',  new \DateTimeZone( 'America/Los_Angeles' ) );
    $dateActiveSubscriptionPeriodPaidInFull = $sixmonthsagoPT->format(DateTime::ATOM);
    $dateActiveSubscriptionPeriodUpForRenewal = $sixmonthsfromnowPT->format(DateTime::ATOM);

    $billPlan = new BillingPlan();
    $product = new Product();

    // Generally, the billing plan and productid will be provided in your datastore.
    // These objects need to exist in CashBox prior to migration.
    $merchantBillingPlanId = 'OneYearSubOneYearRecurring';
    $merchantProductId = 'Video';
    $billPlan->setMerchantBillingPlanId($merchantBillingPlanId);
    $product->setMerchantProductId($merchantProductId);

    //To save a soap call, you can use sparse objects.
    $account = new Account();
    $account->merchantAccountId = $merchantAccountId;

    // Assuming merchantPaymentMethodId is the same value as the merchantAccountId.
    $merchantPaymentMethodId = $merchantAccountId;
    $paymentMethod = new PaymentMethod();
    $paymentMethod->merchantPaymentMethodId = $merchantPaymentMethodId;

    //If you don't have the address VID, then set migrationtransaction shippingaddress to null.
    $address = null;

//    // If you want to fetch the account by making a soap call to CashBox...
//    // This is a soap call to CashBox.
//    $fetchedAccount = get_account_by_merchantAccountId($merchantAccountId);
//
//    // Only use sparse object, so all data members are not over-written.
//    $account->merchantAccountId = $fetchedAccount->merchantAccountId;
//    $fetchedPaymentMethod = $account->paymentMethods[0];
//
//    // Only use sparse object, so all data members are not over-written.
//    $paymentMethod->merchantPaymentMethodId = $fetchedPaymentMethod->merchantPaymentMethodId;

    $paymentMethodType = 'CreditCard';
    $currency='USD';
    $lastPaidPrice='10.00';
    $useZeroUnlessYouKnowDifferent=0;
    $paymentProcessor='Paymentech';
    $transactionType='Recurring';
    $authCode='000';
    $capturedStatus='Captured';
    $txItemType='RecurringCharge';
    $txItemName= $merchantProductId . ':' . $merchantBillingPlanId;

    // This paymentProcessorTransactionId makes the transaction refundable if migrating from Litle (Vantiv) into Litle.
    $paymentProcessorTransactionId = 'RetrievedIdFromLegacyBillingPaymentProcessor';

    // Assume campaigns were not used when creating this Autobill.
    // Alternatively, consider specifying campaign on the AutoBillItem if necessary.
    $item = new AutoBillItem();
    $item->setIndex(0);
    $item->setAddedDate($dateActiveSubscriptionPeriodPaidInFull);
    $item->setMerchantAutoBillItemId(get_unique_value());
    $item->setProduct($product);

    $autobill = new AutoBill();

    // No need to set payment method on Autobill as it will be inherited from the Account.
    $autobill->setAccount($account);
    $autobill->setItems(array($item));
    $autobill->setBillingPlan($billPlan);
    $autobill->setCurrency($currency);
    $autobill->setCustomerAutoBillName($uniqueValue);
    $autobill->setMerchantAutoBillId($uniqueValue);
    $autobill->setStartTimestamp($dateActiveSubscriptionPeriodPaidInFull);

    // For this example, we assume one transaction item with a price equal to the entire migration transaction amount.
    $txItemA = new MigrationTransactionItem();
    $txItemA->setItemType($txItemType);
    $txItemA->setName($txItemName);
    $txItemA->setPrice($lastPaidPrice);
    $txItemA->setServicePeriodStartDate($dateActiveSubscriptionPeriodPaidInFull);
    $txItemA->setServicePeriodEndDate($dateActiveSubscriptionPeriodUpForRenewal);

    // It is recommended Sku equals the merchantProductId of the Product set on the Autobill.
    $txItemA->setSku($merchantProductId);

    $creditCardStatusA = new TransactionStatusCreditCard();
    $statusLogA = new TransactionStatus();

    // AuthCode is the code for a transaction successfully run through the payment processor.
    $creditCardStatusA->setAuthCode($authCode);
    $statusLogA->setCreditCardStatus($creditCardStatusA);
    $statusLogA->setPaymentMethodType($paymentMethodType);
    $statusLogA->setStatus($capturedStatus);
    $statusLogA->setTimestamp($dateActiveSubscriptionPeriodPaidInFull);

    $merchantTransactionId = $uniqueValue;
    fail_if_merchant_transaction_id_too_long($merchantTransactionId);

    // Assume sales tax calculated refunds not in scope in this example.
    // If they were, consider setting salesTaxAddress and tax migration transaction items.
    // Assume we are only migrating the last transaction used to pay for the Autobill.
    // If in scope, you have the option to migrate more than one.
    $migrationTransaction = new MigrationTransaction();
    $migrationTransaction->setAccount($account);

    // Set to 21 characters or less, or you will not be able to refund against this.
    $migrationTransaction->setMerchantTransactionId($merchantTransactionId);

    // Total migration transaction amount must equal sum of tx items.
    $migrationTransaction->setAmount($lastPaidPrice);

    // It is recommended that AutoBillCycle is set to the number of times the Autobill has renewed.
    // Make a best effort guess if exact number is unknown.
    // This will help with CashBox Reviews to determine metrics such as Lifetime Value (LTV).
    $migrationTransaction->setAutoBillCycle($useZeroUnlessYouKnowDifferent);
    $migrationTransaction->setBillingDate($dateActiveSubscriptionPeriodPaidInFull);

    // If the billing plan is modified, then the billing plan cycle resets to 0.
    // In general, if the billing has not been modified, this value will be the same as the AutoBill Cycle.
    $migrationTransaction->setBillingPlanCycle($useZeroUnlessYouKnowDifferent);
    $migrationTransaction->setCurrency($currency);
    $migrationTransaction->setPaymentProcessor($paymentProcessor);
    $migrationTransaction->setMerchantBillingPlanId($merchantBillingPlanId);
    $migrationTransaction->setMigrationTransactionItems(array($txItemA));
    $migrationTransaction->setPaymentMethod($paymentMethod);
    $migrationTransaction->setShippingAddress($address);
    $migrationTransaction->setStatusLog(array($statusLogA));
    $migrationTransaction->setType($transactionType);
    $migrationTransaction->setPaymentProcessorTransactionId($paymentProcessorTransactionId);

//    Division ID mapping:
//    123456 - Standard Subscription  [Default]
//    123457 - Premium Subscription
    // CashBox merchant configuration will be configured to route automatically based on a name value pair.
    // Confirm with your Deployment Consultant the values matching the merchant configuration.
    $divisionId = new NameValuePair();
    $divisionId->setName('vin:Division');
    $divisionId->setValue('Premium Subscription');
    $migrationTransaction->setNameValues(array($divisionId));
    //$divisionNumber='123456';
    //$migrationTransaction->setDivisionNumber($divisionNumber);

    $srd = '';
    $response = $autobill->migrate($srd, $dateActiveSubscriptionPeriodUpForRenewal,
        array($migrationTransaction));

    // Log soap id for each API call.
//    $log->addDebug('Method = Autobill.migrate' . PHP_EOL);
//    $log->addDebug('Soap Id = ' . $response['data']->return->soapId . PHP_EOL);
//    $log->addDebug('Return Code = ' . $response['returnCode'] . PHP_EOL);
//    $log->addDebug('Return String = ' . $response['returnString'] . PHP_EOL);
    if ($response['returnCode'] == 200) {
        print "Call succeeded" . PHP_EOL;
    } else {
        print "Call failed" . PHP_EOL;
    }
    return $response;
}

function CreateAccount($merchantAccountId, $email)
{
    $account = new Account();

    $account->setName('Migrated Customer');
    $account->setMerchantAccountId($merchantAccountId);

    // Be conscious that using real email addresses in ProdTest depending on configuration will
    // have live emails triggered and sent on billing events for the Account.
    // It is recommended that when testing in ProdTest be certain to mask real email addresses.
    $account->setEmailAddress($email);
    $account->setEmailTypePreference('html');
    $account->setWarnBeforeAutoBilling(true);

    $anyOtherHelpfulDataForCSRsWhenLookingUpAccount = new NameValuePair();
    $anyOtherHelpfulDataForCSRsWhenLookingUpAccount->setName('HelpfulData');
    $anyOtherHelpfulDataForCSRsWhenLookingUpAccount->setValue('BestCustomerEver');

    $account->setNameValues(array($anyOtherHelpfulDataForCSRsWhenLookingUpAccount));

    $address = new Address();
    $address->setAddr1('303 Twin Dolphin Drive');
    $address->setAddr2('Suite 200');
    $address->setCity('Redwood City');
    $address->setDistrict('CA');
    $address->setPostalCode('94065');
    $address->setCountry('US');
    $address->setPhone('123-456-7890');
    $srd = '';

    $account->setShippingAddress($address);
    $response = $account->update($srd);
    // Log soap id for each API call.
//    $log->addDebug('Method = Account.update' . PHP_EOL);
//    $log->addDebug('Soap Id = ' . $response['data']->return->soapId . PHP_EOL);
//    $log->addDebug('Return Code = ' . $response['returnCode'] . PHP_EOL);
//    $log->addDebug('Return String = ' . $response['returnString'] . PHP_EOL);
    if ($response['returnCode'] == 200) {
        print "Call succeeded" . PHP_EOL;
    } else {
        print "Call failed" . PHP_EOL;
        print_r($response);
    }
}
