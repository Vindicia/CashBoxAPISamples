<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$name = "Parent Account";
$addr1 = "19 Davis Dr";
$city = "Belmont";
$state = "CA";
$postalcode = "94002";
$country = "US";
$email = "parent-".rand(10000,99999)."@vindicia.com";

$pAddress = new Address();
    $pAddress->setName($name);
    $pAddress->setAddr1($addr1);
    $pAddress->setCity($city);
    $pAddress->setDistrict($state);
    $pAddress->setPostalCode($postalcode);
    $pAddress->setCountry($country);

$cc = new CreditCard();
  $cc->setAccount('4485983356242217');
  $cc->setExpirationDate('201710');

$pm = new PaymentMethod();
$pm->setType('CreditCard');
$pm->setBillingAddress($pAddress);
$pm->setCreditCard($cc);
$pm->setActive(1);

$pm_array = array ($pm);


$pAccountId = "parent-" .rand(1000,9999) . "-" . rand(1000,999999);

$account = new Account();
        $account->setMerchantAccountId($pAccountId);
        $account->setEmailAddress($email);
        $account->setShippingAddress($pAddress);
        $account->setEmailTypePreference('html');
        $account->setWarnBeforeAutoBilling(false);
        $account->setName($name);
        $account->setPaymentMethods($pm_array);



$name = "Child One";
$addr1 = "19 Davis Dr";
$city = "Belmont";
$state = "CA";
$postalcode = "94002";
$country = "US";


$email = "childAccount".rand(10000,99999)."@vindicia.com";

$cAddress = new Address();
    $cAddress->setName($name);
    $cAddress->setAddr1($addr1);
    $cAddress->setCity($city);
    $cAddress->setDistrict($state);
    $cAddress->setPostalCode($postalcode);
    $cAddress->setCountry($country);

$cAccountId = "childAccount" .rand(1000,9999) . "-" . rand(1000,999999);

$child1  = new Account();
        $child1->setMerchantAccountId($cAccountId);
        $child1->setEmailAddress($email);
        $child1->setShippingAddress($cAddress);
        $child1->setEmailTypePreference('html');
        $child1->setWarnBeforeAutoBilling(false);
        $child1->setName($name);


// use the force flag to remove these children from a previous parent
// and assign them to this new one
$force=false;
//$force=true;

// use payerReplace to determine if any existing autobills of these children
// should use the parents payment method, or only autobills created from here in should.
//$payerReplacementBehavior='ReplaceOnlyFutureAutoBills';
$payerReplacementBehavior='ReplaceOnlyFutureAutoBills';

$response = $account->addChildren(array($child1), $force, $payerReplacementBehavior);

print_r ($response);

print "created parent account $pAccountId and child $cAccountId\n";

?>
