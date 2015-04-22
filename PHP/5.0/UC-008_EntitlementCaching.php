<pre>
<?php

#-----------------------------------------------------------------------------------
#
#	UC-008 	Entitlement Caching
#
#		User Story
#
#		As a merchant system I need to locally store entitlement status so that I
#		can control access to a product or service.
#
#
#		Pre-Condition:
#
#			Local entitlement repository exists; Entitlements are defined for offerings;
#			Active subscriptions exist that are granting entitlement(s)
#
#			$next_start is maintained in persistent storage, readable & writable from here
#
#
#		Methods:
#
#			fetchEntitlements()		Main loop of paged calls to Entitlement.fetchDeltaSince
#									used to populate & maintain local Entitlement cache, by
#									requesting all changes to Entitlements since the time
#									of the last successful execution, tracked in $next_start
#
#			userEntitled()			Determines dynamically at time of login if user entitled
#									from inspection of local Entitlement cache.
#
#
#-----------------------------------------------------------------------------------

ini_set('include_path','/usr/local/Vindicia_php5_lib_5.0');
ini_set('display_errors',1);
error_reporting(E_ALL|E_STRICT);

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

// initialize local storage of start timestamp for next execution:
$next_start = "";	// move this into a database/persistent storage


$bPrintProducts = true;

if ($bPrintProducts) {
    $fetchProduct_soapId = displayProducts();
    logMsg("Product.fetchAll() soapId: " . $fetchProduct_soapId . "\n\n");
}
		
$bFetchEntitlements = true;

if ($bFetchEntitlements) {
	$nUpdated = fetchEntitlements();
	logMsg( "TEntitlement: Updated " . $nUpdated . " entitlements\n" );
}

function displayProducts()
{
    $soapId = null;

    $page = 0;
    $pageSize = 10;

	$returnCode = '200';
	
	$product = new Product();
	$response = $product->fetchAll($page, $pageSize);
	print_r($response);
	//print_r($response['data']->products);
	$return = $response['data']->return;
	$returnCode = logReturn($return, "TEntitlement: Product.fetchAll result:");
	if ( '200' != $returnCode ) {
		logEnvironment();
	}
	$soapId = $return->soapId;
	$products = $response['data']->products;
    printProducts("Product", $products);

    return $soapId;
}

function logEnvironment() 
{
	logMsg("logEnvironment(): soapLogin=" . VIN_SOAP_CLIENT_USER .
        	", soapURL=" . VIN_SOAP_HOST . "\n\n" .

		"PHP Version: " . phpversion() . "\n" .
		"SOAP Version: " . VIN_SOAP_CLIENT_VERSION . "\n" .
		"SOAP_LOGIN: " . VIN_SOAP_CLIENT_USER . "\n" .
		"SOAP URL: " . VIN_SOAP_HOST . "\n" .
		"DEFAULT_TIMEOUT: " . VIN_SOAP_TIMEOUT . "\n");

}
	
function logMsg($message) {

	// can change to write to a logfile:
	print ($message . "\n");

}

function logReturn($return, $s)
{
	// Input: $response->data->return
	
	// Log response to soap call
	logMsg(timestamp() . "\n" . $s . "\n" .
		"\tReturn code: " . $return->returnCode . "\n" .
		"\tReturn Message: " . $return->returnString . "\n" .
		"\tSoap call ID: " . $return->soapId . "\n" );

	return ( $return->returnCode );
}

function getCashBoxVersion()
{
    return VIN_SOAP_CLIENT_VERSION;
}
	
function timestamp(
	$dayOffset = 0 )
{
	# initialize call timestamp in case of error for support information below:
	date_default_timezone_set("America/Los_Angeles");
	// c - The ISO-8601 date (e.g. 2015-06-17T16:34:42+00:00)
	$timestamp = date("c", time() + $dayOffset * 60 * 60 * 24);

	return $timestamp;
}
	
function dateString($name, $date)
{
	return ( $name . ": " . $date );
}

function logEFDS(
			$start,
			$page,
			$pageSize,
			$end)
{
	logMsg(timestamp() . " Entitlement.fetchDeltaSince:\n\t" .
				dateString("start", $start) .
				", page: " . $page . ", pageSize: " . $pageSize .
				", " . dateString("end", $end) );
}

function load_next_start()
{
	global $next_start;

    logMsg("load_next_start(): Update to read from database - " . dateString("next_start", $next_start) );
    return $next_start;      // replace this with call to database query
}

function save_next_start($start)
{
	global $next_start;

    logMsg("save_next_start(): Update to write to database - " . dateString("next_start", $start) );
    $next_start = $start;     // replace this with call to database update
    return;
}
	
