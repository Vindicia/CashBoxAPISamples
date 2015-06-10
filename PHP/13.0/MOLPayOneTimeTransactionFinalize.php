<?php
require_once("Vindicia/Soap/Vindicia.php");
require_once("Vindicia/Soap/Const.php");

$tx = new Transaction();
$sparseReturnDescriptor = ''; // We want full Transaction object returned
$tx_vid = "" ; // Grab this from the value associated with the name 'vid' in the query string of the URL when customer arrives at your return page after completing the MOLPay payment
$response = $tx->finalizeCustomerAction($sparseReturnDescriptor, $txVid);
if ($response['returnCode'] == 200) {
       $returned_tx = $response['data']->transaction;
        if ($returned_tx->statusLog[0]->status == 'Pending') {
        	print ("Payment approval is pending. Access will be granted after payment is cleared \n");
        	// Store the transaction in local "pend" table and monitor it for approval
        	// using a Transaction->fetchDeltaSince() based cron job

        }
        else if ($returned_tx->statusLog[0]->status == 'AuthorizedPending' || 
        	$returned_tx->statusLog[0]->status == 'Captured' ||
        	$returned_tx->statusLog[0]->status == 'Authorized') {
        	print ("Payment is successful! \n");

        }
        else if ($returned_tx->statusLog[0]->status == 'Cancelled') {
        	print ("Payment was declined, please try your purchase again \n");
        }
        else {
        	print ("Error: Unexpected transaction status\n");
        	print ("Vindicia soap id: " . $response['data']->return->soapId . "\n");
        }
} else
{
	print ("Error: Vindicia return code: " . $response['returnCode'] .  "  ");
	print (" return string: " . $response['returnString'] .  "  ");
	print (" soap id: " . $response['data']->return->soapId . "\n");
}


?>
