<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$parentID = $argv[1];
$child1ID = $argv[2];
$child2ID = $argv[3];

print "parent: $parentID \n";
print "child1: $child1ID \n";
print "child2: $child2ID \n";

$parent = new Account();
$parent->setMerchantAccountId($parentID);

$parentNvp = new NameValuePair();
$parentNvp->setName('parentAccountId');
$parentNvp->setValue($parentID);


$child1 = new Account();
$child1->setMerchantAccountId($child1ID);
$child1->setNameValues(array($parentNvp));


$child2 = new Account();
$child2->setMerchantAccountId($child2ID);
$child2->setNameValues(array($parentNvp));

// use the force flag to remove these children from a previous parent
// and assign them to this new one
//$force=true;
$force=false;

// use payerReplacementBehavior to determine if any existing autobills of these children
// should use the parents payment method, or only autobills created from here in should.
//$payerReplacementBehavior='ReplaceOnAllAutoBills';
$payerReplacementBehavior='ReplaceOnlyFutureAutoBills';

$response = $parent->addChildren(array($child1,$child2), $force, $payerReplacementBehavior);

print_r ($response);

?>
