<pre>
<?php

#-----------------------------------------------------------------------------------
#
#	UC-003 	Update Credit Card On File For a Subscription
#
#		User Story
#
#		As a Merchant System I want to update the credit card payment method on file
#		for all existing subscriptions that use the payment method on file so that
#		billing continues uninterrupted on the new credit card.
#
#
#		Pre-Condition:
#
#			Customer account identified (in $merchantAccountId below)
#
#		Files Used:
#
#				Requires functionality for HOA Account_UpdatePaymentMethod
#				in file hoaAccountUpdatePaymentMethod.php
#
#-----------------------------------------------------------------------------------

require_once("hoaAccountUpdatePaymentMethod.php");


$merchantAccountId = 'testaccount5861';
# $merchantAccountId = 'TestAccount-88663';

$account =  new Account();
$account->setMerchantAccountId($merchantAccountId);

$autobill = new AutoBill();
$response = $autobill->fetchByAccount($account, false);
print_r($response);

if ($response['returnCode'] == 200) {
	$targetAutoBill = $response['data']->autobills[0];
	$paymentMethod = $targetAutoBill->paymentMethod;
	$merchantPaymentMethodId = $paymentMethod->merchantPaymentMethodId;
	print "\$merchantPaymentMethodId=$merchantPaymentMethodId" . PHP_EOL;

	$results = hoaAccountUpdatePaymentMethod(
		$merchantAccountId,
		$merchantPaymentMethodId);

	$apiReturnCode = $results['apiReturnCode'];
	$validated = $results['validated'];
	print "results: apiReturnCode=$apiReturnCode, validated=$validated" . PHP_EOL;
	
	if ( ($apiReturnCode == '200') && ($validated == '1') ) {
		print "Card successfully validated when updated.  Now check if AutoBill re-activated:" . PHP_EOL;

		$entitlement = new Entitlement();
		$response = $entitlement->fetchByAccount($account, true, false);
		print_r($response);

		$now = strtotime('now');		# current time

		$entitlements = $response['data']->entitlements;
		if ( isset($entitlements) && is_array($entitlements) ) {
			foreach ($entitlements as $index => $entitlement) {
				# print "\n\$index = $index\n";
				print "entitlements[" . $index . "]: = \n";
				# print_r ($entitlement);
				print "endTimestamp = " . $entitlement->endTimestamp . "\n";
			
				if ( empty($entitlement->endTimestamp) || is_null($entitlement->endTimestamp)
						|| ( strtotime($entitlement->endTimestamp) > $now )  ) {
					print "...entitled!  ==> subscription activated successfully.\n";
					return;
				}
			}
		}
		print "Although Card was validated, user is not entitled.  Create a replacement AutoBill:\n";
		
		# retrieve values from target AutoBill to use to create replacement:
		#
		# 	$account, $merchantPaymentMethodId already set above
		#
		$merchantAutoBillId = $targetAutoBill->merchantAutoBillId;
		# bump merchantAutoBillId to make it unique:
		$merchantAutoBillId = $merchantAutoBillId . "_2";
		$merchantBillingPlanId = $targetAutoBill->billingPlan->merchantBillingPlanId;
		$merchantProductId = $targetAutoBill->items[0]->product->merchantProductId;
		$currency = $targetAutoBill->currency;

		createAutoBill($account, $merchantPaymentMethodId, $merchantAutoBillId,
								$merchantBillingPlanId, $merchantProductId, $currency);

	}
} else {
		print "Card was not validated.  Please request customer to supply another Card." . PHP_EOL;
		return;
}


function createAutoBill($account, $merchantPaymentMethodId, $merchantAutoBillId,
							$merchantBillingPlanId, $merchantProductId, $currency)
{
	$autobill = new AutoBill();
	$autobill->setAccount($account);		# same Account
		
	# AutoBills can have multiple products each in an AutoBillItem as an array:
	$item = new AutoBillItem();
	$item->setIndex(0);
	# set product to an existing product
	$product = new Product();
	$product->setMerchantProductId($merchantProductId);
	$item->setProduct($product);	# set the Product in the AutoBillItem
	# WSDL AutoBill.items data member is set using PHP method AutoBill.setItems()
	$response = $autobill->setItems(array($item));
	# print_r ($response);
		
	$autobill->setMerchantAutoBillId($merchantAutoBillId);
	$autobill->setCustomerAutoBillName($merchantAutoBillId);

	# use same PaymentMethod that just validated successfully
	$pm = new PaymentMethod();
	$pm->setMerchantPaymentMethodId($merchantPaymentMethodId);
	$autobill->setPaymentMethod($pm);
	$autobill->setCurrency($currency);

	# set billing plan to existing billing plan
	$billingplan = new BillingPlan();
	$billingplan->setMerchantBillingPlanId($merchantBillingPlanId);
	$autobill->setBillingPlan($billingplan);


	# AutoBill.update() method parameters:
	$validate = false; // through 8.0
	$fraudScore = 100;	// Use this to accept cards involved in chargeback (i.e. Fraud Score=100)

	$minChargebackProbability = $fraudScore;
	$ignoreAvsPolicy = true;
	$ignoreCvnPolicy = true;
	$campaignCode = "";
	$dryrun = false;

	# 9.0 parameters
	$immediateAuthFailurePolicy = 'doNotSaveAutoBill';
	$validateForFuturePayment = $validate;

	print("\nmerchantAccountId=" . $account->getMerchantAccountId() . "\n");
	print("\tmerchantAutoBillId=$merchantAutoBillId\n");
	print("\t\tmerchantProductId=$product->merchantProductId,");
	print("\tmerchantBillingPlanId=$merchantBillingPlanId\n");
	print("\tminChargeBackProbability=$minChargebackProbability\n\n");

	$response = $autobill->update('SucceedIgnore', $validate,
						$minChargebackProbability,
						$ignoreAvsPolicy, $ignoreCvnPolicy,
						$campaignCode, $dryrun);	// 5.0+

	# $response = $autobill->update($immediateAuthFailurePolicy, $validateForFuturePayment,
	#					$minChargebackProbability,
	# 					$ignoreAvsPolicy, $ignoreCvnPolicy,
	#					$campaignCode, $dryrun);	// 9.0
	echo "\n";

	# print_r ($response);
	
	$createAutoBill_soapId = $response['data']->return->soapId;
	print "createAutoBill(): soapId = " . $createAutoBill_soapId;

}

?>
</pre>
