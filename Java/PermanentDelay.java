import com.vindicia.client.*;

// 
public class PermanentDelay {

	public static void main(String[] args) {
		// TODO Auto-generated method stub SKD00001847
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL = "https://soap.prodtest.sj.vindicia.com";
		com.vindicia.client.ClientConstants.SOAP_LOGIN = "XXXXXXX_soap";
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = "XXXXXXXXXXXXX";
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = 250000;
		
		String autoBillId = "vin_test_abill1406160465445" ; // ID of the autobill we want to delay
        java.util.Date today = new java.util.Date();
        
        try {
            com.vindicia.client.AutoBill autoBill = com.vindicia.client.AutoBill.fetchByMerchantAutoBillId(autoBillId);
            if (autoBill != null) {
                com.vindicia.client.AutoBill txFetcherBill = new com.vindicia.client.AutoBill();
                txFetcherBill.setMerchantAutoBillId(autoBillId);
                com.vindicia.client.Transaction[] txs = null;
                try {
                    txs = com.vindicia.client.Transaction.fetchByAutobill(txFetcherBill);
               
                    if (txs != null && txs.length > 0 ) {
                        com.vindicia.client.Transaction latestTx = null;
                        for (int i=0; i<txs.length; i++) {
                            if (latestTx == null || latestTx.getTimestamp().getTimeInMillis() < txs[i].getTimestamp().getTimeInMillis())
                            {   
                                latestTx = txs[i];
                            }

                        }
                       
                        if (latestTx.getStatusLog()[0].getStatus().getValue().equals("Cancelled")) {
                            // The autobill is in hard or soft error
                            if (autoBill.getStatus().getValue().equals("Suspended")) {
                                if (autoBill.getEndTimestamp().getTime().getTime() < today.getTime()  ) {
                                    // autobill is dead. Do not delay.
                                }
                                else {
                                    // autobill is in hard error, but not dead yet. No next billing is scheduled . Do not delay.
                                    System.out.println("AutoBill is in hard error - can not delay");
                                }
                       
                            }
                            else {
                                // autobill is in soft error, do we want to delay?
                                // Let's say as a matter of policy we do not allow delays on subscriptions in soft error
                                System.out.println("Billing is being actively retried - can not delay");
                            }
                        }
                        else {
                            // latest transaction has a good status, let's make sure autobill is good too
                        	
                            if (autoBill.getStatus().getValue().equals("Active") && autoBill.getEndTimestamp().getTime().getTime() > today.getTime()) {
                               
                                com.vindicia.soap.v4_3.Vindicia.Transaction nextBill = autoBill.getNextBilling();
                                java.util.Calendar nextBillDate = nextBill.getTimestamp();
                                // let's say we want to delay this autobill by 10 days
                                nextBillDate.add(java.util.Calendar.DAY_OF_MONTH, 10);
                                int day = nextBillDate.get(java.util.Calendar.DAY_OF_MONTH);
                               
                                boolean delayed = false;
                                
                                java.util.Calendar delayDate = java.util.Calendar.getInstance();
                                delayDate.set(2014,7,21);  // next billing will be on Aug. 21
                                try {
                                    // make sure the movePermanently flag is set to 'true'
                                    autoBill.delayBillingToDate(delayDate.getTime(), true);
                                    // if we are here, we got a 200-OK response back
                                    delayed = true;
                                }
                                catch(VindiciaReturnException ex) {
                                    // delay failed
                                    System.out.println("Could not delay autobill, return code: " + ex.getReturnCode() + " return string: '" + ex.getMessage() + "'" + " SOAP ID:"  + ex.getSoapId());
                                }
                                if (delayed) {
                                    // Let's make 21 billing day 'sticky' so subsequent billings fall on this new anniversary date
                                    try {
                                        autoBill.changeBillingDayOfMonth(21);
                                    }
                                    catch(VindiciaReturnException ex) {
                                        // update of billing day failed
                                    }
                                }
                            }
                       
                        }
                    }
                     
                    else {
                        System.out.println("The autobill has not billed yet");
                    }
                }
               
                catch (VindiciaReturnException ex) {
                    System.out.println("Could not fetch transactions for the autobill, return code: " + ex.getReturnCode() + " return string: '" + ex.getMessage() + "'");
                   
                }
               
           
            }
            else {
                // no autobill found with given id
            }
           
        }
        catch (VindiciaReturnException vrex2) {
            System.out.println("Could not fetch autobill, return code: " + vrex2.getReturnCode() + " return string: '" + vrex2.getMessage() + "'");
           
        }
        catch (Exception ex) {
          ex.printStackTrace();
           
        }
		
	}
	

}
