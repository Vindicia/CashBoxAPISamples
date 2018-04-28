<pre>
<?php
#
#	SEL001BillSelect.php:
#
#		SEL-001	Submit Failed Transactions for Recovery
#
# ---------------------------------------------------------------
#
# This sample was run using wsdl2php from pear to generate PHP library:
#
# 1. Download & install pear if needed (already installed on the Mac):
#
#	https://pear.php.net/manual/en/installation.getting.php
#
# 2. Download & install wsdl2php-0.2.1-pear.tgz from Sourceforge:
#
#	https://sourceforge.net/projects/wsdl2php/
#
#		sudo pear install wsdl2php-0.2.1-pear.tgz
#
# 3. Generate PHP library using wsdl2php:
#
#		wsdl2php https://soap.vindicia.com/1.1/Select.wsdl
#
# 4. Cleanup generated code to work with Select (Return datatype is PHP keyword):
#
#	mv Select.php{,.orig}; sed 's/class Return /class VindiciaReturn /g' Select.php.orig > Select2.php
#	(or edit generated Select.php: change class Return to class VindiciaReturn).
#
#	(SelectUtil.php overrides 'Return' to 'VindiciaReturn' to match the change above)
#
#	Also fix up constructor to be compatible with PHP 7.0+:
#
#	sed 's/function Select(/function __construct(/g' Select2.php > Select.php
#
#
# 5. This sample may be found on GitHub at:
#
#	https://github.com/Vindicia/CashBoxAPISamples/tree/master/Select/PHP
#
#	Reference:
#
#		http://developer.vindicia.com/docs/soap/Select.html?ver=1.1 (billTransactions)
#
#

// Change this to the location where you have installed Select.php:
ini_set('include_path','/usr/local/Select_php5_lib_1.1');
ini_set('display_errors',1);
 
require_once("Select.php");
require_once("SelectUtil.php");


$wsdl = "https://soap.vindicia.com/".VIN_SOAP_CLIENT_VERSION."/Select.wsdl";

$properties = getProperties("Environment");

$endpoint = $properties["soap_url"];
$soap_login = $properties["soap_login"];
$soap_password = $properties["soap_password"];

$select = new Select($wsdl, getOptions($endpoint));

$auth = new Authentication();

$auth->version = VIN_SOAP_CLIENT_VERSION;
$auth->login = $soap_login;
$auth->password = $soap_password;
$auth->userAgent = 'Select PHP Client Library v'.VIN_SOAP_CLIENT_VERSION;

print "\tsoap endpoint = " . $endpoint . EOL . EOL;
print "\tsoap login = " . $auth->login . EOL;
print "\tsoap version = " . $auth->version . EOL;
print "\tuserAgent = " . $auth->userAgent . EOL . EOL;


$timestamp = '2016-02-05T10:41:39-07:00';	# Change to timestamp of last Failure of the Failed Transaction

// create a unique identifier

$merchantTransactionId = 'TEST1234567890';	# Change this to your unique id of the Failed Transaction


$customerId = 'CUST123456';	# Change this to your unique id of the Customer's account
$subscriptionId = 'SUBS1234567890';	# Change this to your unique id of the Customer's subscription
$authCode = '302';
$creditCardAccount = '4417123456789113';	# When using Tokens, change to the BIN (1st 6 digits of Card)
$amount = '10.99';
$paymentMethodId = 'PAYMETHOD123456';	# Change to your unique id of the Payment Method, or Token Id
$creditCardExpirationDate = '202208';
$divisionNumber = '5698';
$currency = 'USD';
$billingFrequency = 'Monthly';
$paymentMethodIsTokenized = false;	# Change to True when using Tokens

$n = 2;	# number of transactions to process

$transactions = array();

for ($i =0; $i < $n; $i++) {
	$trx = getTransaction($merchantTransactionId.($i==0 ? '' : '_'.$i),
		$customerId, $subscriptionId,
		$authCode, $creditCardAccount, $paymentMethodId, $creditCardExpirationDate,
		$amount, $currency, $timestamp, $divisionNumber, $billingFrequency,
		$paymentMethodIsTokenized);
	// print_r ($trx);
  	$transactions[$i] = $trx;
	print "transactions[" . $i . "]->paymentMethodIsTokenized="
		. ($transactions[$i]->paymentMethodIsTokenized ? "true" : "false") . EOL;
}

print_r($transactions);
print "paymentMethodIsTokenized=" . ($paymentMethodIsTokenized ? "true" : "false") . EOL;

$billTransactions = new billTransactions();

$billTransactions->auth = $auth;
$billTransactions->transactions = $transactions;	#array($transaction);

try{ 
	$response = $select->billTransactions($billTransactions);
}catch(SoapFault $fault){ 
	print "\nRequest "  . EOL; 
	# print_r($select->__getLastRequest()); 
	echo "\n\n ====== Printing request soap envl below ====== <br>\n\n\n" . "<pre class=\"brush: xml\">" . htmlspecialchars(xmlpp($select->__getLastRequest())) ."</pre>"."\n\n <br>============End Soap Request============\n\n\n<br>";

	print "\nSOAP Fault: " . $fault->getMessage() . EOL . EOL; 
}

print_r($select);
print_r($response);
print_r($response->return);
print_r($response->response);

print "\nRequest "  . EOL; 
# print_r($select->__getLastRequest()); 
echo "\n\n ====== Printing request soap envl below ====== <br>\n\n\n" . "<pre class=\"brush: xml\">" . htmlspecialchars(xmlpp($select->__getLastRequest())) ."</pre>"."\n\n <br>============End Soap Request============\n\n\n<br>";


print "\nResponse "  . EOL;
if ( $select->__getLastResponse() != NULL ) {
	echo "\n\n====== Printing response soap envl below ========= <br>\n\n\n" . "<pre class=\"brush: xml\">". htmlspecialchars(xmlpp($select->__getLastResponse())) . "</pre>". "\n\n <br>============End Soap Response ============\n\n\n<br>";


print "\treturnCode = " . $response->return->returnCode . EOL;
print "\treturnString = " . $response->return->returnString . EOL;
print "\tsoapId = " . $response->return->soapId . EOL . EOL . EOL;

print "\tTransactionValidationResponse array:" . EOL . EOL;
# print_r($response->response);

foreach ($response->response as $key => $val)
{
	print "\tTransactionValidationResponse[" . $key . "]:" . EOL;
	print "\t\tmerchantTransactionId = " . $val->merchantTransactionId . EOL;
	print "\t\tcode = " . $val->code . EOL;
	print "\t\tdescription = " . $val->description . EOL . EOL;
}
}
else
	print "\n\tNo Response "  . EOL . EOL . EOL;


print "\tsoap endpoint = " . $endpoint . EOL . EOL;
print "\tsoap login = " . $auth->login . EOL;
print "\tsoap version = " . $auth->version . EOL;
print "\tuserAgent = " . $auth->userAgent . EOL;


?>
<pre>

