import java.util.ResourceBundle;

import com.vindicia.client.*;

/**
 * 
 * This sample illustrates how you can cancel a subscription and immediately disentitle a customer.
 * The sample also shows how to settle the subscription to issue a prorated refund to the customer.
 * It also handles the case where the subscription may have a billing transaction in progress.
 *
 */


public class SubscriptionCancellationWithSettlement {

	public static void main(String[] args) {
	
		ResourceBundle rb = ResourceBundle.getBundle("properties.Environment");
		com.vindicia.client.ClientConstants.SOAP_LOGIN = rb.getString("soap_login");
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = rb.getString("soap_password");
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL= rb.getString("soap_url");
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = Integer.parseInt(rb.getString("soap_timeout"));
		
		
		String subscriptionId = "vin_test_ab_1401681878734";
		

		boolean isLatestTxInFlight = false;
		String latestTransactionId = null;
		
		com.vindicia.soap.v5_0.Vindicia.Transaction latestTx = null;
		try {
			com.vindicia.client.AutoBill sub = new com.vindicia.client.AutoBill();
			sub.setMerchantAutoBillId(subscriptionId);
				
			try {
				
				// Fetch Transactions processed for this AutoBill
				// We want to do this to make sure the cancellation day (today) may be renewal or Autobill start day.
				// So there may be a billing (transaction) in progress. 
				// We want to make sure the billing in progress is canceled or appropriately refunded after the
				// subscription canceled
				
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
					
					if (latestTx != null && 
							(latestTx.getStatusLog()[0].getStatus().equals(com.vindicia.soap.v5_0.Vindicia.TransactionStatusType.Authorized) ||
							latestTx.getStatusLog()[0].getStatus().equals(com.vindicia.soap.v5_0.Vindicia.TransactionStatusType.New))
							)
					{
						// So this means we have the latest transaction in progress. 
						// This tells us that we should cancel with 'settlement' turned on and
						// separately issue refund.
						
						isLatestTxInFlight = true;
						latestTransactionId = latestTx.getMerchantTransactionId();
					}
					
				}

				
			}
						
			catch (VindiciaReturnException vre) {
					
				System.out.println("Subscription's transactions could not be fetched " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
				System.out.println("Cancellation of subscription ID " + subscriptionId + " failed");
				return;
				
			}
			catch (Exception e) {
				System.out.print("Subscription's transactions could not be fetched. " );
				System.out.println("Cancellation of subscription ID " + subscriptionId + " failed");
				e.printStackTrace();
				return;
			}
			if (isLatestTxInFlight) {
				com.vindicia.client.AutoBillCancelReturn abcRet = sub.cancel(true, // disentitle customer immediately
						true, // force - this is a No-op flag unless you are using minimum commitment period feature
						false  // we are not settling
						);
				
				System.out.println("Subscription ID " + sub.getMerchantAutoBillId() + " has been cancelled!");
				
				// if we are here, cancellation was successful - this process may have canceled the pending transaction in New or
				// authorized status. Let's check that by re-fetching the latest transaction
				try {
					com.vindicia.client.Transaction latestTxAgain = com.vindicia.client.Transaction.fetchByMerchantTransactionId(latestTransactionId);
					// if we are here, we got the latest transaction
					if(latestTxAgain != null && (latestTxAgain.getStatusLog()[0].getStatus().equals(com.vindicia.soap.v5_0.Vindicia.TransactionStatusType.Authorized) ||
							latestTx.getStatusLog()[0].getStatus().equals(com.vindicia.soap.v5_0.Vindicia.TransactionStatusType.New))) {
						
						System.out.println("Attempting to issue refund against pending transaction ID " + latestTxAgain.getMerchantTransactionId() + ".");
						
						com.vindicia.client.Refund ref = new com.vindicia.client.Refund();
						ref.setAmount(latestTxAgain.getAmount());
						ref.setTransaction(latestTxAgain);
						ref.setCurrency(latestTxAgain.getCurrency());
						ref.setNote("Refunding due to subscription cancellation at " + getPrintableDate(java.util.Calendar.getInstance()));
						try {
							com.vindicia.client.Refund[] issuedRefunds = com.vindicia.client.Refund.perform(new com.vindicia.client.Refund[] {ref});
							
							// if we are here, we got a 200 response code
							System.out.println("Issued refund of $" + issuedRefunds[0].getAmount().floatValue() + " against pending transaction ID " + latestTxAgain.getMerchantTransactionId());
						}
						catch (VindiciaReturnException vre) {
							
							System.out.println("Could not issue refund agains pending transaction " +  latestTxAgain.getMerchantTransactionId() + ". Vindicia return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
							
						}
						catch (Exception e) {
							System.out.println("Could not issue refund agains pending transaction " +  latestTxAgain.getMerchantTransactionId());
							e.printStackTrace();
							
						}
						
					}
					else {
						// we do not need to do anything since pending transaction was cancelled by system during autobill cancellation process
					}
							
				}
				catch (VindiciaReturnException vre) {
					
					System.out.println("Could not fetch pending transaction, return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
					
				}
				catch (Exception e) {
					System.out.println("Could not fetch pending transaction" );
					e.printStackTrace();
					
				}
			}
			else {
				
				// We have nothing to settle
				sub.cancel(true, // disentitle customer immediately
						true, // force - this is a No-op flag unless you are using minimum commitment period feature
						false // nothing to settle
						);
				
				// if we are here, cancellation was successful
				System.out.println("Subscription ID " + sub.getMerchantAutoBillId() + " has been cancelled!");
				
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
