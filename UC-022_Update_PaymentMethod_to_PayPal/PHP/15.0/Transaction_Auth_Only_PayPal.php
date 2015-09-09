<?php

require_once("Vindicia/Soap/Vindicia.php");
require_once("Vindicia/Soap/Const.php");

    $address = new Address();
    $address->setName('A. Customer');;
    $address->setAddr1('19 Davis Dr');
    $address->setCity('Belmont');
    $address->setDistrict('CA');
    $address->setPostalCode('94002');
    $address->setCountry('US');
    
    
    $account = new Account();								
    $account->setMerchantAccountId('paypal_account_'.rand(1000,9999999));
    $account->setEmailAddress('acustomer@vindicia.com');
    $account->setShippingAddress($address);
    $account->setEmailTypePreference('html');
    $account->setWarnBeforeAutoBilling(true);
    $account->setName('Ralph Morrison');

    
    $paypal = new PayPal();
    $paypal->setReturnUrl('http://localhost:8888/success.php');
    $paypal->setCancelUrl('http://localhost:8888/cancellation.php');
    $paypal->setRequestReferenceId(1); 

    
    $pm = new PaymentMethod();
    $pm->setMerchantPaymentMethodId('paypal_pm_'.rand(1000,9999999));
    $pm->setType('PayPal');
    $pm->setPayPal($paypal);
    $pm->setBillingAddress($address);
    $pm->setAccountHolderName('Ralph Morrison');
    
    $pms = array($pm);
    
    $account->setPaymentMethods($pms);
    
    $response = $account->update('');    // create the account                               

    $transaction = new Transaction();
    $transaction->setMerchantTransactionId('paypal_auth_tx_'.rand(10000,99999));
    
    $transaction->setAccount($account);
   
   
   	$transaction_lineitem = new TransactionItem();
   	$transaction_lineitem->setSku('PAYPAL_PM_VALIDATION');
   	$transaction_lineitem->setName('For Validation only - no charge to you');
   	$transaction_lineitem->setPrice('0.01');
   	$transaction_lineitem->setQuantity('1');
   	
   	$lineitems = array($transaction_lineitem);
   	
   	$transaction->setTransactionItems($lineitems);
   	$transaction->setSourcePaymentMethod($pm);
   	$transaction->setShippingAddress($address);
         
	$minChargebackProbability = 100; // no fraud scoring for PayPal transactions
	$sendEmailNotification = 0;
	$campaignCode = null;
	$dryRun = 0;

   	$response = $transaction->auth('',$minChargebackProbability, $sendEmailNotification, $campaignCode, $dryRun);

   // uncomment for debug
   // print_r($response);

$auth_status = $response['data']->transaction->statusLog[0]->payPalStatus;
$redirect_url = $auth_status->redirectUrl;

print "redirect is $redirect_url \n";
?>
