<?php
require_once("Vindicia/Soap/Vindicia.php");
require_once("Vindicia/Soap/Const.php");


$account  = new Account();
// existing customer's Account ID. Tax will be added based on shippingAddress populated on this Account
$account->setMerchantAccountId("jdoe101"); 

$pm = new PaymentMethod();
$pm->setMerchantPaymentMethodId('VINTESTPM-' . rand(10000, 99999)); // Unique payment method id
$pm->setType('HostedPage');

$hostedPageInfo = new HostedPage();
$hostedPageInfo->setCountryCode('MY');
$hostedPageInfo->setLanguage('en');
$hostedPageInfo->setReturnUrl('http://www.mysite.co.my/PaymentReturn.php'); // specify a page on your site customer will be redirected to after completing the MOLPay payment
$hostedPageInfo->setProcessorPaymentMethodId('all'); // Customer can choose any payment method type at MOLPay

$provider = new PaymentProvider();
$provider->setName('MOLPay');

$hostedPageInfo->setPaymentProvider($provider);

$pm->setHostedPage($hostedPageInfo);

$transaction = new Transaction();
$transaction->setCurrency('MYR');
 // Unique transaction ID, use prefix to tell transactions apart from the subscription transactions
$transaction->setMerchantTransactionId('VINTEST-' . rand(10000, 99999));
$transaction->setAccount($account);
$transaction->setSourcePaymentMethod($pm);

//Create purchase line items. This can also be created by looking up a CashBox Product 
$transaction_lineItem0 = new TransactionItem();
$transaction_lineItem0->setSku('MYPRODUCT001');  // If using a CashBox Product, specify the Product's ID here
$transaction_lineItem0->setName('Digital Magazine');
$transaction_lineItem0->setPrice('4.99');
$transaction_lineItem0->setQuantity('1');
$transaction_lineItem0->setTaxClassification('DM010100'); // Specify appropriate Avalara TaxCode here
// Campaign Code can be specified for each individual line item:
//$transaction_lineItem0->setCampaignCode('promo2');
$lineitems = array($transaction_lineItem0);
$transaction->setTransactionItems($lineitems);
// we can choose to send email for one-time transactions, or not
$sendEmailNotification = 1;
// use the default CashBox AVS and CVN policy
$ignoreAvsPolicy = true; // Does not apply to MOLPay payment method
$ignoreCvnPolicy = true; // Does not apply to MOLPay payment method
$sparseReturnDescriptor = ''; // we want the whole Transaction object back
// Campaign Code can also be passed in to the call as a param to apply to all eligible items
//$campaign = 'promo2'; 
$response = $transaction->authCapture($sparseReturnDescriptor, $sendEmailNotification, $ignoreAvsPolicy, $ignoreCvnPolicy, $campaign, $dryrun);
if ($response['returnCode'] == 200) {
       $returned_tx = $response['data']->transaction;
        if ($returned_tx->statusLog[0]->status == 'AuthorizationPending') {
        	print ("Visit the following URL to complete payment \n");
        	print($returned_tx->statusLog[0]->hostedPageStatus->redirectUrl . "\n\n");
        }
        else if ($returned_tx->statusLog[0]->status == 'Cancelled') {
        	print ("Payment did not complete. Please start purchase again \n");
        	print (" soap id: " . $response['data']->return->soapId . "\n");
        }
        else {
        	print ("Error: Unexpected transaction status\n");
        	print ("Vindicia soap id: " . $response['data']->return->soapId . "\n");
        }
} else
{
	print ("Error: Vindicia return code: " . $response['returnCode'] .  "  ");
	print (" return string: " . $response['returnString'] .  "  ");
	print (" soap id: " . $response['data']->return->soapId . "\n");
}


?>
