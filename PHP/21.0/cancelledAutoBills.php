<?php
// Name: Cancelled AutoBills Use Case
// Author: Liam Maxwell
// Date: 08/03/2018
//
// For a given time period locate autobills that became cancelled in that time period


// CHANGE: Include the Vindicia library and your auth credentials
require_once("/Users/liamm/Documents/LHM/Vindicia/Soap/Vindicia.php");
require_once("/Users/liamm/Documents/LHM/Vindicia/Soap/Const.php");

// Declare Global Variables and Constants
date_default_timezone_set("America/Los_Angeles");

// php cancelledAutoBills.php -d YYYYMMDD -i 1
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
echo "----------------------------------------------------------------------------------------------------------\n";
echo "merchantAccountId, merchantAutoBillId, startTimestamp, endTimestamp, emailAddress, cancelReasonCode, cancelReasonDesc\n";

// We will be searching for AutoBills that are cancelled
// For performance reasons we will use pageSize of 10 this means to grab ten at a time in the fetch
$autobill = new AutoBill();
$cancelReason = new CancelReason(); 
$eventType = AutoBill::AUTO_BILL_EVENT_TYPE__CANCELLATION;
$srd = '';
$page = 0;
$pageSize = 10;

do {

	$count = 0;
	$response = $autobill->fetchDeltaSince($srd, $startTime, $page, $pageSize, $stopTime, $eventType);

  // Validate that we got a successful API result even if empty
	if ($response['returnCode'] == 200) {

		// create an array of cancelled AutoBills that were returned
		$fetchedAutoBills = ( isset($response['data']->autobills) ?
							$response['data']->autobills : array() );

    // Determine the number of AutoBills that were retruned
		$count = sizeof($fetchedAutoBills);

    // As long as the array is not null then loop thru each AutoBill
		if ($fetchedAutoBills != null) {

			foreach ($fetchedAutoBills as $ab) {
        // Pull the attributes we want to report on

				$autoBillId = $ab->getMerchantAutoBillId();
				$account = $ab->account->merchantAccountId;
				$startDate = $ab->startTimestamp;
				$endDate = $ab->endTimestamp; 
				$email = $ab->account->emailAddress;
				$cancelReason = $ab->getCancelReason(); 
				$cancelReasonCode = $cancelReason->reason_code;
				$cancelReasonDesc = $cancelReason->description; 
				
				if ($cancelReasonCode == "") { 
				   $cancelReasonCode = "<BLANK>";
				   $cancelReasonDesc = "<BLANK>";
				}
				
				// Send the report line item to stdout
				echo "$account, $autoBillId, $startDate, $endDate, $email, $cancelReasonCode, \"$cancelReasonDesc\"\n";
			}
		}
  // Increment the page counter to make sure the next fetch is the next page of cancelled AutoBills
	$page++;

	} else {
    // If there was an error calling the API print to stdout the response
		echo "Error Retrieving Accounts...\n\n";
		print_r($response);
	}

} while ($count > 0);

echo "-----------------------------------------------------------------------------------------------------------\n";
echo "Finished Cancelled Subscription Search from $startTime to $stopTime.\n";

exit(0);

?>
