<?php
require_once("../../../API/50/Vindicia/Soap/Vindicia.php");
require_once("../../../API/50/Vindicia/Soap/Const.php");

$merchantAutoBillId = 'ab-2015-06-26_05_48_36';

$autoBill = new AutoBill();
$autoBill->setMerchantAutoBillId($merchantAutoBillId);

$curAmount = new CurrencyAmount();
$curAmount->setCurrency('USD');
$curAmount->setAmount(25);
$cr = new Credit();
$cr->setCurrencyAmounts(array($curAmount));
$response = $autoBill->grantCredit($cr, '25 Currency Credit');

?>