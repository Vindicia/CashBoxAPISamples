<?php
require_once("Vindicia/Soap/Vindicia.php");
require_once("Vindicia/Soap/Const.php");


$account  = new Account();
// existing customer's Account ID. Tax will be added based on shippingAddress populated on this Account
$account->setMerchantAccountId("jdoe101"); 

$pm = new PaymentMethod();
$pm->setMerchantPaymentMethodId('VINTESTPM-' . rand(10000, 99999)); // Unique payment method id
$pm->setType('HostedPage');
$pm->setCurrency('MYR'); // This guarantees that validation transactions use MYR currency

$hostedPageInfo = new HostedPage();
$hostedPageInfo->setCountryCode('MY');
$hostedPageInfo->setLanguage('en');
$hostedPageInfo->setReturnUrl('http://www.vindicia.com/'); // specify a page on your site customer will be redirected to after completing the MOLPay payment
$hostedPageInfo->setProcessorPaymentMethodId('credit'); // If the billing plan is recurring, customer can pay with credit card only

$provider = new PaymentProvider();
$provider->setName('MOLPay');

$hostedPageInfo->setPaymentProvider($provider);

$pm->setHostedPage($hostedPageInfo);

$abill = new AutoBill();
$abill->setCurrency('MYR'); // AutoBill will be billed in this currency. Make sure the Product and BillingPlan has prices in MYR
$abill->setMerchantAutoBillId('VINTEST-AB-' . rand(10000, 99999));  // Unique subscription ID
$abill->setAccount($account);
$abill->setPaymentMethod($pm);

//Create AutoBillItem to represent the purchased product
$item = new AutoBillItem();

// This is subscription product customer has selected to subscribe to
// Make sure a Product with this ID is already defined via the CashBox portal
$prod = new Product();
$prod->setMerchantProductId('QuickRenewalTestOnly'); 

$item->setProduct($prod);
$item->setQuantity(1);
// Campaign Code can be specified for each individual line item:
//$item->setCampaignCode('promo2');
$items = array($item);

$abill->setItems($items);

// If we are not going to use the default billing plan associated with the Product
// we must explicitly tell the AutoBill which billing plan it should use

$plan = new BillingPlan();
$plan->setMerchantBillingPlanId('FastRenewalTestPlan');

$abill->setBillingPlan($plan);

$immediateAuthFailurePolicy = 'doNotSaveAutoBill'; // if validation tx fails, do not save AutoBill
$validateForFuturePayment = true; // if no payment is due at subscription start, we still want the payment method validated with a 1 RM transaction
$minChargebackProbability = 100; // not using fraud screening
$ignoreAvsPolicy = true; // Does not apply to MOLPay payment method
$ignoreCvnPolicy = true; // Does not apply to MOLPay payment method
$sparseReturnDescriptor = ''; // we want the whole Transaction object back
// Campaign Code can also be passed in to the call as a param to apply to all eligible items
$campaignCode = null;  // we will populate campaign code on individual items if necessary
$dryRun = false;
$response = $abill->update($sparseReturnDescriptor, $immediateAuthFailurePolicy, 
							$validateForFuturePayment, $minChargebackProbability,
							 $ignoreAvsPolicy, $ignoreCvnPolicy, $campaignCode, $dryRun );
if ($response['returnCode'] == 200) {
       $returned_tx = $response['data']->initialTransaction;
        if ($returned_tx->statusLog[0]->status == 'AuthorizationPending') {
        	print ("Visit the following URL to complete payment \n");
        	print($returned_tx->statusLog[0]->hostedPageStatus->redirectUrl . "\n\n");
        }
        else if ($returned_tx->statusLog[0]->status == 'Cancelled') {
        	print ("Payment did not complete. Please start purchase again \n");
        	print (" soap id: " . $response['data']->return->soapId . "\n");
        }
        else {
        	print ("Error: Unexpected initial transaction status\n");
        	print ("Vindicia soap id: " . $response['data']->return->soapId . "\n");
        }
} else
{
	print ("Subscription creation failed: \n");
	print ("Error: Vindicia return code: " . $response['returnCode'] .  "  ");
	print (" return string: " . $response['returnString'] .  "  ");
	print (" soap id: " . $response['data']->return->soapId . "\n");
}


?>
