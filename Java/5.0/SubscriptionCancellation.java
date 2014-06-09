import com.vindicia.client.*;

/**
 * 
 * This sample illustrates how you can cancel a subscription and immediately disentitle a customer.
 * The sample also shows how to settle the subscription to issue a prorated refund to the customer.
 * It also handles the case where the subscription may have a billing transaction in progress.
 *
 */


public class SubscriptionCancellation {

	public static void main(String[] args) {
	
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL = "https://soap.prodtest.sj.vindicia.com";
		com.vindicia.client.ClientConstants.SOAP_LOGIN = "XXXXXX_soap";
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = "XXXXXXXXXXXX";
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = 250000;
		
		
		String subscriptionId = "vin_test_ab_1401681878734";
		
		boolean settle = false;
		boolean isLatestTxInFlight = false;
		
		
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
					
					if (latestTx != null && latestTx.getStatusLog()[0].getStatus().equals(com.vindicia.soap.v5_0.Vindicia.TransactionStatusType.Captured)) {
							settle = true;
												
					}
					else if (latestTx != null && 
							(latestTx.getStatusLog()[0].getStatus().equals(com.vindicia.soap.v5_0.Vindicia.TransactionStatusType.Authorized) ||
							latestTx.getStatusLog()[0].getStatus().equals(com.vindicia.soap.v5_0.Vindicia.TransactionStatusType.New))
							)
					{
						// So this means we have the latest transaction in progress. 
						// This tells us that we should cancel with 'settlement' turned on and
						// separately issue refund.
						
						isLatestTxInFlight = true;
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
			
			if (settle) {
				// latest transaction for this autobill shows 'captured' status ... we can settle the subscription
				// Settlement will issue prorated refund against the captured transaction.
				
				com.vindicia.client.AutoBillCancelReturn abcRet = sub.cancel(true, // disentitle customer immediately
						true, // force - this is a No-op flag unless you are using minimum commitment period feature
						settle  // this is true, so we are settling
						);
				
				// if we are here, we got a 200 response code, which means the cancel call was successful
				
				System.out.println("Subscription ID " + sub.getMerchantAutoBillId() + " has been cancelled!");
				if (abcRet.getRefunds() != null && abcRet.getRefunds().length > 0 ) {
					System.out.println("Following refund/s have been issued for unused subscription time:");
					
					for (int i= 0; i<abcRet.getRefunds().length; i++){
						System.out.print ("Refund of $" + abcRet.getRefunds()[i].getAmount().floatValue() + ", with Refund ID: " + abcRet.getRefunds()[i].getMerchantRefundId() );
						System.out.print(" has been issued against payment (transaction ID "+ abcRet.getRefunds()[i].getTransaction().getMerchantTransactionId());
						System.out.print(") of $"+ abcRet.getRefunds()[i].getTransaction().getAmount().floatValue() + " made on ");
						System.out.println(getPrintableDate(abcRet.getRefunds()[i].getTransaction().getTimestamp()));
						
					}
					
					System.out.println("Please note that refunds may take several days to show up on your card statement");
				}
				
			}
			else if (!settle && isLatestTxInFlight) {
				com.vindicia.client.AutoBillCancelReturn abcRet = sub.cancel(true, // disentitle customer immediately
						true, // force - this is a No-op flag unless you are using minimum commitment period feature
						settle  // this is false, so we are not settling
						);
				
				System.out.println("Subscription ID " + sub.getMerchantAutoBillId() + " has been cancelled!");
				
				// if we are here, cancellation was successful - this process may have cancelled the pending transaction in New or
				// authorized status. Let's check that by refetching the latest tx
				try {
					com.vindicia.client.Transaction latestTxAgain = com.vindicia.client.Transaction.fetchByMerchantTransactionId(latestTx.getMerchantTransactionId());
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
