<?php

require_once("../../../API/50/Vindicia/Soap/Vindicia.php");
require_once("../../../API/50/Vindicia/Soap/Const.php");

hoaAccountUpdatePaymentMethod();

function get_unique_value()
{
    $nowNewYork = new \DateTime( 'now',  new \DateTimeZone( 'America/New_York' ) );
    return $nowNewYork->format('Y-m-d_h_i_s');
}

function hoaAccountUpdatePaymentMethod()
{
    $creditCardAccount = '5454541111111111';
    $paymentType = 'CreditCard';
    $cvn = '111';
    $exp = '201805';
    $email = get_unique_value() . '@nomail.com';
    $successUrl = 'http://good.com';
    $errorUrl = 'http://bad.com';
    $HOAmethod = 'Account_UpdatePaymentMethod';
    $HOAurl = 'https://secure.prodtest.sj.vindicia.com/vws';
    $HOAversion = '5.0';
    $ipAddress = '127.0.0.1';
    $name = 'John Vindicia';
    $addr1 = '303 Twin Dolphin Drive';
    $city = 'Redwood City';
    $district = 'CA';
    $postalCode = '94065';
    $country = 'US';

    # Create a new WebSession object
    $webSession = new WebSession();

    # Set the WebSession parameters
    $webSession->setReturnURL($successUrl);
    $webSession->setErrorURL($errorUrl);
    $webSession->setIpAddress($ipAddress);
    $webSession->setMethod($HOAmethod);
    $webSession->setVersion($HOAversion);

    $merchantAccountId = 'account-2015-02-10_02_55_50';
    $merchantPaymentMethodId = 'pm-2015-02-10_02_55_50';

// Step 2: start configuring the WebSession with the parameters we want to have

    $nvp1 = new NameValuePair();
    $nvp1->setName('vin_Account_merchantAccountId');
    $nvp1->setValue($merchantAccountId); // so that we can use the existing account

    $nvp2 = new NameValuePair();
    $nvp2->setName('vin_PaymentMethod_merchantPaymentMethodId');
    $nvp2->setValue($merchantPaymentMethodId);

    $nvp3 = new NameValuePair();
    $nvp3->setName('vin_PaymentMethod_type');
    $nvp3->setValue($paymentType);

    $webSession->setPrivateFormValues(array($nvp1, $nvp2, $nvp3
    ));

    $nvp7 = new NameValuePair();
    $nvp7->setName('Account_updatePaymentMethod_updateBehavior');
    $nvp7->setValue('CatchUp');

    $nvp8 = new NameValuePair();
    $nvp8->setName('Account_updatePaymentMethod_replaceOnAllAutoBills');
    $nvp8->setValue('true');

    $nvp9 = new NameValuePair();
    $nvp9->setName('Account_updatePaymentMethod_ignoreAvsPolicy');
    $nvp9->setValue('false');

    $nvp10 = new NameValuePair();
    $nvp10->setName('Account_updatePaymentMethod_ignoreCvnPolicy');
    $nvp10->setValue('false');

    $webSession->setMethodParamValues(array(
        $nvp7, $nvp8, $nvp9, $nvp10));

// now, create the session and generate it's session ID

    $response = $webSession->initialize();

    # Check to see that the initialize succeeded
    #
    if ($response['returnCode'] == 200) {
        # The VID of the WebSession object serves as session id
        #
        $vin_WebSession_vid = $response['data']->session->getVID();
    } else {
        print($response);
        return;
    }

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

    # Create the curl command line for exec by looping through the
    # $post array
    #
    $curlopts = "";
    foreach ($post as $name => $value) {
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
    if (php_sapi_name() == "cli") {
        $curlresp = implode("\n", $curlout);
    }
    #
    #------------------------------------------------------------

    # For CLI, use the WebSessionId we stored in the POST values
    # for curl. For everything else, retrieve the WebSessionId
    # from the URL query string on the redirect to the returnURL
    #
    if (php_sapi_name() == "cli") {
        $session_id = $post['vin_WebSession_vid'];
    } else {
        $session_id = $_GET['session_id'];
    }

    $webSession = new WebSession();
    $webSession->setVid($session_id);

    $response = $webSession->finalize();

    if ($response['returnCode'] != '200') {
        print $response['data']->session->apiReturn->returnCode . PHP_EOL;
        print $response['data']->session->apiReturn->returnString . PHP_EOL;
    }
    else {
        print $response['data']->session->apiReturn->returnCode . PHP_EOL;
        print $response['data']->session->apiReturn->returnString . PHP_EOL;
        if ($response['data']->session->apiReturn->returnCode == "200") {
            print PHP_EOL. 'Updated Credit Card.  Account=' . $merchantAccountId . ' PaymentMethod=' . $merchantPaymentMethodId . PHP_EOL;
        }
        else if ($response['data']->session->apiReturn->returnCode="261") {
            print "All active AutoBills were updated. AutoBills which are both expired and Suspended cannot be updated.\n";
        }
        else if ($response['data']->session->apiReturn->returnCode=="400") {
            print "One of the following:
• Invalid Payment Method Type. (You cannot change the Payment Method Type on an existing Payment Method.)
• No PaymentMethod specified in arguments.
• Data validation error Failed to create Payment-Type-Specific Payment Record: Credit Card conversion failed: Credit Card failed Luhn check.\n";
        }
        else if ($response['data']->session->apiReturn->returnCode=="402") {
            print "One of the following:
• PaymentMethod failed validation.
• Error attempting to authorize card.
• Unable to authorize card.\n";
        }
        else if ($response['data']->session->apiReturn->returnCode="404") {
            print "No match found error-description.
            Returned if CashBox cannot find an account that matches the input in the Vindicia database.\n";
        }
        else if ($response['data']->session->apiReturn->returnCode="407") {
            print "Transaction cannot be processed due to Failed AVS policy evaluation\n";
        }
        else if ($response['data']->session->apiReturn->returnCode="408") {
            print "Transaction cannot be processed due to Failed CVN policy evaluation\n";
        }
        else if ($response['data']->session->apiReturn->returnCode="409") {
            print "AutoBill creation failed: due to AVS and CVV Check Failed\n";
        }
        else if ($response['data']->session->apiReturn->returnCode="410") {
            print "AutoBill creation failed: due to AVS and CVV Check not being able to be performed\n";
        }
        else {
            print "Error while making call to Vindicia CashBox\n";
        }
    }
}