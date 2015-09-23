<?php
//ini_set('include_path','/usr/local/Vindicia_php5_lib_9.0');
ini_set('include_path','../../../API/90');
ini_set('display_errors',1);

require_once("Vindicia/Soap/Vindicia.php");
require_once("Vindicia/Soap/Const.php");

prepayNonRecurringWithNonRecurringPrice('2015_09_23_09_35_57');

function get_current_active_nonrecurring_autobill($merchantAutobillId)
{
    $autoBill = new AutoBill();
    $response = $autoBill->fetchByMerchantAutoBillId($merchantAutobillId);
    logCall($response, 'Autobill::FetchByMerchantAutoBillId', $merchantAutobillId);
    if (($response['returnCode'] == 200)) {
        return $response['data']->autobill;
    }
}

function prepay($autobill)
{
    // Only use sparse object, so all data members are not over-written.
    $sparseAccount = new Account;
    $sparseAccount->merchantAccountId = $autobill->account->merchantAccountId;
    $fetchedPaymentMethod = $autobill->account->paymentMethods[0];

    // Only use sparse object, so all data members are not over-written.
    $sparsePaymentMethod = new PaymentMethod();
    $sparsePaymentMethod->merchantPaymentMethodId = $fetchedPaymentMethod->merchantPaymentMethodId;

    $transaction = new Transaction();
    $transaction->setCurrency('USD');

    $transaction->setSourcePaymentMethod($sparsePaymentMethod);
    $transaction->setAccount($sparseAccount);

    // loop through the cart on server side to add items.
    $transaction_lineitem1 = new TransactionItem();
    $transaction_lineitem1->setSku($autobill->items[0]->product->merchantProductId);
    $transaction_lineitem1->setName('PRE-PAY:' . $autobill->items[0]->product->descriptions[0]->description);
    $transaction_lineitem1->setPrice($autobill->items[0]->product->prices[0]->amount);
    $transaction_lineitem1->setQuantity('1');
    $lineitems = array($transaction_lineitem1);
    $transaction->setTransactionItems($lineitems);

    $sendEmailNotification = false;
    $ignoreAvsPolicy = true;
    $ignoreCvnPolicy = true;
    $campaign = NULL;
    $dryrun = false;

    $response = $transaction->authCapture($sendEmailNotification, $ignoreAvsPolicy, $ignoreCvnPolicy, $campaign, $dryrun);

    if ( $response['returnCode'] != '200' )
    {
        print $response['returnCode'] . PHP_EOL;
        print $response['returnString'] . PHP_EOL;
    }

    else
    {
        print "returnCode=" . $response['returnCode'] . PHP_EOL;
        print "returnString=" . $response['returnString'] . PHP_EOL;

        if ( $response['returnCode'] == "200" )
        {
            $returnTransaction = $response['data']->transaction;

            if($returnTransaction->statusLog[0]->status=='Authorized') {
                print "Transaction approved\n";
                print ("Transaction with id " . $returnTransaction->merchantTransactionId .
                    " was successfully captured");
                return $returnTransaction->merchantTransactionId;
            }
            else if($returnTransaction->statusLog[0]->status=='Cancelled') {
                print "Transaction not approved \n";
                print "Reason code is: ";
                print $returnTransaction->statusLog[0]->creditCardStatus->authCode;
                print "\n";
            }
            else {
                print "Error: Unexpected transaction status\n";
            }
        }
        else if ($response['returnCode']=="202") {
            print "Transaction cannot be processed due to taxes being temporarily unavailable\n";
        }
        else if ($response['returnCode']=="400") {
            print "Transaction cannot be processed due to data validation error\n";
        }
        else if ($response['returnCode']=="402") {
            print "Transaction cannot be processed due to transaction error\n";
        }
        else if ($response['returnCode']=="409") {
            print "Transaction cannot be processed due to Failed AVS and CVN policy evaluation\n";
        }
        else if ($response['returnCode']=="410") {
            print "Transaction cannot be processed due to not being able to perform AVS and CVN policy evaluation\n";
        }
        else {
            print "Error while making call to Vindicia CashBox\n";
        }
    }
}


