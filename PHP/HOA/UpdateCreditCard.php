<?php
//Example Method for WebSession Initialize
$app->get('/wsinitializeaccountupdatepaymentmethod', function(Request $request) use($app) {
    $merchantAccountId = $request->query->get('vin_Account_merchantAccountId');
    $merchantPaymentMethodId = $request->query->get('vin_PaymentMethod_merchantPaymentMethodId');
//if we assume only using one payment method, perhaps payment method id is merchant account id
    //$merchantPaymentMethodId = $merchantAccountId;
    $paymentType = 'CreditCard';
    $successUrl = 'ajax_success';
    $errorUrl = 'ajax_fail';
    $HOAmethod = 'Account_UpdatePaymentMethod';
    $HOAversion = '12.0';
    $ipAddress = '127.0.0.1';

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
    $webSession->setPrivateFormValues(array(
        $acct_id,
        $paym_id,
        $pmt_type
    ));
#------------------------------------------------------------
    # Set any parameters specific for the Method we are
    # calling in the WebSession.
    #
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

    $response = $webSession->initialize();

    if ( $response['returnCode'] == 200 )
    {
        $sessionId = $response['data']->session->getVID();
        return $sessionId;
    }
// Add error checking and logging of soap ids
});

//Example Method for WebSession Finalize
$app->get('/wsfinalizeaccountupdatepaymentmethod', function(Request $request) use($app) {
    $webSession = new WebSession();
    $websession_id = $request->query->get('vin_WebSession_VID');
    $webSession->setVID($websession_id);
    $response = $webSession->finalize();

// Add error checking and logging of soap ids
    // Check all the response codes, log soap id.
    $paymentMethodVid = $response['data']->session->apiReturnValues->accountUpdatePaymentMethod->account->paymentMethods[0]->VID;
    return $paymentMethodVid;
});
?> 