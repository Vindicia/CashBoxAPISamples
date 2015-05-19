<?php

ini_set('include_path','/usr/local');
ini_set('display_errors',1);
error_reporting(E_ALL|E_STRICT);

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$name = "Johnny Rotten";
$addr1 = "300 MLK Blvd";
$city = "Charlotte";
$state = "NC";
$postalcode = "28202";
$country = "US";

$email = "johnny@rotten.com";

// now, create the Address object

$address = new Address();
    $address->setName($name);
    $address->setAddr1($addr1);
    $address->setCity($city);
    $address->setDistrict($state);
    $address->setPostalCode($postalcode);
    $address->setCountry($country);

$accountID = "map-" .rand(1000,9999) . "-" . rand(1000,999999);


$map = new MerchantAcceptedPayment();

$pm = new PaymentMethod();
    $pm->setType('MerchantAcceptedPayment');
    $pm->setMerchantAcceptedPayment($map);


$pm_array = array ($pm);

$account = new Account();
        $account->setMerchantAccountId($accountID);
        $account->setEmailAddress($email);
        $account->setShippingAddress($address);
        $account->setEmailTypePreference('html');
        $account->setWarnBeforeAutoBilling(false);
        $account->setName($name);
        $account->setPaymentMethods($pm_array);


// must be an existing Product
$productID = $argv[1];
print "productID is $productID \n";
$product = new Product();
$product->setMerchantProductId($productID);

// AutoBills may have multiple products
// each in an AutoBillItem as an array:
$item = new AutoBillItem();
// set the Product in the AutoBillItem
$item->setProduct($product);
$item->setMerchantAutoBillItemId('myAutoBillItem-' . rand(00000,99999));


// must be an existing BillingPlan
$billingplanID = $argv[2];
print "billingplanID is $billingplanID \n";
$billingplan = new BillingPlan();
$billingplan->setMerchantBillingPlanId($billingplanID);

$autobillID='ab-map-'.rand(1000,9999999);

print "autobillID is $autobillID \n";

$autobill = new AutoBill();
// set the Product (AutoBillItem)
$autobill->setItems(array($item));
$autobill->setAccount($account);
$autobill->setBillingPlan($billingplan);
$autobill->setMerchantAutoBillId($autobillID);

$validateForFuturePayment = 0;
$immediateAuthFailurePolicy = 'doNotSaveAutoBill';
$fraudScore = 100;
$ignoreAVS = 1; 
$ignoreCVN = 1;
$dryrun = 1;
$dryrun = $argv[3];
print "dryrun is $dryrun \n";
$coupon = NULL;
$coupon = $argv[4];
print "coupon is $coupon \n";

$response = $autobill->update($immediateAuthFailurePolicy, $validateForFuturePayment, $fraudScore, $ignoreAVS, $ignoreCVN, $coupon, $dryrun);

print_r ($response);

$return_code = $response['returnCode'];
$return_string = $response['returnString'];

print "Return code is $return_code \n";
print "Return string is $return_string \n";

$return_id = $response['data']->autobill->merchantAutoBillId;
print "Created MerchantAutoBillId: $return_id \n";

?>
