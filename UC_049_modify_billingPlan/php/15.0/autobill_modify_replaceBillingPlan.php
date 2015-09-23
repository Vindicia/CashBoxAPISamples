<?php

ini_set('include_path','/usr/local');
ini_set('display_errors',1);
error_reporting(E_ALL|E_STRICT);

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$abId = $argv[1];
$bpId = $argv[2];

$effectiveDate = 'today';
$prorate = $argv[3];
$dryrun = $argv[4];

print "Modifying to use billing plan $bpId \n";
print "dryrun is $dryrun \n";

$autobill = new AutoBill();
$return=$autobill->fetchByMerchantAutoBillId($abId);
$autobillVID = $return['data']->autobill->VID;

print "VID is $autobillVID \n";

$modAutoBill = new AutoBill();
$modAutoBill->setVID($autobillVID);

$changeBillingPlanTo = new BillingPlan();
$changeBillingPlanTo->setMerchantBillingPlanId($bpId);

$response = $modAutoBill->modify('',$prorate,$effectiveDate,$changeBillingPlanTo,NULL,$dryrun,'');
print_r ($response);


?>
