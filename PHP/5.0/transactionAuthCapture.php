<?php

ini_set('include_path','/usr/local/Vindicia_php5_lib_9.0');
ini_set('display_errors',1);

require_once("Vindicia/Soap/Vindicia.php");
require_once("Vindicia/Soap/Const.php");

// This example uses an existing account
$accountId = $argv[1];
$dryrun    = $argv[2];
print "accountId is $accountId \n";
print "dryrun is $dryrun \n";

$account  = new Account();
$return   = $account->fetchByMerchantAccountId($accountId);
$customer = $return['data']->account;

// This example is assuming we already know it's the default PM and type is Credit Card
$pm = $customer->paymentMethods[0];
$nv = new NameValuePair();
$nv->setName('CVN');
$nv->setValue('123');

$pm->setNameValues(array(
    $nv
));


$transaction = new Transaction();
$transaction->setCurrency('USD');
$transaction->setMerchantTransactionId('testTrx-' . rand(10000, 99999));

$transaction->setAccount($customer);

$transaction_lineItem0 = new TransactionItem();
$transaction_lineItem0->setSku('RMMTEST002');
$transaction_lineItem0->setName('LineItem 1');
$transaction_lineItem0->setMerchantAutoBillItemId('foobar');
$transaction_lineItem0->setPrice('99.99');
$transaction_lineItem0->setQuantity('1');

// Campaign Code can be assigned to each individual line item:
$transaction_lineItem0->setCampaignCode('promo2');


$lineitems = array(
    $transaction_lineItem0
);

$transaction->setTransactionItems($lineitems);
$transaction->setSourcePaymentMethod($pm);

// we can choose to send email for one-time transactions, or not
$sendEmailNotification = 1;

// use the default CashBox AVS and CVN policy
$ignoreAvsPolicy = 0;
$ignoreCvnPolicy = 0;

// Campaign Code can also be passed in to the call as a param to apply to all eligible items
//$campaign = 'promo2'; 

$response = $transaction->authCapture($sendEmailNotification, $ignoreAvsPolicy, $ignoreCvnPolicy, $campaign, $dryrun);

print_r($response);
?>
