<?php

function finalize_credit_card_AutoBill_then_transaction_auth_capture_Transaction_Items($websession_id)
{
    $webSession = new WebSession();
    $webSession->setVID($websession_id);
    $response = $webSession->finalize();

    if ( $response['returnCode'] != '200' )
    {
        print($response);
    }

    # Note, finalize almost always returns a 200 returnCode. The real
    # test for success of the underlying API call is inspection of
    # the apiReturn and apiReturnValues objects
    # Parse out the return object from the method call
    #
    $apiReturnValues = $response['data']->session->apiReturnValues;

    # Check the returnCode of the method called.
    # See Returns for update method of AutoBill object in the API
    # Reference for possible returnCodes.
    #
    if ( $response['data']->session->apiReturn->returnCode != "200" )
    {
        //408 - AutoBill creation failed: CVV check failed
        //407 - AutoBill creation failed: AVS Check Failed
        //409 - AutoBill creation failed: AVS and CVV Check Failed
        //410 - AutoBill creation failed: AVS and CVV check could not be performed
        //402 - AutoBill creation failed: Card authorization failed
        //400 - AutoBill creation failed
        print($apiReturnValues);
    }
    else {
        //Get info from autobill transaction for use processing remaining cart items
        $autobill = $response['data']->session->apiReturnValues->autoBillUpdate->autobill;
        $account = $autobill->account;
        $paymentMethod = $autobill->paymentMethod;

        $transaction = new Transaction();
        $transaction->setCurrency('USD');

        $transaction->setSourcePaymentMethod($paymentMethod);
        $transaction->setAccount($account);
        $transaction->setShippingAddress($account->shippingAddress);

        // loop through the cart on server side to add items.
        $transaction_lineitem1 = new TransactionItem();
        $transaction_lineitem1->setSku('club cover');
        $transaction_lineitem1->setName('club cover');
        $transaction_lineitem1->setPrice('4.99');
        $transaction_lineitem1->setQuantity('1');
        $transaction_lineitem2 = new TransactionItem();
        $transaction_lineitem2->setSku('shipping');
        $transaction_lineitem2->setName('shipping');
        $transaction_lineitem2->setPrice('5.00');
        $transaction_lineitem2->setQuantity('1');
        $transaction_lineitem2->setTaxClassification('NT');
        $lineitems = array($transaction_lineitem1, $transaction_lineitem2);
        $transaction->setTransactionItems($lineitems);

        $sendEmailNotification = false;
        $ignoreAvsPolicy = true;
        $ignoreCvnPolicy = true;
        $campaign = NULL;
        $dryrun = false;

        $response = $transaction->authCapture($sendEmailNotification, $ignoreAvsPolicy, $ignoreCvnPolicy, $campaign, $dryrun);

        if ( $response['returnCode'] != '200' )
        {
            print $response['data']->session->apiReturn->returnCode . PHP_EOL;
            print $response['data']->session->apiReturn->returnString . PHP_EOL;
        }

        else
        {
            print "returnCode=" . $response['data']->session->apiReturn->returnCode . PHP_EOL;
            print "returnString=" . $response['data']->session->apiReturn->returnString . PHP_EOL;

            if ( $response['data']->session->apiReturn->returnCode == "200" )
            {
                $returnTransaction = $response['data']->session->apiReturnValues->transactionAuthCapture->transaction;

                if($returnTransaction->statusLog[0]->status=='Authorized') {
                    print "Transaction approved\n";
                    print ("Transaction with id " . $returnTransaction->merchantTransactionId .
                        " was successfully captured");
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
            else if ($response['data']->session->apiReturn->returnCode="202") {
                print "Transaction cannot be processed due to taxes being temporarily unavailable\n";
            }
            else if ($response['data']->session->apiReturn->returnCode=="400") {
                print "Transaction cannot be processed due to data validation error\n";
            }
            else if ($response['data']->session->apiReturn->returnCode=="402") {
                print "Transaction cannot be processed due to transaction error\n";
            }
            else if ($response['data']->session->apiReturn->returnCode="409") {
                print "Transaction cannot be processed due to Failed AVS and CVN policy evaluation\n";
            }
            else if ($response['data']->session->apiReturn->returnCode="410") {
                print "Transaction cannot be processed due to not being able to perform AVS and CVN policy evaluation\n";
            }
            else {
                print "Error while making call to Vindicia CashBox\n";
            }
        }
    }
}

function finalize_paypal_AutoBill_then_transaction_auth_capture_Transaction_Items($vid)
{
    $autobill = new Autobill();
    $response = $autobill->finalizePayPalAuth($vid, true);
    if ($response['returnCode'] != '200') {
        print('Error finalizing autobill' . PHP_EOL);
        print 'Soap Id = ' . $response['data']->return->soapId . PHP_EOL;
        print 'Return Code = ' . $response['returnCode'] . PHP_EOL;
        print 'Return String = ' . $response['returnString'] . PHP_EOL;
    } else {
        // You can obtain the paypal payer email address from the return object if you desire to persist this.
        $response_object = $response['data'];
        $auth_status = $response_object->authStatus;
        $payPalEmail = $auth_status->payPalStatus->paypalEmail;
        print('Successfully paid for by ' . $payPalEmail);

        //Get info from autobill transaction for use processing remaining cart items
        $autobill = $response['data']->autobill;
        $account = $autobill->account;
        $paymentMethod = $autobill->paymentMethod;

        $transaction = new Transaction();
        $transaction->setCurrency('USD');

        $transaction->setSourcePaymentMethod($paymentMethod);
        $transaction->setAccount($account);
        $transaction->setShippingAddress($account->shippingAddress);

        // loop through the cart on server side to add items.
        $transaction_lineitem1 = new TransactionItem();
        $transaction_lineitem1->setSku('club cover');
        $transaction_lineitem1->setName('club cover');
        $transaction_lineitem1->setPrice('4.99');
        $transaction_lineitem1->setQuantity('1');
        $transaction_lineitem2 = new TransactionItem();
        $transaction_lineitem2->setSku('shipping');
        $transaction_lineitem2->setName('shipping');
        $transaction_lineitem2->setPrice('5.00');
        $transaction_lineitem2->setQuantity('1');
        $transaction_lineitem2->setTaxClassification('NT');
        $lineitems = array($transaction_lineitem1, $transaction_lineitem2);
        $transaction->setTransactionItems($lineitems);

        $billPayPalImmediately = new NameValuePair();
        $billPayPalImmediately->setName('vin:BillPayPalImmediately');
        $billPayPalImmediately->setValue('true');

        $autobillVID = new NameValuePair();
        $autobillVID->setName('vin:AutoBillVID');
        $autobillVID->setValue('none');

        $transaction->setNameValues(array($billPayPalImmediately, $autobillVID));

        $sendEmailNotification = false;
        $ignoreAvsPolicy = true;
        $ignoreCvnPolicy = true;
        $campaign = NULL;
        $dryrun = false;

        $response = $transaction->authCapture($sendEmailNotification, $ignoreAvsPolicy, $ignoreCvnPolicy, $campaign, $dryrun);

        if ($response['returnCode'] != '200') {
            print $response['data']->session->apiReturn->returnCode . PHP_EOL;
            print $response['data']->session->apiReturn->returnString . PHP_EOL;
        } else {
            print "returnCode=" . $response['data']->session->apiReturn->returnCode . PHP_EOL;
            print "returnString=" . $response['data']->session->apiReturn->returnString . PHP_EOL;

            if ($response['data']->session->apiReturn->returnCode == "200") {
                $returnTransaction = $response['data']->session->apiReturnValues->transactionAuthCapture->transaction;

                if ($returnTransaction->statusLog[0]->status == 'Authorized') {
                    print "Transaction approved\n";
                    print ("Transaction with id " . $returnTransaction->merchantTransactionId .
                        " was successfully captured");
                } else if ($returnTransaction->statusLog[0]->status == 'Cancelled') {
                    print "Transaction not approved \n";
                    print "Reason code is: ";
                    print $returnTransaction->statusLog[0]->creditCardStatus->authCode;
                    print "\n";
                } else {
                    print "Error: Unexpected transaction status\n";
                }
            } else if ($response['data']->session->apiReturn->returnCode = "202") {
                print "Transaction cannot be processed due to taxes being temporarily unavailable\n";
            } else if ($response['data']->session->apiReturn->returnCode == "400") {
                print "Transaction cannot be processed due to data validation error\n";
            } else if ($response['data']->session->apiReturn->returnCode == "402") {
                print "Transaction cannot be processed due to transaction error\n";
            } else if ($response['data']->session->apiReturn->returnCode = "409") {
                print "Transaction cannot be processed due to Failed AVS and CVN policy evaluation\n";
            } else if ($response['data']->session->apiReturn->returnCode = "410") {
                print "Transaction cannot be processed due to not being able to perform AVS and CVN policy evaluation\n";
            } else {
                print "Error while making call to Vindicia CashBox\n";
            }
        }
    }
}

function Combining_Subscription_Non_Subscription_Items()
{
    $autobill = new AutoBill();

    $productSubscription = new Product();
    $productSubscription->setMerchantProductId('Video Subscription'); //Must be defined in CashBox portal

    $productNonRecurring = new Product();
    $productNonRecurring->setMerchantProductId('Golf Clubs'); //Must be defined in CashBox portal

    $itemSubscription = new AutoBillItem();
    $itemSubscription->setIndex(0);
    $itemSubscription->setProduct($productSubscription);

    $itemNonRecurring = new AutoBillItem();
    $itemNonRecurring->setCycles(1);  //Only bill first time
    $itemNonRecurring->setIndex(1);
    $itemNonRecurring->setProduct($productNonRecurring);

    $autobill->setItems(array($itemSubscription, $itemNonRecurring));

    // Finish setting up autobill and then call autobill.update.
}