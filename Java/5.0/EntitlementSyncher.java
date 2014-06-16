/*
 * This sample illustrates how to fetch Entitlements from Vindicia as they are created and change in status in CashBox
 * The fetchDeltaSince() call supported by the CashBox API allows you to retrieve Entitlement objects from
 * CashBox as they are generated or undergo end date changes. Typically you should use this job to detect entitlement
 * end date changes caused by renewal billing failures and cancellations not initiated by your application (e.g. done
 * by agents using the CashBox portal)
 * 
 * This sample assumes you are using CashBox's OPTIMISTIC ENTITLEMENT MODEL.
 * 
 * The key principles in using this API is to fine tune its calling parameters since it is likely to return massive
 * amounts of data over SOAP or take too long to return depending on the input parameters and volume of the 
 * data set CashBox database must parse to return the data you are seeking.
 * 
 * This sample includes some best practice recommendations in making this API call. Please refer to the
 * comments within the sample code below.
 * 
 */

import java.util.ResourceBundle;

import com.vindicia.client.*;
import com.vindicia.soap.v5_0.*;

public class EntitlementSyncher {


	public static void main(String[] args) {
		
		ResourceBundle rb = ResourceBundle.getBundle("properties.Environment");
		com.vindicia.client.ClientConstants.SOAP_LOGIN = rb.getString("soap_login");
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = rb.getString("soap_password");
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL= rb.getString("soap_url");
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = 300000; // keep time out longer for bulk data fetch requests 
		
		// Typically Entitlement syncing is run as cron or similarly periodically scheduled job.
		// During each run of the job, Entitlements that are created or are ending and thus underwent a change
		// over the given time interval are retrieved using a series of fetchDeltaSince
		// calls made in a loop with that time interval as input, but incrementing the page number for
		// each iteration of the loop. Size (number of records returned) of a page is passed in as
		// an input parameter to each call in the series and should remain constant for the series.

		
		
		// In this sample we fetch all entitlements that underwent change
		// between 8 am and 10 am on June 2, 2014. This job could be run, for example
		// at 11 am on June 6, 2014 to fetch entitlement changes between those 2 hours
		// Instead of using a time window that contains a current time using a past time window
		// helps in making sure the fetching does not occur while changes are still
		// taking place in the Vindicia database
		
		// The fetch window in this example is 2 hours long. Longer the time window, longer the query
		// will take on the server side to return - correspondingly it is advisable to increase the timeout
		// set on your client library. Typically most Vindicia merchants run fetch jobs several times
		// a day - each job querying for entitlement changes ranging over several hours in the past
		
		long fetchWindowInMilliSec = 24*3600*1000; // 2 hours - make this a configurable parameter of your job
		
		// set your time zone in the input request
		// This will help CashBox translate your time into US Pacific time which it uses internally
		
		java.util.TimeZone timeZone = java.util.TimeZone.getTimeZone("America/Los_Angeles"); 
																							
		java.util.Calendar fetchWindowStart = java.util.Calendar.getInstance();
		fetchWindowStart.set(2014, 5, 2, 8, 0, 0);  // This time should be the end of your last fetch time window
		fetchWindowStart.setTimeZone(timeZone);
		
		
		java.util.Calendar fetchWindowEnd = java.util.Calendar.getInstance();
		fetchWindowEnd.setTimeInMillis(fetchWindowStart.getTimeInMillis() + fetchWindowInMilliSec);  // store this time locally when the fetch job completes
		fetchWindowEnd.setTimeZone(timeZone);
		
		int page = 0; // start at the very first page
		int pageSize = 200;  // make this an externally configurable parameter for your fetch job, 100-200 is a typical number
		int timeoutRetryCount = 0;
		
		com.vindicia.client.Entitlement[] ents = null;
		
		System.out.println("Fetching entitlement changes between " + getPrintableDate(fetchWindowStart) + " and " + getPrintableDate(fetchWindowEnd));
		do {
			try {
				ents = com.vindicia.client.Entitlement.fetchDeltaSince(fetchWindowStart, page, pageSize, fetchWindowEnd);
			}
			catch (VindiciaReturnException vre) {
				// This indicates we got a non-200 response from CashBox which is unexpected - stop the job
				System.out.println("Entitlement fetch job failed on page number " + page + ", return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
				break;
			}
			catch (Exception e) {
						
				if (e.getCause().getCause().toString().contains("Read timed out") && timeoutRetryCount < 1) {
					
					// Since the exception is due to time out , we will retry after a few seconds.

					System.out.println("Fetch timed out on page " + page + " . Waiting for 10 seconds before retrying retrieval of page " + page);
					try {
						
						Thread.sleep(10000);

					} catch (InterruptedException ie) {
						System.out.println("Sleep interrupted " + page);   
					}

					System.out.println("Wait over ... retrying fetching page " + page);
					timeoutRetryCount++; // we want to retry only once more, so need to keep count
					continue; // reenter the loop with same page number
					
				}
				else if (e.getCause().getCause().toString().contains("Read timed out") && timeoutRetryCount >= 1) {
					// We have got a timeout again  retry - stop the job
					System.out.println("Second timeout on page " + page + ". Transaction fetch failed on page number " + page );
					e.printStackTrace();
					break;
				}
				else {
					// We do not know reason for exception - stop the job
					System.out.println("Transaction fetch failed on page number " + page );
					e.printStackTrace();
					break;
				}
				
			}
			if (ents != null ) {
				for (int i = 0; i < ents.length; i++) {
					// The entitlement ID indicates what privilege the customer will get/lose
					System.out.print("Entitlement ID: " + ents[i].getMerchantEntitlementId() + ",  ") ;
					
					// Customer Account ID indicates which customer is getting or losing the privilege
					System.out.print("  Customer Account ID: " + ents[i].getAccount().getMerchantAccountId() + ",  ") ;
					
					if (ents[i].getDescription() != null)
						System.out.print("  Entitlement Description " + ents[i].getDescription()); // optional description of the entitlement
					System.out.print(",  Product " + ents[i].getMerchantProductId()); // ID of the product that granted this entitlement to the customer
					System.out.print(",  Subscription id " + ents[i].getMerchantAutoBillId()); // ID of the subscription that granted this entitlement
					if (ents[i].getStartTimestamp() != null) {
						// For seasonsal entitlements pay attention to the start date - it could be in future
						// store the future start date in your local store and allow customer access on
						// that date onwards
						
						System.out.println("  Start timestamp: " + getPrintableDate(ents[i].getStartTimestamp()) + "  ") ;
					}
					else {
						System.out.println("  Start timestamp: null");
					}
					if (ents[i].getEndTimestamp() != null) {
						// This indicates billing failure, last subscription period where end is known, or cancellation
						
						// Customer's entitlement should end on this date - which could be in future or today
						// unless there is another update from CashBox indicating otherwise. If in future store it locally
						// and keep customer granting access until that date. If today, it should end at 23:59:59 pacific time
						
						System.out.println("  End ts: " + getPrintableDate(ents[i].getEndTimestamp()) + "  ") ;
					}
					else {
						// Per optimistic entitlement model this means customer entitled forever 
						// and at this time customer we do not know when the entitlement will end
					
						// This indicates start of a new subscription, a new purchase. But most
						// likely you already have that information locally stored when customer
						// made the purchase
						
						System.out.println("  No End timestamp available. Customer is entitled forever");

					}

				}
				page++; 
				timeoutRetryCount = 0; // reset for next page
			}
			else {
				// This means we are done fetching all transactions for this job
				System.out.println("Entitlement fetch did not return any entitlements on page number " + page );
				break;
			}
		} while(true);
				
	
	}
	
	protected static String getPrintableDate( java.util.Calendar cal ) {
		java.text.DateFormat df = new java.text.SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
		return df.format(cal.getTime());
	}
	



}
