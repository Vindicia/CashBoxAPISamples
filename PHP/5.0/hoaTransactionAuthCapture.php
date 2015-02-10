<?
#----------------------------------------------------------------
# Use HOA to create a Transaction.
#
#
# Use Account with merchantAccountId if it exists or create it if it
# does not exist.
#
# Use PaymentMethod with merchantPaymentMethodId if it exists or
# create if it does not exist.
#
# A by product of this approach is that the PaymentMethod object
# automatically gets associated with the Account object.
#

// Include the Vindicia library
require_once("../../../API/50/Vindicia/Soap/Vindicia.php");
require_once("../../../API/50/Vindicia/Soap/Const.php");

hoaTransactionAuthCapture();

function get_unique_value()
{
    $nowNewYork = new \DateTime( 'now',  new \DateTimeZone( 'America/New_York' ) );
    return $nowNewYork->format('Y-m-d_h_i_s');
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

function hoaTransactionAuthCapture()
{
    $uniqueValue = get_unique_value();
    $merchantAccountId = 'account-' . $uniqueValue;
    $merchantTransactionId = 't-' . $uniqueValue;
    if (fail_if_merchant_transaction_id_too_long($merchantTransactionId))
    {
        return;
    }

    $merchantPaymentMethodId = 'pm-' . $uniqueValue;
    $campaign = '';

    $creditCardAccount = '5454541111111111';
    $paymentType = 'CreditCard';
    $cvn = '111';
    $exp = '201801';
    $email = get_unique_value() . '@nomail.com';
    $successUrl = 'http://good.com';
    $errorUrl = 'http://bad.com';
    $HOAmethod = 'Transaction_AuthCapture';
    $HOAurl = 'https://secure.prodtest.sj.vindicia.com/vws';
    $HOAversion = '5.0';
    $ipAddress = '127.0.0.1';
    $name = 'John Vindicia';
    $addr1 = '303 Twin Dolphin Drive';
    $city = 'Redwood City';
    $district = 'CA';
    $postalCode = '94065';
    $country = 'US';

    #------------------------------------------------------------
    #-Step 1-
    #-Step 1- Initialize the WebSession before the PaymentMethod
    #-Step 1- form is displayed to the user
    #-Step 1-
    #

    # Create a new WebSession object
    $webSession = new WebSession();

    # Set the WebSession parameters
    $webSession->setReturnURL($successUrl);
    $webSession->setErrorURL($errorUrl);
    $webSession->setIpAddress($ipAddress);
    $webSession->setMethod($HOAmethod);
    $webSession->setVersion($HOAversion);

    #------------------------------------------------------------
    # Set PrivateFormValues. These are hidden fields in the POST
    # that we want to protect from hacking. If the value in the
    # POST does not match the value set during initialization,
    # the WebSession.finalize will fail

//    $account_VID = $account->VID;
//
//    $nameVals[0] = new NameValuePair();
//    $nameVals[0]->setName('Account_VID');
//    $nameVals[0]->setValue($account_VID); // so that we can use the existing account

    $tx_id = new NameValuePair();
    $tx_id->setName('vin_Transaction_merchantTransactionId');
    $tx_id->setValue($merchantTransactionId); // so that we can use the existing account

    # Your ID for this user
    $acct_id = new NameValuePair();
    $acct_id->setName("vin_Account_merchantAccountId");
    $acct_id->setValue($merchantAccountId);

    # Your ID for this PaymentMethod
    $paym_id = new NameValuePair();
    $paym_id->setName("vin_PaymentMethod_merchantPaymentMethodId");
    $paym_id->setValue($merchantPaymentMethodId);

    $pmt_type = new NameValuePair();
    $pmt_type->setName("vin_PaymentMethod_type");
    $pmt_type->setValue($paymentType);

    # Add the PrivateFormValues to the WebSession
    $webSession->setPrivateFormValues(array($tx_id, $acct_id
    ,$paym_id
    ,$pmt_type
    ));

    #------------------------------------------------------------
    # Set any parameters specific for the Method we are
    # calling in the WebSession.
    #
    $ignoreCvnPolicy = new NameValuePair();
    $ignoreCvnPolicy->setName("Transaction_AuthCapture_ignoreCvnPolicy");
    $ignoreCvnPolicy->setValue("false");

    $ignoreAvsPolicy = new NameValuePair();
    $ignoreAvsPolicy->setName("Transaction_AuthCapture_ignoreAvsPolicy");
    $ignoreAvsPolicy->setValue("false");

    $dryRun = new NameValuePair();
    $dryRun->setName("Transaction_AuthCapture_dryRun");
    $dryRun->setValue("false");

    $sendEmailNotification = new NameValuePair();
    $sendEmailNotification->setName("Transaction_AuthCapture_sendEmailNotification");
    $sendEmailNotification->setValue("false");

    // Transaction_AuthCapture takes in one more parameter - campaignCode
//    $campaignCodeNVP = new NameValuePair();
//    $campaignCodeNVP->setName("AutoBill_Update_campaignCode");
//    $campaignCodeNVP->setValue($campaign);

    $webSession->setMethodParamValues(array(
        $sendEmailNotification, $ignoreCvnPolicy, $ignoreAvsPolicy,
        $dryRun
//        , $campaignCodeNVP
    ));

    # Initialize the WebSession
    #
    $response = $webSession->initialize();

    # Check to see that the initialize succeeded
    #
    if ( $response['returnCode'] == 200 )
    {
        # The VID of the WebSession object serves as session id
        #
        $vin_WebSession_vid = $response['data']->session->getVID();
    }
    else
    {
        print($response);
        return;
    }

    #------------------------------------------------------------
    #-Step 2-
    #-Step 2- This is the payment method FORM and the HOA POST
    #-Step 2-

    # Fields on the checkout FORM
    # User supplied input
//    $post['vin_PaymentMethod_merchantPaymentMethodId'] =
//                $merchantPaymentMethodId;
    $post['vin_PaymentMethod_accountHolderName'] =
    $post['vin_PaymentMethod_billingAddress_name'] =
        $name;
    $post['vin_PaymentMethod_billingAddress_addr1'] =
        $addr1;
    $post['vin_PaymentMethod_billingAddress_city'] =
        $city;
    $post['vin_PaymentMethod_billingAddress_district'] =
        $district;
    $post['vin_PaymentMethod_billingAddress_postalCode'] =
        $postalCode;
    $post['vin_PaymentMethod_billingAddress_country'] =
        $country;
    $post['vin_Account_emailAddress'] = $email;
    $post['vin_PaymentMethod_creditCard_account'] =
        $creditCardAccount;
    $post['vin_PaymentMethod_creditCard_expirationDate'] =
        $exp;
    $post['vin_PaymentMethod_nameValues_cvn'] =
        $cvn;

    $post['vin_Transaction_transactionItems_0_sku'] = 'Item 1';
    $post['vin_Transaction_transactionItems_0_name'] = 'Item 1 Description';
    $post['vin_Transaction_transactionItems_0_price'] = '99';
    $post['vin_Transaction_transactionItems_0_quantity'] = '1';

    # Hidden fields in the checkout FORM
    #
    $post['vin_WebSession_vid'] = $vin_WebSession_vid;

    # Copy the BillingAddress to the ShippingAddress to improve
    # Chargeback dispute success. Visa will deny disputed Chargeback
    # for many reasons. A missing ShippingAddress, even though there
    # is nothing being shipped, is commonly one of those reasons.
    # This can be done with JavaScript on the checkout form.
    #
    $post['vin_Account_name'] =
        $post['vin_PaymentMethod_billingAddress_name'];
    $post['vin_Account_shippingAddress_name'] =
        $post['vin_PaymentMethod_billingAddress_name'];
    $post['vin_Account_shippingAddress_addr1'] =
        $post['vin_PaymentMethod_billingAddress_addr1'];
    $post['vin_Account_shippingAddress_city'] =
        $post['vin_PaymentMethod_billingAddress_city'];
    $post['vin_Account_shippingAddress_district'] =
        $post['vin_PaymentMethod_billingAddress_district'];
    $post['vin_Account_shippingAddress_county'] =
        $post['vin_PaymentMethod_billingAddress_county'];
    $post['vin_Account_shippingAddress_postalCode'] =
        $post['vin_PaymentMethod_billingAddress_postalCode'];
    $post['vin_Account_shippingAddress_country'] =
        $post['vin_PaymentMethod_billingAddress_country'];
    $post['vin_Account_shippingAddress_phone'] =
        $post['vin_PaymentMethod_billingAddress_phone'];

    // If you have a Campaign Code form value...
    //$post['Transaction_AuthCapture_campaignCode'] = $campaign;

    # Create the curl command line for exec by looping through the
    # $post array
    #
    $curlopts = "";
    foreach ( $post as $name => $value )
    {
        $curlopts .= " --data-urlencode $name=\"$value\"";
    }

    # Do the POST
    #
    exec("curl -s $curlopts " . $HOAurl,
        $curlout, $curlret);

    #-Step 3-----------------------------------------------------
    #-Step 3-
    #-Step 3- This code should be on the returnURL page
    #-Step 3-
    #-Step 3- Nothing has been committed until the WebSession gets
    #-Step 3- finalized. This is done in the returnURL page code. For
    #-Step 3- example, the returnURL is a confirmation page and when
    #-Step 3- the user clicks a confirmation button the form action
    #-Step 3- is a page that performs all the actual finalize steps.
    #-Step 3-


    #------------------------------------------------------------
    # This is only necessary for this CLI implementation.
    #
    # Flatten the output from exec so we can search it. The response
    # from a successful HOA POST should be a 302 page that contains
    # our returnURL with the WebSessionVID as the query string.
    #
    if ( php_sapi_name() == "cli" )
    {
        $curlresp = implode("\n", $curlout);
    }
    #
    #------------------------------------------------------------

    # For CLI, use the WebSessionId we stored in the POST values
    # for curl. For everything else, retrieve the WebSessionId
    # from the URL query string on the redirect to the returnURL
    #
    if ( php_sapi_name() == "cli" )
    {
        $session_id = $post['vin_WebSession_vid'];
    }
    else
    {
        $session_id = $_GET['session_id'];
    }

    $campaignCode = $post['Transaction_AuthCapture_campaignCode'];

    if ($campaignCode != null)
    {
        $fetchedWebSession = new WebSession();
        $response = $fetchedWebSession->fetchByVid($session_id);
        $response_object = $response['data'];
        $return_code = $response['returnCode'];
        $websession = $response_object->session;
        if (($return_code!="200") || ($websession->apiReturn->returnCode != "200"))
        {
            print($response);
        }
    }

    $webSession = new WebSession();
    $webSession->setVid($session_id);
    if ($campaignCode != null)
    {
        $campaignCodeNVP = new NameValuePair();
        $campaignCodeNVP->setName("Transaction_AuthCapture_campaignCode");
        $campaignCodeNVP->setValue($campaignCode);

        $webSession->setMethodParamValues(array($campaignCodeNVP));
    }

    $response = $webSession->finalize();

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
