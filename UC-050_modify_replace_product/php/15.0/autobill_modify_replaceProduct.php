<?php

ini_set('include_path', '/usr/local');
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$abID                = $argv[1];
$remID               = $argv[2];
$addID               = $argv[3];
$effectiveDate       = $argv[4];
$prorate             = $argv[5];
$dryrun              = $argv[6];
$changeBillingPlanTo = NULL;

print "Replacing product $remID with product $addID on autobill $abID \n";
print "dryrun is $dryrun \n";


$autobill    = new AutoBill();
$return      = $autobill->fetchByMerchantAutoBillId('', $abID);
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
$addItem->setMerchantAutoBillItemId('myAutoBillItem-' . rand(00000, 99999));

$replaceModification = new AutoBillItemModification();
$replaceModification->setRemoveAutoBillItem($remItem);
$replaceModification->setAddAutoBillItem($addItem);

$response = $modAutoBill->modify('', $prorate, $effectiveDate, $changeBillingPlanTo, ($replaceModification), $dryrun, '');
print_r($response);

if ($response['returnCode'] == 200) {
    // Now we can force this autobill to show up in autobill.fetchDeltaSince for this time block
    // this is a workaround for the fact that modify itself does not cause an autobill to show up in FDS
    
    // but only if this was NOT a dryrun... 
    
    if ($dryrun != 1) {
        
        print "Forcing FDS return by resetting nextBilling date";
        
        $autobill             = new AutoBill();
        $autobillVID          = $response['data']->autobill->VID;
        $autobillNextBillDate = $response['data']->autobill->nextBilling->timestamp;
        echo "VID is $autobillVID \n";
        echo "nextBill is $autobillNextBillDate \n";
        
        $autobill = new AutoBill();
        $autobill->setVID($autobillVID);
        $response = $autobill->delayBillingToDate('', $autobillNextBillDate, true);
        print_r($response);
        
    }
    
}

?>
