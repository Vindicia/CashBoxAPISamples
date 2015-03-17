<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$accountID = $argv[1];
$showAll = false;
$includeChildren = false;

print "merchantAccountId: $accountID \n";

$account = new Account();
$account->setMerchantAccountId($accountID);

$entitlement = new Entitlement();

$response = $entitlement->fetchByAccount($account, $showAll, $includeChildren);

print_r ($response);

?>
