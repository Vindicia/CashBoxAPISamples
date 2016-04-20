<?php
#
#	SEL003CancelSubmittedSelectTransactions.php:
#
#		SEL-003	Cancel Submitted Select Transactions
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
#	mv Select.php{,.orig}; sed 's/class Return /class VindiciaReturn /g' Select.php.orig > Select.php
#	(or edit generated Select.php: change class Return to class VindiciaReturn).
#
#	(SelectUtil.php overrides 'Return' to 'VindiciaReturn' to match the change above)
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


$customerId = 'CUST1234';	# Change this to your unique id of the Customer's account
$subscriptionId = 'SUBS1235';	# Change this to your unique id of the Customer's subscription
$authCode = '302';
$creditCardAccount = '4111111111111111';	# When using Tokens, change to the BIN (1st 6 digits of Card)
$amount = '10.99';
$paymentMethodId = 'PAYMETHOD1234';	# Change to your unique id of the Payment Method, or Token Id
$creditCardExpirationDate = '202208';
$divisionNumber = '5698';
$currency = 'USD';
$billingFrequency = 'Monthly';
$paymentMethodIsTokenized = 'False';	# Change to True when using Tokens

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
}

print_r($transactions);


$billTransactions = new billTransactions();

$billTransactions->auth = $auth;
$billTransactions->transactions = $transactions;	#array($transaction);

$response = $select->billTransactions($billTransactions);

print_r($response);

#
# Oops: should not have submitted those to Select...ask to pull them back
#
#	Submit those two Transactions in call to refundTransactions to cancel processing:
#
print EOL;
print "Cancelling Submission of Failed Transactions to Select...";
print EOL;

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

$response = $select->refundTransactions($refundTransactions);

print_r($response);
// print_r($response->return);
// print_r($response->return->returnCode);

if ($response->return->returnCode == '200') {
	print EOL;
    print "\t==> Successfully submitted!";
	print EOL.EOL;
}

?>
