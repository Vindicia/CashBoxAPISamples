<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$abID = $argv[1];
$amount = $argv[2];
$currency = 'USD';
$invoiceId = $argv[3];
$overageDisposition = NULL;
$useForFutureRebills = 0;
$note = 'Payment Made';

$pmID = "pm" .rand(1000,9999) . "-" . rand(1000,999999);

$map = new MerchantAcceptedPayment();
    $map->setPaymentType('Check');
    $map->setAccount('123456789');


$pm = new PaymentMethod();
    $pm->setType('MerchantAcceptedPayment');
    $pm->setMerchantAcceptedPayment($map);
    $pm->setMerchantPaymentMethodId($pmID);


$autobill = New AutoBill();
$autobill->setMerchantAutoBillId($abID);

$response = $autobill->makePayment($pm, $amount, $currency, $invoiceId, $overageDisposition, $useForFutureRebills, $note);

print_r ($response);

?>
