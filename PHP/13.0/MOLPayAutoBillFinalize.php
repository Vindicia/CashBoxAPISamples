<?php
require_once("Vindicia/Soap/Vindicia.php");
require_once("Vindicia/Soap/Const.php");

$abill = new AutoBill();
$sparseReturnDescriptor = ''; // We want full AutoBill object returned
$tx_vid = "" ; // Grab this from the value associated with the name 'vid' in the query string of the URL when customer arrives at your return page after completing the MOLPay payment
$response = $abill->finalizeCustomerAction($sparseReturnDescriptor, $tx_vid);
if ($response['returnCode'] == 200) {
       $returned_abill = $response['data']->autobill;
        if ($returned_abill->status == 'PendingCustomerAction') {
        	print ("Payment approval is pending. Access will be granted after payment is cleared \n");
        	// Store the transaction in local "pend" table and monitor it for approval
        	// using a Transaction->fetchDeltaSince() based cron job

        }
        else if ($returned_abill->status == 'Active') {
        	print ("Payment is successful! \n");

        }
        else if ($returned_abill->status == 'Suspended' || $returned_bill->status == 'Cancelled') {
        	print ("Payment was declined, please try your purchase again \n");
        }
        else {
        	print ("Error: Unexpected autobill status\n");
        	print ("Vindicia soap id: " . $response['data']->return->soapId . "\n");
        }
} else
{
	print ("Error: Vindicia return code: " . $response['returnCode'] .  "  ");
	print (" return string: " . $response['returnString'] .  "  ");
	print (" soap id: " . $response['data']->return->soapId . "\n");
}


?>
