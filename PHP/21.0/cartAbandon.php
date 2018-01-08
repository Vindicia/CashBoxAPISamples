<?php 
// Name: Cart Abandon Use Case 
// Author: Liam Maxwell
// Date: 10/30/2017
//
// For a given time period locate any account that does not have any 
// Autobills.  We will ignore account with any Autobills, even if cacncelled. 
// I will loop through calls to Account.fetchCreatedSince() and loop through the results
// looking for absence of any autobills. 
// CHANGE: Include the Vindicia library and your auth credentials 


// Declare Global Variables and Constants 
require_once("Vindicia.php");
require_once("Const.php");
date_default_timezone_set("America/Los_Angeles");

// php cartAbandon.php -d YYYYMMDD -i 1

if ($argc < 5) { 
	echo "\nWrong Number of Arguments Passed!\n\n";
	echo "Usage: $argv[0] -d YYYYMMDD -i I\n\n";
	echo "Where 'YYYYMMDD' is the date you wish to start the search.\n";
	echo "Where 'I' is an Integer representing the number of days after the start date to search.\n\n";
	die("Try Again\n"); 
}

for ($i=1; $i < $argc; $i++) { 
	switch($argv[$i]) {
	case '-d':
		// Set the Start Date Value 
		$i++; 
		if (isset($argv[$i])) { 
			$dayToRefresh = $argv[$i]; 
			$time = strtotime($dayToRefresh);
			$dayToRefresh = date('Y-m-d',$time);			
			
		} else {
			die("Must specify a YYYYMMDD value after -d!\n");
		}
		break; 
	case '-i':
		// Set the Number of Days Value  
		$i++; 
		if (isset($argv[$i])) { 
			$daysToRefresh = $argv[$i]; 
			echo "Executing For: $dayToRefresh + $daysToRefresh Days\n";
			
		} else {
			die("Must specify an integrer value for number of days after -i!\n");
		}
		break; 		
	default:
		die("Unknown argument passed: " . "$argv[$i]\n"); 
		break; 
	}
}

// Keep in mind that all CashBox timestamps are in the Pacific timezone
$startTime = "$dayToRefresh" . "T00:00:00.000Z"; 
$stopTime = date('Y-m-d', strtotime(('+ ' . $daysToRefresh . ' days'), strtotime($dayToRefresh)));
$stopTime = "$stopTime" . "T23:59:59.000Z"; 

echo "Start: $startTime\n";
echo "Stop: $stopTime\n";


$account = new Account(); 
$account1 = new Account(); 
$autobill = new AutoBill();
$srd = '';
$page = 0; 
$pageSize = 10; 
$abPage = 0; 
$abPageSize = 10; 

do {
	$response = $account->fetchCreatedSince($srd, $startTime, $stopTime, $page, $pageSize);
	$count = 0;
									
	if ($response['returnCode'] == 200) {

		$fetchedAccounts = ( isset($response['data']->accounts) ?
							$response['data']->accounts : array() );
							
		$count = sizeof($fetchedAccounts);

		if ($fetchedAccounts != null) {

			foreach ($fetchedAccounts as $act) {

				$customerId = $act->getMerchantAccountId();
				
				// Check each account for subscriptions 
				$abcount = 0; 
				$account1->setMerchantAccountId($customerId);
				$response = $autobill->fetchByAccount($srd, $account1, false, $abPage, $abPageSize);
				
				// a 404 return code indicates no autobills/subscription 
				if ($response['returnCode'] == 404) { 
					echo "Cart Abandon: $customerId\n";
				}	
			}
		}
	
	$page++; 
		
	} else {
	
		echo "Finished Retrieving Accounts...\n\n";
	}
	
} while ($count > 0);

exit(0); 

?>
