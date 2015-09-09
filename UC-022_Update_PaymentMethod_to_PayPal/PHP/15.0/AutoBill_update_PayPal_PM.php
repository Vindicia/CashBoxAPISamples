<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$ab=$argv[1];
$pmId=$argv[2];

print "autobill is $ab\n";

$paypal = new PayPal();
$paypal->setReturnUrl('http://localhost:8888/success.php');
$paypal->setCancelUrl('http://localhost:8888/cancellation.php');
$paypal->setRequestReferenceId(1);


$pm = new PaymentMethod();
$pm->setMerchantPaymentMethodId($pmId);
$pm->setType('PayPal');
$pm->setPayPal($paypal);

$autobill = new AutoBill();
$autobill->setMerchantAutoBillId($ab);
$autobill->setPaymentMethod($pm);


$immediateAuthFailurePolicy = 'doNotSaveAutoBill';
$validateForFuturePayment = 0;
$fraudScore = 100;
$ignoreAVS = 1;
$ignoreCVN = 1;
$dryrun = 1;
$dryrun = $argv[3];
$coupon = NULL;

$response = $autobill->update('',$immediateAuthFailurePolicy, $validateForFuturePayment, $fraudScore, $ignoreAVS, $ignoreCVN, $coupon, $dryrun,'');

print_r ($response);

$return_code = $response['returnCode'];
$return_string = $response['returnString'];

print "Return code is $return_code \n";
print "Return string is $return_string \n";

$return_id = $response['data']->autobill->merchantAutoBillId;
print "Created MerchantAutoBillId: $return_id \n";

?>
