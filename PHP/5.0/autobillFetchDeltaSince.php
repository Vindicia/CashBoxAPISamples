<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$pg             = 0;
$pageSize       = 100;
$startTimestamp = '2015-02-09T00:00:00.000-08:00';
$endTimestamp   = '2015-02-10T23:59:59.000-08:00';

do {
    $auto = new AutoBill();
    $ret  = $auto->fetchDeltaSince($startTimestamp, $pg, $pageSize, $endTimestamp);
    
    $count = 0;
    
    // optional to print all returned autobills in full
    //print_r ($ret);
    
    if ($ret['data']->autobills != null) {
        $fetched_ABs = $ret['data']->autobills;
        $count       = sizeof($fetched_ABs);
        print "count is: " . $count . "\n";
        foreach ($fetched_ABs as $ab) {
            print $ab->VID . "," . $ab->merchantAutoBillId . "," . $ab->billingPlan->merchantBillingPlanId . "," . $ab->status . "," . $ab->startTimestamp . "," . $ab->endTimestamp . "\n";
        }
        $pg++;
    }
    
} while ($count != 0);


$return_code = $ret['returnCode'];
print "Return Code is: $return_code \n";
?>
