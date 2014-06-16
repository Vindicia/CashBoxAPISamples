import java.util.ResourceBundle;

	
	/* 
	 * This sample illustrates how to change a subscription (AutoBill) so it has a new Product and a new 
	 * Billing Plan beginning today.
	 *
	 * The process involves calling AutoBill.modify() that allows for prorated credit for
	 * the product and the billing plan being removed per remaining number of days in the
	 * paid cycle of the current plan. Debit portion has full charge for the first cycle
	 * of the new billing plan, and the next renewal date depends on duration of that cycle.
	 */


public class AutoBillIModifyBillingPlanChange {

	
	public static void main(String[] args) {
		ResourceBundle rb = ResourceBundle.getBundle("properties.Environment");
		com.vindicia.client.ClientConstants.SOAP_LOGIN = rb.getString("soap_login");
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = rb.getString("soap_password");
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL= rb.getString("soap_url");
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = Integer.parseInt(rb.getString("soap_timeout"));
		
		com.vindicia.client.AutoBill abill = new com.vindicia.client.AutoBill();
		abill.setMerchantAutoBillId("vin_test_abill1396445387013"); // this is ID of the AutoBill you want to modify
		
		com.vindicia.client.Product productRemoved = new com.vindicia.client.Product();  // Product we want to remove
		productRemoved.setMerchantProductId("monthlySubscriptionProduct");  // we will simply identify the Product by its ID. No need to fetch whole Product object
		
		com.vindicia.soap.v5_0.Vindicia.AutoBillItem itemRemoved = new com.vindicia.soap.v5_0.Vindicia.AutoBillItem();
		itemRemoved.setProduct(productRemoved);
		
		com.vindicia.client.Product productAdded = new com.vindicia.client.Product(); // Product we want to add
		productAdded.setMerchantProductId("annualSubcriptionProduct"); // we will simply identify the Product by its ID. No need to fetch it.
		
		com.vindicia.soap.v5_0.Vindicia.AutoBillItem itemAdded = new com.vindicia.soap.v5_0.Vindicia.AutoBillItem();
		itemAdded.setProduct(productAdded);
		itemAdded.setMerchantAutoBillItemId("item-" + System.currentTimeMillis());  // unique item id
		//itemAdded.setCampaignCode("OTT");  // campaign code, if any, for the product being added can be specified here
		
		com.vindicia.soap.v5_0.Vindicia.AutoBillItemModification modification = new com.vindicia.soap.v5_0.Vindicia.AutoBillItemModification();
		modification.setRemoveAutoBillItem(itemRemoved);
		modification.setAddAutoBillItem(itemAdded);
		
		com.vindicia.client.BillingPlan newPlan = new com.vindicia.client.BillingPlan();
		newPlan.setMerchantBillingPlanId("annual-plan"); // Just pass the ID of the new billing plan customer is switching to
		
		com.vindicia.soap.v5_0.Vindicia.AutoBillItemModification[] modifications = new com.vindicia.soap.v5_0.Vindicia.AutoBillItemModification[] {modification};
		
		try {
			// Now make the 
			com.vindicia.client.AutoBillModifyReturn abmr = abill.modify (
																	true,  	// Give prorated credit for product being removed
																	"today", // make the change effective today
																	newPlan, // Changing the Billing plan also
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
					
					System.out.println("Total refund amount due to modification: $" + refunds[0].getAmount() + " . See debit and credit details below.");
					

				}
				else  {
					// Refund is null, so there must be net cost to the customer
					if (tx != null ) {
						System.out.println("Total charge processed for modification: $" + tx.getAmount() + ". See debit and credit details below.");
						
					}
					
					
				}
				
				if (abill.getNextBilling() != null)
					System.out.println("Next billing will be on " + getPrintableDate(abill.getNextBilling().getTimestamp()) + " for $" + abill.getNextBilling().getAmount().floatValue() );
				
				// To give customer break down of how we reached refund amount, we need to parse the $0 transaction that accompanies this
				
				if (tx != null ) {
					printTxDetails(tx);
				}
				else {
					System.out.println("No modification transaction available to provide breakdown of charges");
				}
				
				
			}

		}
		catch (com.vindicia.client.VindiciaReturnException vre ) {
			// If we are here, modify call failed - most common response code is 400
			System.out.println("Modification failed, return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + "  soap id: " + vre.getSoapId());
		}
		catch (Exception ex) {
			// If we are, we encountered system exception
			System.out.println("Modification failed");
			ex.printStackTrace();
		}
	}
	
	public static void printTxDetails(com.vindicia.soap.v5_0.Vindicia.Transaction tx) {
		
		System.out.println("\nModification Transaction (ID " + tx.getMerchantTransactionId() + ") : Line item by line item details: ") ;
		
		// Transaction line items
		for (int j = 0; j<tx.getTransactionItems().length; j++) {
			System.out.println("  Line item " + j + " details: ");
			System.out.println("      SKU: " + tx.getTransactionItems()[j].getSku());
			System.out.println("      Description: " + tx.getTransactionItems()[j].getName());
			System.out.println("      Price: $" + tx.getTransactionItems()[j].getPrice().floatValue());
			System.out.println("      Tax details for line item " + j + " : ");
			if (tx.getTransactionItems()[j].getTax() != null && tx.getTransactionItems()[j].getTax().length > 0) {
				for (int k = 0; k < tx.getTransactionItems()[j].getTax().length; k++ ) {
					System.out.println("            Tax jurisdiction: " + tx.getTransactionItems()[j].getTax()[k].getJurisdiction());
					System.out.println("            Tax description: " + tx.getTransactionItems()[j].getTax()[k].getName());
					System.out.println("            Tax amt: $" + tx.getTransactionItems()[j].getTax()[k].getAmount().floatValue() + "\n");
				}
			}
			else {
				System.out.println("            None available");
			}
			System.out.println("\n");
			

		}
	}
	
	protected static String getPrintableDate( java.util.Calendar cal ) {
		java.text.DateFormat df = 
			new java.text.SimpleDateFormat("EEE, d MMM yyyy");
		return df.format(cal.getTime());
	}

}
