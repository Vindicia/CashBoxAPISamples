	
	/* 
	 * This sample illustrates how to replace an AutoBill's Product with another Product
	 * The process involves calling AutoBill.modify() that allows for prorated credit for
	 * the product being removed and prorated debit for the product being added
	 */


public class AutoBillIModifySample1 {

	
	public static void main(String[] args) {
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL = "https://soap.prodtest.sj.vindicia.com";
		com.vindicia.client.ClientConstants.SOAP_LOGIN = "XXXXXXXXXXXX_soap";
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = "XXXXXXXXXXXXXXXXXXXXX";
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = 250000;
		
		
		com.vindicia.client.AutoBill abill = new com.vindicia.client.AutoBill();
		abill.setMerchantAutoBillId("vin_test_ab_1401681425960"); // this is ID of the AutoBill you want to modify
		
		com.vindicia.client.Product productRemoved = new com.vindicia.client.Product();  // Product we want to remove
		productRemoved.setMerchantProductId("monthlyProduct2");  // we will simply identify the Product by its ID. No need to fetch it.
		
		com.vindicia.soap.v5_0.Vindicia.AutoBillItem itemRemoved = new com.vindicia.soap.v5_0.Vindicia.AutoBillItem();
		itemRemoved.setProduct(productRemoved);
		
		com.vindicia.client.Product productAdded = new com.vindicia.client.Product(); // Product we want to add
		productAdded.setMerchantProductId("monthlyProduct1"); // we will simply identify the Product by its ID. No need to fetch it.
		
		com.vindicia.soap.v5_0.Vindicia.AutoBillItem itemAdded = new com.vindicia.soap.v5_0.Vindicia.AutoBillItem();
		itemAdded.setProduct(productAdded);
		itemAdded.setMerchantAutoBillItemId("item-" + System.currentTimeMillis());  // unique item id
		//itemAdded.setCampaignCode("OTT");  // campaign code, if any, for the product being added can be specified here
		
		com.vindicia.soap.v5_0.Vindicia.AutoBillItemModification modification = new com.vindicia.soap.v5_0.Vindicia.AutoBillItemModification();
		modification.setRemoveAutoBillItem(itemRemoved);
		modification.setAddAutoBillItem(itemAdded);
		
		com.vindicia.soap.v5_0.Vindicia.AutoBillItemModification[] modifications = new com.vindicia.soap.v5_0.Vindicia.AutoBillItemModification[] {modification};
		
		try {
			// Now make the 
			com.vindicia.client.AutoBillModifyReturn abmr = abill.modify (
																	true,  	// Give prorated credit for product being removed
																			// and calculate prorated debit amount for product being added
																	"today", // make the change effective today
																	null, // Billing plan will not change
																	modifications,
																	false  	// set this to true for a 'dry run' to show a 
																			// preview of customer charges incurred
																	);
			
			// if we are here, we got a 200 - success response back. But let's confirm again:
			if (abmr.getReturnObject().getReturnCode().getValue() == 200) {
				// We got a 200 response code back
				
				// The modification may result a net refund or net charge to the customer depending on total of credit and debit
				
				com.vindicia.soap.v5_0.Vindicia.Refund[] refunds = abmr.getRefunds();
				com.vindicia.soap.v5_0.Vindicia.Transaction tx = abmr.getTransaction();
				
				if (refunds != null && refunds.length > 0 ) {
					
					// Modification resulted into net refund
					// In most cases there should be only one refund
					
					System.out.println("Total refund amount due to modification: " + refunds[0].getAmount() + " . See break down below: ");
					
					// To give customer break down of how we reached refund amount, we need to parse the $0 transaction that accompanies this
					
					if (tx != null ) {
						printTxDetails(tx);
					}
				}
				else  {
					// Refund is null, so there must be net cost to the customer
					if (tx != null ) {
						System.out.println("Total charge processed during modification: $" + tx.getAmount() + " - see break down below:");
						printTxDetails(tx);
					}
					
				}
			}

		}
		catch (com.vindicia.client.VindiciaReturnException vre ) {
			// If we are here, modify call failed
			System.out.println("Modification failed, return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + "  soap id: " + vre.getSoapId());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void printTxDetails(com.vindicia.soap.v5_0.Vindicia.Transaction tx) {
		System.out.print("\n Transaction id: " + tx.getMerchantTransactionId() + ",  ") ;
		System.out.println("Transaction status: " + tx.getStatusLog()[0].getStatus().getValue() + ",  Processor response code: " +  tx.getStatusLog()[0].getCreditCardStatus().getAuthCode() + "\n\n");
		// Transaction line items
		for (int j = 0; j<tx.getTransactionItems().length; j++) {
			System.out.println("  Line item " + j);
			System.out.println("      SKU: " + tx.getTransactionItems()[j].getSku());
			System.out.println("      Description: " + tx.getTransactionItems()[j].getName());
			System.out.println("      Price: $" + tx.getTransactionItems()[j].getPrice().floatValue());
			System.out.println("      Taxes:");
			if (tx.getTransactionItems()[j].getTax() != null && tx.getTransactionItems()[j].getTax().length > 0) {
				for (int k = 0; k < tx.getTransactionItems()[j].getTax().length; k++ ) {
					System.out.println("        Tax jurisdiction: " + tx.getTransactionItems()[j].getTax()[k].getJurisdiction());
					System.out.println("        Tax description: " + tx.getTransactionItems()[j].getTax()[k].getName());
					System.out.println("        Tax amt: $" + tx.getTransactionItems()[j].getTax()[k].getAmount().floatValue() + "\n");
				}
			}
			else {
				System.out.println("        None");
			}
			System.out.println("\n");
			

		}
	}

}
