<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$pg             = 0;
$pageSize       = 100;
$startTimestamp = '2015-02-01T00:00:00';
$endTimestamp   = '2015-02-08T00:00:00';

$cb  = new Chargeback();
$ret = $cb->fetchDeltaSince($startTimestamp, $endTimestamp, $pg, $pageSize);

// optional to return the complete set of objects returned
//print_r ($ret);

$fetched_CBs = $ret['data']->chargebacks;

if ($fetched_CBs != null) {
    foreach ($fetched_CBs as $cb) {
        print "Found Chargeback VID: " . $cb->VID . "\n";
        print "merchantTransactionId: " . $cb->merchantTransactionId . "\n";
        print "amount is : " . $cb->amount . "\n";
        print "\n";
    }
}

$return_code = $ret['returnCode'];
print "Return Code is: $return_code \n";
//print_r ($ret);
?>
