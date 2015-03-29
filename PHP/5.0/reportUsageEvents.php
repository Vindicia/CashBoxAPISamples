<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$testId = rand(1, 1000000); // random number for some unique IDs

$usageEvent1 = new Event();
$usageEvent1->setMerchantEventId("eventID-1-" . $testId); // unique Id
$usageEvent1->setEventDate('2015-03-27T06:29:34-07:00');
$usageEvent1->setDescription("Service usage");
$usageEvent1->setMerchantAutoBillId("subscriptionID101");

// CashBox will automatically identify which AutoBill item the event should be
// applied to if there are no more than one item with same product. If the 
// subscription 
$usageEvent1->setMerchantProductId("ratedPriceProduct");
$usageEvent1->setAmount(14902); 

// Separate event related to another subscription can be reported in a single call

$usageEvent2 = new Event();
$usageEvent2->setMerchantEventId("eventID-2-" . $testId); // unique Id
$usageEvent2->setEventDate('2015-03-28T23:59:00-07:00');
$usageEvent2->setDescription("Service usage");
$usageEvent2->setMerchantAutoBillId("subscriptionID102");
$usageEvent2->setMerchantProductId("regular-product");
$usageEvent2->setAmount(159002); 

// Now let's record the two events with CashBox

$rp = new RatePlan(); // This is the SOAP interface that supports the recordEvent call

$response = $rp->recordEvent(array($usageEvent1, $usageEvent2)); // up to 50 events can be put in the array
print_r($response);
if ($response['returnCode'] == 200) {
	print "Successfully reported events. Call SOAP ID" .  $response['data']->return->soapId . "\n";

}
else {
	print "Events could not be reported";
	print "Return code: " . $response['returnCode'] . " Return string: " . $response['returnString'] .  " Call SOAP ID: " .  $response['data']->return->soapId . "\n";
}
?>
