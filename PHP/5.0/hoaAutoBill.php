<?
#----------------------------------------------------------------
# Use HOA to create an AutoBill.
#
# Required values are values for merchantProductId and
# merchantBillingPlanId which must already exist in CashBox
# (validation is not performed, but the AutoBill->update method of
# the WebSession will fail).
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

hoaAutoBill();

function get_unique_value()
{
    $nowNewYork = new \DateTime( 'now',  new \DateTimeZone( 'America/New_York' ) );
    return $nowNewYork->format('Y-m-d_h_i_s');
}

function hoaAutoBill()
{
    # Set the data members from the arg values
    #
    $uniqueValue = get_unique_value();
    $merchantAutoBillId = 'ab-' . $uniqueValue;
    $merchantAccountId = 'account-' . $uniqueValue;
    $merchantPaymentMethodId = 'pm-' . $uniqueValue;
    $merchantProductId = 'Video';
    $merchantBillingPlanId = 'OneMonthSubOneMonthRecurring';
    $creditCardAccount = '5454541111111111';
    $paymentType = 'CreditCard';
    $cvn = '111';
    $exp = '201501';
    $email = get_unique_value() . '@nomail.com';
    $successUrl = 'http://good.com';
    $errorUrl = 'http://bad.com';
    $HOAmethod = 'AutoBill_Update';
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

    # Your ID for this AutoBill
    $ab_id = new NameValuePair();
    $ab_id->setName("vin_AutoBill_merchantAutoBillId");
    $ab_id->setValue($merchantAutoBillId);

    # Your ID for this user
    $acct_id = new NameValuePair();
    $acct_id->setName("vin_Account_merchantAccountId");
    $acct_id->setValue($merchantAccountId);

    # Permissible values for the Product that is going to be purchased
//    $prod_id = new NameValuePair();
//    $prod_id->setName("vin_Product_merchantProductId");
//    $prod_id->setValue($merchantProductId);

    # Permissible values for the Product that is going to be purchased
    $prod_id = new NameValuePair();
    $prod_id->setName("vin_AutoBill_items_0_Product_merchantProductId");
    $prod_id->setValue($merchantProductId);

    # Permissible values for BillingPlan to be used
    $plan_id = new NameValuePair();
    $plan_id->setName("vin_BillingPlan_merchantBillingPlanId");
    $plan_id->setValue($merchantBillingPlanId);

    # Your ID for this PaymentMethod
    $paym_id = new NameValuePair();
    $paym_id->setName("vin_PaymentMethod_merchantPaymentMethodId");
    $paym_id->setValue($merchantPaymentMethodId);

    $pmt_type = new NameValuePair();
    $pmt_type->setName("vin_PaymentMethod_type");
    $pmt_type->setValue($paymentType);

    # Add the PrivateFormValues to the WebSession
    $webSession->setPrivateFormValues(array($ab_id, $acct_id, $prod_id, $plan_id
        ,$paym_id
        ,$pmt_type
    ));

    #------------------------------------------------------------
    # Set any parameters specific for the Method we are
    # calling in the WebSession.
    #
    $validate = new NameValuePair();
    $validate->setName("AutoBill_Update_validatePaymentMethod");
//    $validate->setName("AutoBill_Update_validate");
    $validate->setValue("true");

    $minChargebackProbability = new NameValuePair();
    $minChargebackProbability->setName("AutoBill_Update_minChargebackProbability");
    // Value of 100 turns off fraud checking.
    $minChargebackProbability->setValue("100");

    $ignoreCvnPolicy = new NameValuePair();
    $ignoreCvnPolicy->setName("AutoBill_Update_ignoreCvnPolicy");
    $ignoreCvnPolicy->setValue("false");

    $ignoreAvsPolicy = new NameValuePair();
    $ignoreAvsPolicy->setName("AutoBill_Update_ignoreAvsPolicy");
    $ignoreAvsPolicy->setValue("false");

    $dryRun = new NameValuePair();
    $dryRun->setName("AutoBill_Update_dryRun");
    $dryRun->setValue("false");

    // AutoBill_Update takes in one more parameter - campaignCode
    // We will collect campaign code from the payment form

    $webSession->setMethodParamValues(array($validate,
        $minChargebackProbability, $ignoreCvnPolicy,
        $ignoreAvsPolicy, $dryRun));

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

    # TODO: Parameterize these from $_POST or $argv
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

    # Hidden fields in the checkout FORM
    #
    $post['vin_WebSession_vid'] = $vin_WebSession_vid;
    // If you have a Campaign Code form value...
    //$post['AutoBill_Update_campaignCode'] = 'XYZ';

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

    $webSession = new WebSession();
    $webSession->setVid($session_id);
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

    print('success');
    print($response['data']->session->apiReturn->soapId .
        " AutoBill >" . $merchantAutoBillId . "< created for Account >" .
        $merchantAccountId . "< using PaymentMethod >" .
        $merchantPaymentMethodId . "<  AuthCode->" .
        $response['data']->session->apiReturnValues->autoBillUpdate->
            authStatus->creditCardStatus->getAuthCode() . "<  AVS->" .
        $response['data']->session->apiReturnValues->autoBillUpdate->
            authStatus->creditCardStatus->getAvsCode() . "<  CVN->" .
        $response['data']->session->apiReturnValues->autoBillUpdate->
            authStatus->creditCardStatus->getCvnCode() . "<");
}
