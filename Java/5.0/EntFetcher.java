import com.vindicia.client.*;
import com.vindicia.soap.v5_0.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntFetcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub SKD00001847
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL = "https://soap.staging.sj.vindicia.com";
		com.vindicia.client.ClientConstants.SOAP_LOGIN = "XXXXX_soap";
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = "XXXXXXXXXXXXXXXXXXXXXX";
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = 2500000;
		//String accountFilter = null; 
		
		while (true) {
			try {
				
				java.util.Calendar startTs = java.util.Calendar.getInstance();
				startTs.setTimeInMillis(System.currentTimeMillis() - (30*1000));
				
				java.util.Calendar endTs = java.util.Calendar.getInstance();
				endTs.setTimeInMillis(System.currentTimeMillis());

				System.out.println("Fetching entitlement changes since " + getPrintDate(startTs) );
				com.vindicia.client.Entitlement[] ents = com.vindicia.client.Entitlement.fetchDeltaSince(startTs, 0, 1000, endTs);

				if (ents != null ) {
					for (int i = 0; i < ents.length; i++) {

							System.out.print("Ent id: " + ents[i].getMerchantEntitlementId() + "  ") ;
							System.out.print("Ent active: " + ents[i].getActive() + "  ") ;
							System.out.print("  acct id: " + ents[i].getAccount().getMerchantAccountId() + "  ") ;
							System.out.print("  ent descr " + ents[i].getDescription());
							System.out.print("  product " + ents[i].getMerchantProductId());
							System.out.print("  ab id " + ents[i].getMerchantAutoBillId());
							if (ents[i].getStartTimestamp() != null) {
								System.out.println("  Start ts: " + getPrintDate(ents[i].getStartTimestamp()) + "  ") ;
							}
							else {
								System.out.println("  Start ts: null");
							}
							if (ents[i].getEndTimestamp() != null) {
								System.out.println("  End ts: " + getPrintDate(ents[i].getEndTimestamp()) + "  ") ;
							}
							else {
								System.out.println("  End ts: null");

	
						}
	
					}
				}
				else {
					System.out.println("Found nothing ");
				}

				
					
				
	
				
			}
			catch (VindiciaReturnException vre) {
				
				System.out.println("Transaction fetch failed failed, return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'");
				System.out.println("Soap id " + vre.getSoapId());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Sleeping for 30 seconds .......");
			Thread.sleep(30000);
		}
		

	} // end infinite while loop
	
	protected static String getPrintDate( java.util.Calendar cal ) {
		String dt = "";
		java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return df.format(cal.getTime());

		
	}

}
