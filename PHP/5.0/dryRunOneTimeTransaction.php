<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$testId = rand(1, 1000000); // random number for some unique IDs

$tx = new Transaction();

$tx->setMerchantTransactionId('DRYRUN-' . $testId);

$acct = new Account();
$acct->setMerchantAccountId('jdoe101'); // existing customer account ID

$tx->setAccount($acct);

$tx->setCurrency('USD');

$txItem1 = new TransactionItem();
$txItem1->setSku('ppv-movie-us-prem');
$txItem1->setName('Premium Pay-per-view movie (English)');
$txItem1->setPrice(5.99);
$txItem1->setQuantity(1);
$txItem1->setTaxClassification('DM030000');
$txItem1->setCampaignCode('PPV2015US');

$txItem2 = new TransactionItem();
$txItem2->setSku('smAccess2015');
$txItem2->setName('Social Media Chat Access');
$txItem2->setPrice(2.00);
$txItem2->setQuantity(1);
$txItem2->setTaxClassification('D0000000');

$tx->setTransactionItems(array($txItem1, $txItem2));

$addr = new Address();

$addr->setAddr1('809 Cuesta Dr');
$addr->setCity('Mountain View');
$addr->setDistrict('CA');
$addr->setPostalCode('94040');
$addr->setCountry('US');

$tx->setShippingAddress($addr); // for tax calculation purposes

$response = $tx->auth(100,   // fraud screen disabled for dry run
					false,   // sending of email notification disabled for dry run
					null,     // passing campaign code on each line item
					true);   // dry run
if ($response['returnCode'] == 200) {
	print "Successfully dry ran transaction. Call SOAP ID" .  $response['data']->return->soapId . "\n";

	// Present order summary to the customer

	$retTx = $response['data']->transaction;

	$orderTotal = $retTx->getAmount();
	$orderCurrency = $retTx->getCurrency();

	// iterate over line items to get discounts and taxes

	$retTxItems = $retTx->getTransactionItems();

	foreach ($retTxItems as $retTxItem) {
		if ($retTxItem->getName() != 'Total Tax' &&  strpos($retTxItem->getName(), 'Discount for') === false) {
			print ("Product: "  .  $retTxItem->getName() );
			print ("	" . $retTxItem->getPrice() . " " . $retTx->getCurrency() . "\n");
			// taxes
			print ("    Taxes: \n");
			$itemTaxTotal = 0.0;
			$taxes = $retTxItem->getTax();
			if (!is_null($taxes) ) { 
				foreach ($taxes as $tax) {
					print ("                   " . $tax->getName() . "     " . $tax->getAmount() . " " . $retTx->getCurrency() . "\n");
					$itemTaxTotal = $itemTaxTotal + $tax->getAmount();

				}
				print ("	           ----------------\n");
			}
			print ("	           Tax total: " . $itemTaxTotal . " " . $retTx->getCurrency() . "\n");
				
			// To get the discount line item we have to search the items with sku matching 'Discount for merchantProductId'
			$discountFound = false;
 			foreach ($retTxItems as $dsctItem) {
				if ($dsctItem->getSku() == 'Discount for merchantProductId ' . $retTxItem->getSku()) {
					$discountFound = true;
					$discountItem = $dsctItem;
					break;
				} 
			}
			if ($discountFound) {
                        	print ("    Discount:     \n");
				print ("          Campaign applied: " . $discountItem->getCampaignId() . "  " . $discountItem->getPrice() . " " . $retTx->getCurrency() . "\n");
                        	// taxes
                        	$discountTaxTotal = 0.0;
                        	$discountTaxes = $discountItem->getTax();
				if (!is_null($discountTaxes)) {
                        		foreach ($discountTaxes as $dsctTax) {
                                		print ("                   " . $dsctTax->getName() . "     " . $dsctTax->getAmount() . " " . $retTx->getCurrency() . "\n");
                                		$discountTaxTotal = $discountTaxTotal + $dsctTax->getAmount();

                        		}
                       		 	print ("                 ----------------\n");
				}
                        	print ("                Tax discount total: " . $discountTaxTotal . " " . $retTx->getCurrency() . "\n");

			}
			 
		}
	}
	print "----------------------------------\n";
	print "Total purchase price: " . $retTx->getAmount() . " " . $retTx->getCurrency() . "\n";

}
else {
	print "Transaction dry run failed";
	print "Return code: " . $response['returnCode'] . " Return string: " . $response['returnString'] .  " Call SOAP ID: " .  $response['data']->return->soapId . "\n";
}
?>
