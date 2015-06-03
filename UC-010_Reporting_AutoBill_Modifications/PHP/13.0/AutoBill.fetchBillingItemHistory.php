<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$autobillID = $argv[1];

$autobill = new AutoBill();
$autobill->setMerchantAutoBillId($autobillID);
$response = $autobill->fetchBillingItemHistory('',$autobill); 
print_r ($response);

$return_code = $response['returnCode'];
$return_string = $response['returnString'];
print "status is " . $response['data']->autobill->status . "\n";

?>
