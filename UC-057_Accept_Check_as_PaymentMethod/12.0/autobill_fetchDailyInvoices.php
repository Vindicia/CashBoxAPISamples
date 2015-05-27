<?php


require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$pg = 0;
$pageSize = 100;
$startTimestamp = '2015-05-26T00:00:00-07:00';
$endTimestamp = '2015-05-26T23:59:59-07:00';

do { 
$ab = new AutoBill();
$ret = $ab->fetchDailyInvoiceBillings($startTimestamp, $endTimestamp, $pg, $pageSize); 

$count = 0;

print_r ($ret);

$fetched_TXs=$ret['data']->transactions; 

if ($fetched_TXs != null) {
$count = sizeof($fetched_TXs);
print "count is: " . $count . "\n";
foreach ($fetched_TXs as $tx) {
print "Found Transaction VID: " . $tx->VID . "\n";
print "merchantTransactionId: " . $tx->merchantTransactionId . "\n";
print "amount is : " . $tx->amount . "\n";
print "timestamp is: " . $tx->timestamp . "\n";
foreach ($tx->nameValues as $nvp) {
   if ( $nvp->name == "vin:AutoBillVID" ) {
   print "Autobill Vid:  " . $nvp->value . "\n"; }
   if ( $nvp->name == "vin:MerchantAutoBillIdentifier" ) {
   print "Autobill ID:  " . $nvp->value . "\n"; }
   if ( $nvp->name == "vin:RetryNumber" ) {
   print "Retry Number:  " . $nvp->value . "\n"; }
   if ( $nvp->name == "vin:BillingCycle" ) {
   print "Billing Cycle:  " . $nvp->value . "\n"; }
}

print "\n";
  }
$pg++;
print "page is " . $pg . "\n";
}

} while ( $count != 0 ) ;

$return_code = $ret['returnCode'];
print "Return Code is: $return_code \n";
print_r ($ret);
?>
