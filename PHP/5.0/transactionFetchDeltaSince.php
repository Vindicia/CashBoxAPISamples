<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$pg       = 0;
$pageSize = 100;

$startTimestamp = '2015-02-22T00:00:00-07:00';
$endTimestamp   = '2015-02-23T23:59:00-07:00';

do {
    $tx  = new Transaction();
    $ret = $tx->fetchDeltaSince($startTimestamp, $endTimestamp, $pg, $pageSize, null);
    
    $count = 0;
    
    //uncomment for debugging:
    //print_r ($ret);
    
    $fetched_TXs = $ret['data']->transactions;
    
    if ($fetched_TXs != null) {
        $count = sizeof($fetched_TXs);
        print "count is: " . $count . "\n";
        foreach ($fetched_TXs as $tx) {
            print "Found Transaction VID: " . $tx->VID . "\n";
            print "merchantTransactionId: " . $tx->merchantTransactionId . "\n";
            print "amount is : " . $tx->amount . "\n";
            print "timestamp is: " . $tx->timestamp . "\n";
            print "\n";
        }
        $pg++;
        print "page is " . $pg . "\n";
    }
    
} while ($count != 0);

?>
