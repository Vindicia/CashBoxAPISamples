<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$abID = $argv[1];
$remID = $argv[2];
$addID = $argv[3];
$effectiveDate = $argv[4];
$prorate = $argv[5];
$dryrun = $argv[6];
$changeBillingPlanTo = NULL;

print "Replacing product $remID with product $addID on autobill $abID \n";
print "dryrun is $dryrun \n";

$autobill = new AutoBill();
$return=$autobill->fetchByMerchantAutoBillId($abID);
$autobillVID = $return['data']->autobill->VID;

print "VID is $autobillVID \n";

$modAutoBill = new AutoBill();
$modAutoBill->setVID($autobillVID);

$remProduct = new Product();
$remProduct->setMerchantProductId($remID); 
$remItem = new AutoBillItem();
$remItem->setProduct($remProduct);

$addProduct = new Product();
$addProduct->setMerchantProductId($addID); 
$addItem = new AutoBillItem();
$addItem->setProduct($addProduct);
$addItem->setCampaignCode('promo10');

$replaceModification = new AutoBillItemModification();
$replaceModification->setRemoveAutoBillItem($remItem);
$replaceModification->setAddAutoBillItem($addItem);

$response = $modAutoBill->modify($prorate,$effectiveDate,$changeBillingPlanTo,($replaceModification),$dryrun);
print_r ($response);


?>
