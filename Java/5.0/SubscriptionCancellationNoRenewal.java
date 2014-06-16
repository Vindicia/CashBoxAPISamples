import java.util.ResourceBundle;

import com.vindicia.client.*;

/**
 * 
 * This sample illustrates how you can cancel a subscription without immediately disentitling the customer.
 *
 *
 */


public class SubscriptionCancellationNoRenewal {

	public static void main(String[] args) {
	
		ResourceBundle rb = ResourceBundle.getBundle("properties.Environment");
		com.vindicia.client.ClientConstants.SOAP_LOGIN = rb.getString("soap_login");
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = rb.getString("soap_password");
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL= rb.getString("soap_url");
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = Integer.parseInt(rb.getString("soap_timeout"));
		
		
		String subscriptionId = "vin_test_abill1402692220403";
		
		com.vindicia.soap.v5_0.Vindicia.Transaction latestTx = null;
		
		try {
			com.vindicia.client.AutoBill sub = new com.vindicia.client.AutoBill();
			sub.setMerchantAutoBillId(subscriptionId);

			// Cancel the AutoBill - leave customer entitled for the current period he/she already paid for
				
			com.vindicia.client.AutoBillCancelReturn abcRet = sub.cancel(false, // do not disentitle customer immediately
						true, // force - this is a No-op flag unless you are using minimum commitment period feature
						false  // we are not settling/refunding the customer
						);
				
			// if we are here, we got a 200 response code, which means the cancel call was successful
				
			System.out.println("Subscription ID " + sub.getMerchantAutoBillId() + " has been cancelled!");
			System.out.print("Your entitlements will last through " + getPrintableDate(sub.getEndTimestamp()));
			System.out.println(". There will be no further auto-renewals.");
				
			// Cancellation will attempt to cancel any pending renewal transactions . But it may not be successful
			// if the renewal transaction is in flight (in process of being submitted to the processor and waiting for 
			// response. In that case, we want to use CashBox's feature to schedule a refund against this transaction
			// after it captures

			try {
					com.vindicia.client.Transaction[] txns = Transaction.fetchByAutobill(sub);
					
					if (txns != null && txns.length > 0) {
						for (int i=0; i< txns.length; i++) {
							if (txns[i].getTransactionItems()[0].getSku().equals("VIN_PM_VALIDATION")) {
								// This is a validation transaction, so we can ignore it
								continue;
							}
							else if (latestTx == null) {
								latestTx = txns[i];
							}
							else {
								if (latestTx.getTimestamp().before(txns[i].getTimestamp())) {
									latestTx = txns[i];
								}
							}
							
						}
					}
					// if we are here, we got the latest transaction
					// We want to worry about only transactions in the New status - because these transactions have not
					// given next cycle's entitlements to the customer - so we want to make 
					
					if(latestTx != null && 
							latestTx.getStatusLog()[0].getStatus().equals(com.vindicia.soap.v5_0.Vindicia.TransactionStatusType.New) ) {
						
						System.out.println("Attempting to issue refund against pending transaction ID " + latestTx.getMerchantTransactionId() + ".");
						
						com.vindicia.client.Refund ref = new com.vindicia.client.Refund();
						ref.setAmount(latestTx.getAmount());
						ref.setTransaction(latestTx);
						ref.setCurrency(latestTx.getCurrency());
						ref.setNote("Refunding due to subscription cancellation at " + getPrintableDate(java.util.Calendar.getInstance()));
						try {
							com.vindicia.client.Refund[] issuedRefunds = com.vindicia.client.Refund.perform(new com.vindicia.client.Refund[] {ref});
							
							// if we are here, we got a 200 response code
							System.out.println("Issued refund of $" + issuedRefunds[0].getAmount().floatValue() + " against pending transaction ID " + latestTx.getMerchantTransactionId());
						}
						catch (VindiciaReturnException vre) {
							
							System.out.println("Could not issue refund against pending transaction, if any. " +  latestTx.getMerchantTransactionId() + ". Vindicia return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
							
						}
						catch (Exception e) {
							System.out.println("Could not issue refund against pending transaction, if any. " +  latestTx.getMerchantTransactionId());
							e.printStackTrace();
							
						}
						
					}
					else {
						// We do not need to do anything since we could not
						// find a transaction in 'New' status for this AutoBill
					
					}
							
				}
				catch (VindiciaReturnException vre) {
					
					System.out.println("Could not retrieve AutoBill's pending transactions, if any, for refunding, return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
					
				}
				catch (Exception e) {
					System.out.println("Could not retrieve AutoBill's pending transactions, if any, for refunding" );
					e.printStackTrace();
					
				}
		}
	
		catch (VindiciaReturnException vre) {
				
			System.out.println("Cancellation failed, return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
			
		}
		catch (Exception e) {
			System.out.println("Cancel failed" );
			e.printStackTrace();
			
		}
		
	}
	
	// Helper function for printable date
	
	protected static String getPrintableDate( java.util.Calendar cal ) {
		java.text.DateFormat df = 
			new java.text.SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
		return df.format(cal.getTime());
	}

}
