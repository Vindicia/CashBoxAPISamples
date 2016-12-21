<pre>
<?php

#-----------------------------------------------------------------------------------
#
#	HOA Account_UpdatePaymentMethod sample
#
#		This sample demonstrates Updating a Credit Card on file using HOA.
#
#		This file is called by UC-003_UpdateCreditCard.php which includes
#		the full Use Case flow, with additional API calls from SOAP.
#
#		To run this sample as a single standalone file to see HOA execution only,
#		simply uncomment the call to hoaAccountUpdatePaymentMethod() below.
#
#-----------------------------------------------------------------------------------

ini_set('include_path','/usr/local/Vindicia_php5_lib_9.0');
//ini_set('include_path','../../../API/50');
ini_set('display_errors',1);

require_once("Vindicia/Soap/Vindicia.php");
require_once("Vindicia/Soap/Const.php");

# uncomment for single file execution:
# hoaAccountUpdatePaymentMethod();

function get_unique_value()
{
	$nowNewYork = new \DateTime( 'now', new \DateTimeZone( 'America/New_York' ) );
	return $nowNewYork->format('Y-m-d_h_i_s');
}

function hoaAccountUpdatePaymentMethod(
	$merchantAccountId = null,
	$merchantPaymentMethodId = null )
{
	$creditCardAccount = '5454541111111111';
	$paymentType = 'CreditCard';
	$cvn = '111';
	$exp = '201805';
	$email = get_unique_value() . '@nomail.com';
	$successUrl = 'http://good.com';
	$errorUrl = 'http://bad.com';
	$HOAmethod = 'Account_UpdatePaymentMethod';
	$HOAurl = str_replace("soap","secure",VIN_SOAP_HOST) . "/vws.html";
	$HOAversion = '5.0';	// VIN_SOAP_CLIENT_VERSION
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

	if ( is_null($merchantAccountId) )
		$merchantAccountId = 'account-2015-02-10_02_55_50';
	if ( is_null($merchantPaymentMethodId) )
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

	$webSession->setPrivateFormValues(array($nvp1, $nvp2, $nvp3));

	$nvp7 = new NameValuePair();
	$nvp7->setName('Account_updatePaymentMethod_updateBehavior');
	$nvp7->setValue('CatchUp');

	$nvp8 = new NameValuePair();
	$nvp8->setName('Account_updatePaymentMethod_replaceOnAllAutoBills');
	$nvp8->setValue('false');

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

		print_r($response);

	# Check to see that the initialize succeeded
	#
	if ($response['returnCode'] == 200) {
		# The VID of the WebSession object serves as session id
		#
		$vin_WebSession_vid = $response['data']->session->getVID();
	} else {
		print_r($response);
		return;
	}

	# populate accountHolderName with same value as on billingAddress:
	$post['vin_PaymentMethod_accountHolderName'] =
	$post['vin_PaymentMethod_billingAddress_name'] = $name;
	$post['vin_PaymentMethod_billingAddress_addr1'] = $addr1;
	$post['vin_PaymentMethod_billingAddress_city'] = $city;
	$post['vin_PaymentMethod_billingAddress_district'] = $district;
	$post['vin_PaymentMethod_billingAddress_postalCode'] = $postalCode;
	$post['vin_PaymentMethod_billingAddress_country'] = $country;
	$post['vin_Account_emailAddress'] = $email;
	$post['vin_PaymentMethod_creditCard_account'] = $creditCardAccount;
	$post['vin_PaymentMethod_creditCard_expirationDate'] = $exp;
	$post['vin_PaymentMethod_nameValues_cvn'] = $cvn;

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

	print "<b><i>SOAP URL</i></b>: " . VIN_SOAP_HOST . PHP_EOL;
	
	# Do the POST
	#
	print "Posting to <b>HOA URL</b>: " . $HOAurl . PHP_EOL;
	print PHP_EOL;
	
	exec("curl -s $curlopts " . $HOAurl, $curlout, $curlret);

	# this line is only here to support testing with a single PHP file:
	$_GET = simulate_get($curlout);
	# the above function established the $_GET array to be the same as
	# what PHP by default populates in the $_GET array when the returnURL
	# page is a separate PHP file, and is here to support testing with
	# a single PHP file.

	#---------------------------------------------------------------------------
	#
	#	PHP specific code handling of HOA WebSession Method finalize processing
	#	------------------------------------------------------------------------
	#
	# The finalize call returns an updated WebSession object.  This
	# is correct in that it refers to the WebSession.finalize soap request and the
	# WebSession.finalizeResponse soap response as defined in the WSDL and Online
	# Soap Documentation at:
	#
	#	http://developer.vindicia.com/docs/soap/index.html?ver=9.0
	#
	# However, specific to the CashBox PHP Client library, this translates into
	# the mapping into the PHP API method to invoke the WebSession.finalize soap
	# request, and the WebSession.finalizeResponse object containing the returned
	# WebSession object may be accessed from the response:
	#
	# 1) PHP API method to invoke the WebSession.finalize soap request:
	#
	#	$response = WebSession->finalize()
	#
	# 2) WebSession.finalizeResponse soap response object containing WebSession:
	#
	#	Following a successful call to finalize(), the values from $response, the
	#	WebSession.finalizeResponse soap response, are then accessible by referencing
	#	the nested objects in the response corresponding to the hierarchy in the WSDL.
	#
	# Note that the WebSession data members from the WSDL are documented in the
	# Online Soap documentation for the WebSession datatype below:
	#
	#	http://developer.vindicia.com/docs/soap/AllDataTypes.html?pf=1&ver=9.0&type=WebSession
	#
	# ---
	#
	# HOA uses the following 3 steps:
	#		1. WebSession.initialize (initialize & obtain a sessionId for the WebSession)
	#		2. HOA Form Post (Present Form to buyer with hidden sessionId, buyer posts to HOA)
	#		3. Redirect to HOA success page (sessionId from redirect for WebSession.finalize)
	#
	# Below describes the handling of Step 3, HOA success page,
	# where the sessionId from the redirect URL is passed to the finalize() method below:
	#
	# 6.	Upon payment form submission if customer’s browser is redirected to the Return URL
	#      hosted by you and specified in the WebSession object. On this page finalize the
	#      WebSession object as follows:
	#
	# 		a.	The redirect URL string contains WebSession’s VID as the value associated with
	#			name ‘session_id’.  Use the VID to make the finalize() call below:
	#
	# ---
	#
	#	HOA WebSession Method: Account_UpdatePaymentMethod
	#
	#---------------------------------------------------------------------------

#
# HOA Success Page:  Need to call WebSession.finalize() to invoke internal
# soap call to Account.updatePaymentMethod() as indicated by the value of
# WebSession Method (Account_UpdatePaymentMethod), using the parameters already
# contained in the WebSession object stored in the database (on the HOA/CashBox server).
#
#
# Documentation of Soap Objects returned in PHP code (displayed by print_r($response)):
#
# To see the data members in the WebSession (& all other CashBox Soap objects),
# please review the Online Soap Documentation at the link below:
#
#	http://developer.vindicia.com/docs/soap/index.html?ver=9.0
#
#	Within the Online Soap Documentation, the following links are pertinent:
#
#	All Data Types that are returned by PHP (as seen by print_r($response) are found at:
#		http://developer.vindicia.com/docs/soap/AllDataTypes.html?ver=9.0
#
#	The WebSession methods (including WebSession.initialize() & WebSession.finalize():
#		http://developer.vindicia.com/docs/soap/WebSession.html?ver=9.0
#
#	Specifically for the code below, the WebSession Data Type definition:
#		http://developer.vindicia.com/docs/soap/AllDataTypes.html?pf=1&ver=9.0&type=WebSession
#
# With the above Documentation of the CashBox Soap Objects in mind, the source code
# of the PHP library itself reveals the actual syntax of the PHP methods involved in
# setting data members on the CashBox Soap Objects represented in PHP Objects created
# & used in this sample code.
#
# The source code for the WebSession Object in the PHP library is found under
# 		Vindicia/Soap/WebSession.php within the PHP library for example.
#

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

	print "Parameters from redirect URL:" . PHP_EOL;
	print_r ($_GET);
	$session_id = $_GET['session_id'];
	$webSession = new WebSession();
	$webSession->setVid($session_id);

	# initialize call timestamp in case of error for support information below:
	date_default_timezone_set("America/Los_Angeles");
	$call_timestamp = date("c");	// c - The ISO-8601 date (e.g. 2015-06-17T16:34:42+00:00)

	$response = $webSession->finalize();
	print_r ($response);
 
	$session = $response['data']->session;

		# WebSession.finalizeResponse.return.returnCode
	$returnCode = $response['returnCode'];
		# WebSession.finalizeResponse.return.returnString
	$returnString = $response['returnString'];
		# WebSession.finalizeResponse.return.soapId
	$finalize_soapId = $response['data']->return->soapId;
	print $call_timestamp . " WebSession.finalize soapId: " . $finalize_soapId . "\n";
		# log soap id if available in the return values of this call

		# WebSession.apiReturn.returnCode
	$apiReturnCode = $session->apiReturn->returnCode;
		# WebSession.apiReturn.returnString
	$apiReturnString = $session->apiReturn->returnString;

		# WebSession.apiReturnValues
	$apiReturnValues = $session->apiReturnValues;
		# WebSession.apiReturnValues.accountUpdatePaymentMethod
	$accountUpdatePaymentMethod = $apiReturnValues->accountUpdatePaymentMethod;
		# WebSession.apiReturnValues.accountUpdatePaymentMethod.validated
	$validated = $accountUpdatePaymentMethod->validated;

	if ($response['returnCode'] != '200') {
		print $response['returnCode'] . PHP_EOL;
		print $returnString . PHP_EOL;
		print $apiReturnCode . PHP_EOL;
		print $apiReturnString . PHP_EOL;
	}
	else {
		print $apiReturnCode . PHP_EOL;
		print $apiReturnString . PHP_EOL;
		if ($apiReturnCode == "200") {
			print PHP_EOL. 'Updated Credit Card. Account=' . $merchantAccountId .
				' PaymentMethod=' . $merchantPaymentMethodId . PHP_EOL;
		}
		else if ($apiReturnCode="261") {
			print "All active AutoBills were updated. AutoBills which are both expired and Suspended cannot be updated.\n";
		}
		else if ($apiReturnCode=="400") {
			print "One of the following:
• Invalid Payment Method Type. (You cannot change the Payment Method Type on an existing Payment Method.)
• No PaymentMethod specified in arguments.
• Data validation error Failed to create Payment-Type-Specific Payment Record: Credit Card conversion failed: Credit Card failed Luhn check.\n";
		}
		else if ($apiReturnCode=="402") {
			print "One of the following:
• PaymentMethod failed validation.
• Error attempting to authorize card.
• Unable to authorize card.\n";
		}
		else if ($apiReturnCode="404") {
			print "No match found error-description.
 Returned if CashBox cannot find an account that matches the input in the Vindicia database.\n";
		}
		else if ($apiReturnCode="407") {
			print "Transaction cannot be processed due to Failed AVS policy evaluation\n";
		}
		else if ($apiReturnCode="408") {
			print "Transaction cannot be processed due to Failed CVN policy evaluation\n";
		}
		else if ($apiReturnCode="409") {
			print "AutoBill creation failed: due to AVS and CVV Check Failed\n";
		}
		else if ($apiReturnCode="410") {
			print "AutoBill creation failed: due to AVS and CVV Check not being able to be performed\n";
		}
		else {
			print "Error while making call to Vindicia CashBox\n";
		}
	}
	return array('apiReturnCode' => $apiReturnCode, 'validated' => $validated);
}

// Only to facilitate command line running of method or single PHP
// file execution invoked from a browser, both for testing only.
//
// Artificially sets the $_GET array to facilitate testing so that
// it matches the behavior with a separate HOA Success page (in which
// case the $_GET array is automatically populated with the query values).
function simulate_get($curlout)
{
	print "Response from curl:" . PHP_EOL;
	print_r($curlout);
	print PHP_EOL;

	#if (php_sapi_name() == "cli") {
	$curlresp = implode("\n", $curlout);
	$reg_exUrl = "/(?<=href=(\"|'))[^\"']+(?=(\"|'))/";
	preg_match($reg_exUrl, $curlresp, $url);
	$parsed_url = parse_url($url[0]);
	$qs = $parsed_url['query'];
	$nvparray = explode("&amp;", $qs);
	#print_r($nvparray);
	foreach ($nvparray as $val) {
		if ( !empty($val) ) { 
			$nvp = explode("=", $val);
			$key = $nvp[0];
			$value = $nvp[1];
			#print "\$key=" . $key . ", \$value=" . $value . PHP_EOL;
			$_GET[$key] = $value;
		}
	}
	#}
	#print_r($_GET);
	return $_GET;
}

?>
</pre>
