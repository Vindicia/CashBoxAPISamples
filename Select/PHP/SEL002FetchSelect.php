<?php
#
#	SEL002FetchSelect.php:
#
#		SEL-002	Retrieve Results of Select Processing
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
#		http://developer.vindicia.com/docs/soap/Select.html?ver=1.1 (fetchBillingResults)
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

# Timestamps in Select are maintained & returned in Pacific Timezone
#
# Therefore to match the requested timezone to the Select timezone,
# the -07:00 timezone specification should be used.  Your own timezone
# may be used here instead & it will be honored, but when reviewing
# the timestamps on the returned Transactions, they will be transformed
# to the Pacific Timezone (i.e. -07:00).
#
$start = '2016-04-05T10:41:39-07:00';	# Change to timestamp of last successful execution 
$end = '2016-05-02T10:41:39-07:00';	# Change to timestamp of now (or prior midnight)
$pageSize = 100;


function logMsg($message) {

    print $message.EOL;

}

function wrapValue($value) {

    $left = "-[";
    $right = "]-";
    $wrappedValue = $left.$value.$right;
    return $wrappedValue;
}

# Reference:
#
#	http://developer.vindicia.com/docs/soap/Select.html?ver=1.1 (fetchBillingResults)
#
function fetchResults($start, $end, $pageSize, $page) {

	global $select;
	global $auth;

    $nResults = 0;
		
    logMsg("------------------------------------------------------------------------");
    logMsg("start=" . $start . ", end=" . $end
			. ", pageSize=" . $pageSize . ", page=" . $page );
				
    logMsg("[Page " . $page . "]: Fetching " . wrapValue($pageSize) . " Billing Results");

	// class fetchBillingResults {
	//   public $auth; // Authentication
	//   public $timestamp; // dateTime
	//   public $endTimestamp; // dateTime
	//   public $page; // int
	//   public $pageSize; // int
	// }
	$fetchBillingResults = new fetchBillingResults();

	$fetchBillingResults->auth = $auth;
	$fetchBillingResults->timestamp = $start;
	$fetchBillingResults->endTimestamp = $end;
	$fetchBillingResults->page = $page;
	$fetchBillingResults->pageSize = $pageSize;

	$response = $select->fetchBillingResults($fetchBillingResults);
    //print_r($response);

	if ($response->return->returnCode == '200') {
		print EOL;
	    print "\t==> Successfully retrieved!";
		print EOL.EOL;
	}

    logMsg("Completed fetchBillingResults request[" . $page . "]:\n\tstart="
					. $start
					. ", end=" . $end
					. ",\n\tpageSize=" . $pageSize . ", page=" . $page . "\n");
    $ftxsReturn = $response->return;
    logMsg("\n\tResult=" . $ftxsReturn->returnCode . ", " . $ftxsReturn->returnString .
    "\n\tsoapId=" . $ftxsReturn->soapId . "\n");
    if ( isset($response->transactions) ) {
        $nResults = reportResults($response->transactions, $page);
    }
    logMsg("[Page " . $page . "]: nResults=" . $nResults);

    return $nResults;

}

function actionFetchResults($start, $end, $pageSize) {

    logMsg(EOL."Beginning process to fetch billing results from " . EOL
    	. "\t" . wrapValue($start) . ", ending " . wrapValue($end) . EOL);

    $page = 0;
		
    $nRecords = 0;
    $nTotalRecords = 0;
    while (True) {
        $nTotalRecords += $nRecords;
        $nRecords = fetchResults($start, $end, $pageSize, $page);
        $page += 1;
        if ( $nRecords <= 0 )
            break;
	}

    logMsg("------------------------------------------------------------------------" .
    	EOL . "Completed process to fetch billing results from " . EOL
    		. "\t" . wrapValue($start) . ", ending " . wrapValue($end) . EOL .
		EOL . 	"\tstart=" . $start .
		EOL . 	"\tend=" . $end .
		EOL . 	"\tpageSize=" . $pageSize .
		EOL . 
		EOL . 	"\tnTotalRecords=" . $nTotalRecords .
		EOL . 	"\tNumber of pages=" . $page .
		EOL . 	"\tPage Size=" . $pageSize . EOL);

}

function reportResults($results, $page) {

    $nRecords = 0;
    if (!is_null($results) ) {
			
    	$nRecords = count($results);
    	logMsg("Retrieved " . $nRecords . ", page [" . $page . "]:");
    	$n = 0;

    	# Reference:
    	#
    	#	http://developer.vindicia.com/docs/soap/AllDataTypes.html?pf=1&ver=1.1&type=Transaction
    	#
    	foreach ($results as $tx) {
    	    $status = $tx->status;
    	    logMsg("[" . $page . ":" . $n . "]: merchantTransactionId " . wrapValue($tx->merchantTransactionId)
					. " created selectTransactionId " . wrapValue($tx->selectTransactionId)
					. " with status " . wrapValue($status)
					. " , authCode " . wrapValue($tx->authCode)
					. " on " . $tx->timestamp
					. " for " . $tx->amount
					. " " . $tx->currency
					);
    	    $n += 1;

    	    # Reference:
    	    #
    	    #	http://developer.vindicia.com/docs/soap/AllDataTypes.html?pf=1&pop=yes&ver=1.1&type=NameValuePair
    	    #
    	    $nameValues = $tx->nameValues;
    	    for ($i = 0; $i < count($nameValues); $i++) {
    	    	logMsg( "\tnameValues[" . $i . "]: " .
    	    		$nameValues[$i]->name . " = " . $nameValues[$i]->value );
    	    }
    	    logMsg("");
    	}
	}
    else
    	logMsg("Nothing to fetch - the Transactions object is null.\n");

    return $nRecords;

}

actionFetchResults($start, $end, $pageSize);

print "\tsoap endpoint = " . $endpoint . EOL . EOL;
print "\tsoap login = " . $auth->login . EOL;
print "\tsoap version = " . $auth->version . EOL;
print "\tuserAgent = " . $auth->userAgent . EOL . EOL;

?>
