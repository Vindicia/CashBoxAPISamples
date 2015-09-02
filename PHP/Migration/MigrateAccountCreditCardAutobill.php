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

function Step2CreateDummyCreditCard($merchantAccountId)
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
    Step2CreateDummyCreditCard($merchantAccountId);

    return $merchantAccountId;
}

function Step3MigrateVideoMember($merchantAccountId)
{
    $uniqueValue = $merchantAccountId;

    //Step 3
    //Assume that this subscription is for Video access for one year, and was paid in full 6 months ago.
    //As such it will be up for renewal in 6 months.
    $sixmonthsagoPT = new \DateTime( '-6 months',  new \DateTimeZone( 'America/Los_Angeles' ) );
    $sixmonthsfromnowPT = new \DateTime( '6 months',  new \DateTimeZone( 'America/Los_Angeles' ) );
    $dateActiveSubscriptionPeriodPaidInFull = $sixmonthsagoPT->format(DateTime::ATOM);
    $dateActiveSubscriptionPeriodUpForRenewal = $sixmonthsfromnowPT->format(DateTime::ATOM);

    $billPlan = new BillingPlan();
    $product = new Product();
    // Choose recurring or non-recurring, priced for rebill amount or at 0 USD for non-recurring.
    $merchantProductId = 'VideoOneYearForFree';
    $merchantBillingPlanId = 'OneYearSubFixedPeriodOneCycleNonRecurring';
//    $merchantBillingPlanId = 'OneMonthSubOneMonthRecurring';
//    $merchantBillingPlanId = 'OneYearSubOneYearRecurring';
//    $merchantProductId = 'Video';
    $billPlan->setMerchantBillingPlanId($merchantBillingPlanId);
    $product->setMerchantProductId($merchantProductId);

    //To save a soap call, you can use sparse objects.
    $account = new Account();
    $account->merchantAccountId = $merchantAccountId;
    $paymentMethod = new PaymentMethod();
    $merchantPaymentMethodId = $merchantAccountId;
    $paymentMethod->merchantPaymentMethodId = $merchantPaymentMethodId;
    $address = null; //If you don't have the address VID, then set migrationtransaction shippingaddress to null.

//    $account = get_account($email);  //This is a soap call to CashBox.
//    $paymentMethod = $account->paymentMethods[0];
//    $address = $paymentMethod->billingAddress;

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

    $item = new AutoBillItem();
    $item->setIndex(0);
    $item->setAddedDate($dateActiveSubscriptionPeriodPaidInFull);
    $item->setMerchantAutoBillItemId(get_unique_value());
    $item->setProduct($product);

    $autobill = new AutoBill();
    $autobill->setAccount($account);
    $autobill->setItems(array($item));
    $autobill->setBillingPlan($billPlan);
    $autobill->setCurrency($currency);
    $autobill->setCustomerAutoBillName($uniqueValue);
    $autobill->setMerchantAutoBillId($uniqueValue);
    $autobill->setStartTimestamp($dateActiveSubscriptionPeriodPaidInFull);

    $txItemA = new MigrationTransactionItem();
    $txItemA->setItemType($txItemType);
    $txItemA->setName($txItemName);
    $txItemA->setPrice($lastPaidPrice);
    $txItemA->setServicePeriodStartDate($dateActiveSubscriptionPeriodPaidInFull);
    $txItemA->setServicePeriodEndDate($dateActiveSubscriptionPeriodUpForRenewal);
    $txItemA->setSku($merchantProductId);

    $creditCardStatusA = new TransactionStatusCreditCard();
    $statusLogA = new TransactionStatus();
    $creditCardStatusA->setAuthCode($authCode);
    $statusLogA->setCreditCardStatus($creditCardStatusA);
    $statusLogA->setPaymentMethodType($paymentMethodType);
    $statusLogA->setStatus($capturedStatus);
    $statusLogA->setTimestamp($dateActiveSubscriptionPeriodPaidInFull);

    $merchantTransactionId = $uniqueValue;
    fail_if_merchant_transaction_id_too_long($merchantTransactionId);
    $migrationTransaction = new MigrationTransaction();
    $migrationTransaction->setAccount($account);
    $migrationTransaction->setMerchantTransactionId($merchantTransactionId);  //Set to 21 characters or less, or you will not be able to refund against this.
    $migrationTransaction->setAmount($lastPaidPrice);  //Total migration transaction amount must equal sum of tx items.
    $migrationTransaction->setAutoBillCycle($useZeroUnlessYouKnowDifferent);
    $migrationTransaction->setBillingDate($dateActiveSubscriptionPeriodPaidInFull);
    $migrationTransaction->setBillingPlanCycle($useZeroUnlessYouKnowDifferent);
    $migrationTransaction->setCurrency($currency);
    $migrationTransaction->setPaymentProcessor($paymentProcessor);
    $migrationTransaction->setMerchantBillingPlanId($merchantBillingPlanId);
    $migrationTransaction->setMigrationTransactionItems(array($txItemA));
    $migrationTransaction->setPaymentMethod($paymentMethod);
    $migrationTransaction->setRetryNumber($useZeroUnlessYouKnowDifferent);
    $migrationTransaction->setShippingAddress($address);
    $migrationTransaction->setStatusLog(array($statusLogA));
    $migrationTransaction->setType($transactionType);

//    Division ID mapping:
//    123456 - Standard Subscription  [Default]
//    123457 - Premium Subscription
    //CashBox will be configured to route automatically based on a name value pair.
    //$divisionNumber='123456';
    $divisionId = new NameValuePair();
    $divisionId->setName('vin:Division');
    $divisionId->setValue('Premium Subscription');
    $migrationTransaction->setNameValues(array($divisionId));
    //$migrationTransaction->setDivisionNumber($divisionNumber);

    $srd = '';
    $response = $autobill->migrate($srd, $dateActiveSubscriptionPeriodUpForRenewal,
        array($migrationTransaction));
    if ($response['returnCode'] == 200) {
        print "Call succeeded" . PHP_EOL;
    } else {
        print "Call failed" . PHP_EOL;
        print_r($response);
    }
    return $response;
}

function CreateAccount($merchantAccountId, $email)
{
    $account = new Account();

    $account->setName('Migrated Customer');
    $account->setMerchantAccountId($merchantAccountId);
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
    if ($response['returnCode'] == 200) {
        print "Call succeeded" . PHP_EOL;
    } else {
        print "Call failed" . PHP_EOL;
        print_r($response);
    }
}
