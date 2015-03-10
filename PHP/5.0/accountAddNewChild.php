<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$parentID = $argv[1];

print "parent: $parentID \n";

$name = "Child One";
$addr1 = "19 Davis Dr";
$city = "Belmont";
$state = "CA";
$postalcode = "94002";
$country = "US";


$email = "childAccount".rand(10000,99999)."@vindicia.com";

$address = new Address();
    $address->setName($name);
    $address->setAddr1($addr1);
    $address->setCity($city);
    $address->setDistrict($state);
    $address->setPostalCode($postalcode);
    $address->setCountry($country);

$accountID = "childAccount" .rand(1000,9999) . "-" . rand(1000,999999);

$child1  = new Account();
        $child1->setMerchantAccountId($accountID);
        $child1->setEmailAddress($email);
        $child1->setShippingAddress($address);
        $child1->setEmailTypePreference('html');
        $child1->setWarnBeforeAutoBilling(false);
        $child1->setName($name);

$parent = new Account();
$parent->setMerchantAccountId($parentID);

// use the force flag to remove these children from a previous parent
// and assign them to this new one
//$force=true;
$force=false;

// use payerReplace to determine if any existing autobills of these children
// should use the parents payment method, or only autobills created from here in should.
//$payerReplacementBehavior='ReplaceOnAllAutoBills';
$payerReplacementBehavior='ReplaceOnlyFutureAutoBills';

$response = $parent->addChildren(array($child1), $force, $payerReplacementBehavior);

print_r ($response);

?>
