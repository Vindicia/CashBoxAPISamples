<html>
<body>

<?php
// Include the Vindicia library

ini_set('include_path','/Applications/MAMP/htdocs/16.0');

require_once("Vindicia/Soap/Vindicia.php");
require_once("Vindicia/Soap/Const.php");

// Pls change the ip address , return and cancel URL as per your setup
// note that IP address should be the customer's IP address
define("IP_ADDRESS", "192.168.1.100");
define("HOA_RETURN_URL","http://localhost:8888/success.php");
define("HOA_CANCEL_URL","http://localhost:8888/error.php");

// pls change the URL according to your test server, 
// for prodtest  set it as 'https://secure.prodtest.sj.vindicia.com/vws'
define("HOA_POST_URL","https://secure.prodtest.sj.vindicia.com/vws.html");
// for staging set it as 'https://secure.staging.sj.vindicia.com/vws'
//define("HOA_POST_URL","https://secure.staging.sj.vindicia.com/vws.html");

// get the merchantPaymentMethodId we will updating from the post
$merchantPmId = $_POST['merchantPmId'];

// ok, now we're ready to get the new card info. Step 1: create the WebSession object
$websession = new WebSession();
$websession->setMethod('PaymentMethod_Update');
$websession->setReturnURL(HOA_RETURN_URL);
$websession->setErrorURL(HOA_CANCEL_URL);
$websession->setIpAddress(IP_ADDRESS);	

// Step 2: start configuring the WebSession with the parameters we want to have

$nvp1 = new NameValuePair();
$nvp1->setName('vin_PaymentMethod_merchantPaymentMethodId');
$nvp1->setValue($merchantPmId); 

$websession->setPrivateFormValues(array($nvp1));

$nvp6 = new NameValuePair();
$nvp6->setName('PaymentMethod_Update_validate');
$nvp6->setValue(1);

//needs to be less than 100 to get a score.  100 is used to ignore fraud scoring
$nvp7 = new NameValuePair();
$nvp7->setName('PaymentMethod_Update_minchargebackprobability');
$nvp7->setValue('99');

$nvp8 = new NameValuePair();
$nvp8->setName('PaymentMethod_Update_replaceOnAllAutoBills');
$nvp8->setValue(1);
 
$nvp9 = new NameValuePair();
$nvp9->setName('PaymentMethod_Update_sourceIp');
$nvp9->setValue(IP_ADDRESS);

$nvp10 = new NameValuePair();
$nvp10->setName('PaymentMethod_Update_replaceOnAllChildAutoBills');
$nvp10->setValue(1);

$nvp11 = new NameValuePair();
$nvp11->setName('PaymentMethod_Update_ignoreAvsPolicy');
$nvp11->setValue(0);

$nvp12 = new NameValuePair();
$nvp12->setName('PaymentMethod_Update_ignoreCvnPolicy');
$nvp12->setValue(0);

$websession->setMethodParamValues(array($nvp6,$nvp7,$nvp8,$nvp9,$nvp10,$nvp11,$nvp12));
    
// now, create the session and generate it's session ID

$rc= $websession->initialize();
// print_r($rc);
$response_object = $rc['data'];
$return_code = $rc['returnCode'];
$return_object = $response_object->session;
$session_id = $return_object->VID;
    
?>

Merchant PM ID is <?=$merchantPmId?><br>


Please enter your payment details in the following form:

<form action=<?=HOA_POST_URL?> method="post">
                
                    <input type="hidden" name="vin_WebSession_version" value="16.0" /> 
                    
                    <input type="hidden" name="vin_WebSession_method" value="PaymentMethod_Update" />                  
                    <input type="hidden" name="vin_WebSession_vid" value="<?=$session_id?>" />

                    <input type="hidden" name="vin_PaymentMethod_Type" value="CreditCard" /> 
                    <input type="hidden" name="vin_PaymentMethod_currency" value="USD" /> 
                    <input type="hidden" value="<?=$merch_payment_ID?>" id="vin_PaymentMethod_merchantPaymentMethodId" name="vin_PaymentMethod_merchantPaymentMethodId" />
			Credit card number: <input type="text"  id="vin_PaymentMethod_creditCard_account" name="vin_PaymentMethod_creditCard_account" title="account number" />
			Expiration Year (YYYY): <input type="text"  id="vin_PaymentMethod_creditCard_expirationDate_year" name="vin_PaymentMethod_creditCard_expirationDate_year" title="expiration date" size="4" maxlength="4"/>
			Expiration Month (MM): <input type="text"  id="vin_PaymentMethod_creditCard_expirationDate_month" name="vin_PaymentMethod_creditCard_expirationDate_month" title="expiration date" size="2" maxlength="2"/>
			<br>
			CVV: <input type="text" id="vin_PaymentMethod_nameValues_CVN" name="vin_PaymentMethod_nameValues_CVN" title="CVN" size="3" maxlength="3" />
			<br>
			
			<input id="submit" name="commit" value="Update" type="submit" />


 </form>


</body>
</html>
