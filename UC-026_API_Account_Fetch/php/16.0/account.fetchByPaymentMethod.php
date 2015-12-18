<?php

ini_set('include_path','/usr/local');
ini_set('display_errors',1);
error_reporting(E_ALL|E_STRICT);

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$ccnum = $argv[1];

$pm= new PaymentMethod();
$pm->setType('CreditCard');
$cc = new CreditCard();
$cc->setAccount($ccnum);
$pm->setCreditCard($cc);

$acct = new Account();
$page = 0;
$pageSize = 1000; // max 1000 records per page
do {
$count = 0;
$response = $acct->fetchByPaymentMethod('',$pm, $page, $pageSize);
$return_code = $response['returnCode'];
print "Return Code is: $return_code \n";
print_r ($response);
if($return_code == 200)
{
$accounts = $response['data']->accounts;
//$count = sizeof($accounts);
foreach ($accounts as $account) {
// process each account found here
print "Found account with id: " . $account->getMerchantAccountId() . "\n";
$acct_name = $account->getname();
$acct_zip = $account->paymentMethods[0]->billingAddress->postalCode;
print "Name on account is $acct_name" . "\n";
print "acct_zip is $acct_zip \n";

}
}
$page++;
} while ($count != 0);
?>
