<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$accountID = $argv[1];

$account = new Account();
$account->setMerchantAccountId($accountID);

$autobill = new AutoBill();
$response = $autobill->fetchByAccount($account, 'true');

//print the entire response for debugging
//print_r ($response);

$returnCode   = $response['returnCode'];
$returnString = $response['returnString'];

if ($returnCode == 200) {
    $returned_abills = $response['data']->autobills;
    print "merchantAutoBillId,status\n";
    
    foreach ($returned_abills as $autobill) {
        print "$autobill->merchantAutoBillId" . "," . "$autobill->status" . "\n";
    }
} else {
    print "Unable to retrieve AutoBills for account ID: $accountID \n";
    print "$returnString\n";
    
}

?>