function prepayNonRecurringWithNonRecurringPrice($merchantAutobillId)
{
// Fetch the current active non-recurring autobill by fetching all autobills by merchantAccountId.
// Pick the current active non-recurring autobill for which they are pre-paying.
    $currentActiveNonRecurringAutobill = get_current_active_nonrecurring_autobill($merchantAutobillId);

    // Pre-pay capturing a transaction for price of product on current active non-recurring autobill.
    $merchantTransactionId = prepay($currentActiveNonRecurringAutobill);

    $startTimestamp = $currentActiveNonRecurringAutobill->endTimestamp;
    $merchantAutoBillId = $currentActiveNonRecurringAutobill->merchantAutoBillId . '+';
    $merchantAccountId = $currentActiveNonRecurringAutobill->account->merchantAccountId;

    // Only use sparse object, so all data members are not over-written.
    $sparseAccount = new Account;
    $sparseAccount->merchantAccountId = $currentActiveNonRecurringAutobill->account->merchantAccountId;

// Assume same billing plan as on current autobill.
    $billingPlan = $currentActiveNonRecurringAutobill->billingPlan;
// Assume same product as on current autobill.
    $product = $currentActiveNonRecurringAutobill->items[0]->product;

    $itemNonRecurring = new AutoBillItem();
    $itemNonRecurring->setIndex(1);
// Override price of product to 0 USD, since payment is taken up front as one-time transaction.
    $itemNonRecurring->setAmount(0);
    $itemNonRecurring->setProduct($product);

    $autobill = new AutoBill();
    $autobill->setMerchantAutoBillId($merchantAutoBillId);
    $autobill->setAccount($sparseAccount);
    $autobill->setItems(array($itemNonRecurring));
    $autobill->setBillingPlan($billingPlan);
// Future dated autobill will start when the current active autobill ends.
// Price is overrided as 0 USD, so no transaction will occur and the user will be entitled for the next non-recurring cycle.
    $autobill->setStartTimestamp($startTimestamp);
    $autobill->setWarnOnExpiration(true);
    $autobill->setCurrency('USD');

// Add a name value pair to signify a linkage to the previous Autobill.
// Add a name value pair to signify a linkage to the Transaction used to collect payment.
// This will help out with revenue recognition.
    $nameVals[0] = new NameValuePair();
    $nameVals[0]->setName("PreviousNonRecurringAutobillId");
    $nameVals[0]->setValue($currentActiveNonRecurringAutobill->merchantAutoBillId);
    $nameVals[1] = new NameValuePair();
    $nameVals[1]->setName("PaidForByMerchantTransactionId");
    $nameVals[1]->setValue($merchantTransactionId);

    $autobill->setNameValues($nameVals);

    $immediateAuthFailurePolicy = 'doNotSaveAutoBill';
    $validateForFuturePayment = false;
    $minChargebackProbability = 100;
    $ignoreAvsPolicy = true;
    $ignoreCvnPolicy = true;
    $campaignCode = null;
    $dryrun = false;

    $response = $autobill->update($immediateAuthFailurePolicy, $validateForFuturePayment,
        $minChargebackProbability, $ignoreAvsPolicy, $ignoreCvnPolicy, $campaignCode, $dryrun);

    logCall($response, 'Autobill::Update', $merchantAccountId);
}

function logCall($response, $method, $comment)
{
    if (($response['returnCode'] != 200)) {
        print 'Fail: ' . $comment . ' > ' . $method . ' > ' . $response['returnString'] . PHP_EOL;
    }
    else
    {
        print 'Success: ' . $comment . ' > '  . $method . PHP_EOL;
    }
    print PHP_EOL;
}