function fetchEntitlements()
{
	$soapId = null;
	$num_updated=0;

	logEnvironment();

	// read next_start from database, set from last call
	$start = load_next_start();

	if ( empty($start) ) {
		$start = timestamp(-1);
		logMsg( "TEntitlement: Initializing " . dateString("next_start", $start) . "\n");
	}
		
	$end = timestamp();	// now
        
	$page=0;
	$pageSize=10;
		
	$entitlement = new Entitlement();

	$returnCode = '200';
	
	do {
		logEFDS($start, $page, $pageSize, $end);
		$response = $entitlement->fetchDeltaSince( $start, $page, $pageSize, $end );
		$returnCode = logReturn($response['data']->return, "TEntitlement result:");
		if ( '200' != $returnCode ) {
			logEnvironment();
			break;
		}
			
		$entitlements = ( isset($response['data']->entitlements) ?
							$response['data']->entitlements : array() );
			// update local entitlement status:
		$num_updated += updateEntitlements( $entitlements );
		$page++;
	} while ( count($entitlements) > 0 );


	// Update next_start to advance to the next time window
	// only if the above loop completed successfully.
	//
	// Otherwise, we want to keep it at the same value
	// so the next execution of the loop above will read
	// the same entitlements again, to get all of the data
	// hopefully with no errors the next time around.
	//	
	if ( '200' == $returnCode ) {
		$start = $end;						// save end as start for next time.
		$start=date('c', strtotime($end)-60);	// subtract 1 minute for next call to be
											// inclusive of any updates to db in last minute

		print "Total pages: " . $page . "\n\n";
		logEFDS($start, $page, $pageSize, $end);
		print "1 minute before current end: " . $start . "\n\n";

		// use next_start as the start timestamp for next call

    	// save next_start to database for next call
    	save_next_start($start);
	}
	else {
		print "Error occurred: next_start NOT updated, the same start timestamp will " .
					"be used for the next iteration, so all of the same data " .
					"will be requested again!\n\n";
	}

	$new_next_start = load_next_start();	// check next_start new value in database
	logMsg("\nThe next execution will use startTimestamp: " . $new_next_start . "\n");

	return $num_updated;
}
	
function updateEntitlement($entitlement)
{
	if ( null != $entitlement ) {
		// update local entitlement cache here:
		//
			
		logMsg( "Updating Entitlement " . $entitlement->merchantEntitlementId .
				" for Account " . $entitlement->account->merchantAccountId .
                ", from Product " . $entitlement->merchantProductId .
                ", from AutoBill " . $entitlement->merchantAutoBillId .
                ",\n\tsource: " . $entitlement->source .
				", current status: " . ( $entitlement->active ? "Active" : "Inactive" ) .
				", " . dateString("entitlement start", $entitlement->startTimestamp) .
				" " . dateString("end", $entitlement->endTimestamp)  . "\n" );
			
	}

	// display dynamic entitlement status (based on start & end timestamps only):
	print "=> " . ( userEntitled($entitlement) ? "" : "NOT " ) . "Entitled!\n\n";
}

function userEntitled($entitlement)
{
	// the userEntitled() method is what should be called at login to determine
	// if the user is entitled, based on the current time and the start & end timestamps

	$now = timestamp();
	
	$isEntitled = $entitlement->startTimestamp < $now &&
                ( ( null == $entitlement->endTimestamp )
                    || $entitlement->endTimestamp > $now // now
                );

    logMsg("userEntitled(): Entitlement " . $entitlement->merchantEntitlementId .
            " for Account " . $entitlement->account->merchantAccountId .
            ", from Product " . $entitlement->merchantProductId .
            ", from AutoBill " . $entitlement->merchantAutoBillId .
            ",\n\tsource: " . $entitlement->source .
            ", dynamic status: " . ($isEntitled ? "Entitled" : "Not Entitled") .
            ", " . dateString("entitlement begins", $entitlement->startTimestamp) .
            " " . dateString("until", $entitlement->endTimestamp) . "\n");

    return $isEntitled;

}
		
function updateEntitlements($entitlements)
{
    $num_updated=0;

	if ( isset($entitlements) && is_array($entitlements) ) {
		foreach ($entitlements as $index => $entitlement) {
			// print Entitlement here:
			printEntitlement( $entitlement, $index );
			// update Entitlement here:
			updateEntitlement( $entitlement );
		}
		$num_updated += count($entitlements);
	}
		
	return $num_updated;
}
	
function printEntitlement($entitlement, $index)
{
	if ( null != $entitlement ){	
		// print Entitlement here:
		logMsg("Account: " . $entitlement->account->merchantAccountId . "\n" .
			"Entitlement[" . $index . "]:" . "\n" .
			"merchantEntitlementId: " . $entitlement->merchantEntitlementId . "\n" .
			"Entitlement Description: " . $entitlement->description . "\n" .
			"merchantAutoBillId: " . $entitlement->merchantAutoBillId . "\n" .
			"merchantProductId: " . $entitlement->merchantProductId . "\n" .
			"source: " . $entitlement->source . "\n" .
			"\tActive: " . $entitlement->active . "\n" .
			"\t" . dateString("Start", $entitlement->startTimestamp) . "\n" .
			"\t" . dateString("End", $entitlement->endTimestamp) );
	}
}
	
function printProducts($prefix, $products)
{
	if ( null != $products && is_array($products) ) {	
		for ($i=0; $i < count($products); $i++) {
			// print Product here:
			$product = $products[$i];
			logMsg( $prefix . "[" . $i . "]:" . "\n" .
				"merchantProductId: " . $product->merchantProductId . "\n" .
				"Product VID: " . $product->VID .
				( count($products) == ($i + 1) ? "\n" : "" ) );
			$credit = $product->creditGranted;
			$descriptions = $product->descriptions;
			$entitlementIds = $product->merchantEntitlementIds;
			$prices = $product->prices;
			$taxClassification = $product->taxClassification;

			printProducts("	bundled", $product->bundledProducts);
		}
	}

}


?>
</pre>
