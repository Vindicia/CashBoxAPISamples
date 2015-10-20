<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$abID = $argv[1];
$addID = $argv[2];
$bpID = $argv[3];
$dryrun = $argv[4];

print "Adding product $addID to autobill $abID \n";
print "BillingPlan is $bpID \n";
print "dryrun is $dryrun \n";


$autobill = new AutoBill();
$return=$autobill->fetchByMerchantAutoBillId('',$abID);
$remID=$return['data']->autobill->items[0]->product->merchantProductId;
$autobillVID = $return['data']->autobill->VID;

print "VID is $autobillVID \n";
print "item to remove is $remID \n";

$modAutoBill = new AutoBill();
$modAutoBill->setVID($autobillVID); 


$addProduct = new Product();
$addProduct->setMerchantProductId($addID); 

$remProduct = new Product();
$remProduct->setMerchantProductId($remID); 

$addItem = new AutoBillItem();
$addItem->setProduct($addProduct);

$remItem = new AutoBillItem();
$remItem->setProduct($remProduct);

$bp = new BillingPLan();
$bp->setMerchantBillingPlanId($bpID);

$mod = new AutoBillItemModification();
$mod->setAddAutoBillItem($addItem);
$mod->setRemoveAutoBillItem($remItem);


$effectiveDate = 'today'; 
$prorate = 1;

$response = $modAutoBill->modify('',$prorate,$effectiveDate,$bp,array($mod),$dryrun);

print_r ($response);

?>
