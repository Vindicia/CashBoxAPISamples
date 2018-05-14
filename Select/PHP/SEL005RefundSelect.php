<pre>
<?php
#
#	SEL005RefundSelect.php:
#
#		SEL-005	Refund Select Transactions
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
#		http://developer.vindicia.com/docs/soap/Select.html?ver=1.1 (refundTransactions)
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


$timestamp = '2016-02-05T10:41:39.000Z';	# Change to timestamp of last Failure of the Failed Transaction

// create a unique identifier

$merchantTransactionId = 'TEST1236';	# Change this to your unique id of the Failed Transaction


#
# These Transactions have already been recovered by Select & returned as
# Captured status from fetchBillingResults previously.
#
# Customer already paid, Duplicate Transactions...request Refunds in Select
#
#	Submit these two Transactions in call to refundTransactions to perform Refunds:
#
print EOL;
print "Requesting Refunds to be performed in Select on previously recovered".EOL;
print "(i.e. Captured) Failed Transactions...".EOL;
print EOL;

$n = 2;	# number of refunds to process

$refunds = array();

for ($i =0; $i < $n; $i++) {
	$refund = $merchantTransactionId.($i==0 ? '' : '_'.$i);
	// print_r ($refund);
  	$refunds[$i] = $refund;
}
//$refunds = array( $merchantTransactionId, $merchantTransactionId.'_1' );

print_r($refunds);

print EOL;
print "Calling refundTransactions...";
print EOL;

$refundTransactions = new refundTransactions();

$refundTransactions->auth = $auth;
$refundTransactions->refunds = $refunds;

try{ 
	$response = $select->refundTransactions($refundTransactions);

	print_r($response);
	// print_r($response->return);
	// print_r($response->return->returnCode);

	if ($response->return->returnCode == '200') {
		print EOL;
	    print "\t==> Successfully submitted!";
		print EOL.EOL;
	}
}catch(SoapFault $fault){ 
	print "\nSOAP Fault: " . $fault->getMessage() . EOL . EOL; 
}

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

	if ( $response->response != null ) {
		foreach ($response->response as $key => $val)
		{
			print "\tTransactionValidationResponse[" . $key . "]:" . EOL;
			print "\t\tmerchantTransactionId = " . $val->merchantTransactionId . EOL;
			print "\t\tcode = " . $val->code . EOL;
			print "\t\tdescription = " . $val->description . EOL . EOL;
		}
	}
	else
		print "\n\t\tNo TransactionValidationResponse array"  . EOL . EOL . EOL;
}
else
		print "\n\tNo Response "  . EOL . EOL . EOL;

?>
<pre>
