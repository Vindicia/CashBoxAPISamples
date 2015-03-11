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

//$payerReplacementBehavior='ReplaceOnlyFutureAutoBills';
$payerReplacementBehavior='ReplaceOnAllAutoBills';

$response = $parent->removeChildren(array($child1,$child2),$payerReplacementBehavior);

print_r ($response);

?>
