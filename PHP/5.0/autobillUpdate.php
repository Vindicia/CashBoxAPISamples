<?php

ini_set('include_path', '/usr/local');
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

// This example uses an existing Account
$accountID = $argv[1];
print "accountID is $accountID \n";
$account = new Account();
$account->setMerchantAccountId($accountID);

// must be an existing Product
$productID = $argv[2];
print "productID is $productID \n";
$product = new Product();
$product->setMerchantProductId($productID);

// AutoBills may have multiple products (AutoBill Items)
$item = new AutoBillItem();

// You can apply a Campaign Code to the product here: 
$item->setCampaignCode('1MonthBonusPromo');

// set the Product in the AutoBillItem
$item->setProduct($product);


// must be an existing BillingPlan
$billingplanID = $argv[3];
print "billingplanID is $billingplanID \n";

$billingplan = new BillingPlan();
$billingplan->setMerchantBillingPlanId($billingplanID);

// Create a random ID for our testing - This should be much more unique in a production env.  
$autobillID = 'ab-random' . rand(1000, 9999999);
print "autobillID is $autobillID \n";

$autobill = new AutoBill();
$autobill->setItems(array(
    $item
));
$autobill->setAccount($account);
$autobill->setBillingPlan($billingplan);
$autobill->setMerchantAutoBillId($autobillID);

// You can apply a Campaign Code to the Billing Plan here: 
//$autobill->setBillingPlanCampaignCode('1MonthBonusPromo');

// IP is used along with address info for fraud scoring
$autobill->setSourceIp('123.123.123.123');

// Choices for a faiure of the initial auth.  Uncomment only one at a time:
$immediateAuthFailurePolicy = 'doNotSaveAutoBill'; // pre-9.0 behavior for an initial auth failure
//$immediateAuthFailurePolicy = 'putAutoBillInRetryCycle'; // Creates AutoBill and retriesthe authorizaton.
//$immediateAuthFailurePolicy = 'putAutoBillInRetryCycleIfPaymentMethodIsValid'; // recommended in the API guide

$validateForFuturePayment = 1; //  need to validate to get the above choices 

$fraudScore = 100; // minChargebackProbability of 100 skips fraud scoring completely
$ignoreAVS  = 0;
$ignoreCVN  = 0;
$dryrun     = 1;
$dryrun     = $argv[4];
print "dryrun is $dryrun \n";
$coupon = NULL;
$coupon = $argv[5];
print "coupon is $coupon \n";

$response = $autobill->update($immediateAuthFailurePolicy, $validateForFuturePayment, $fraudScore, $ignoreAVS, $ignoreCVN, $coupon, $dryrun);

print_r($response);

$return_code   = $response['returnCode'];
$return_string = $response['returnString'];

print "Return code is $return_code \n";
print "Return string is $return_string \n";

$return_id = $response['data']->autobill->merchantAutoBillId;
print "Created MerchantAutoBillId: $return_id \n";

?>
