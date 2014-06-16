/*
 * This sample illustrates how to fetch Transactions from Vindicia as they are created and change in status in CashBox
 * The fetchDeltaSince() call supported by the CashBox API allows you to retrieve full Transaction objects from
 * CashBox as they are generated and undergo status changes.
 * 
 * The key principles in using this API is to fine tune its calling parameters since it is likely to return massive
 * amounts of data over SOAP or take too long to return depending on the input parameters and volume of the 
 * dataset CashBox database must parse to return the data you are seeking.
 * 
 * This sample includes some best practice recommendations in making this API call. Please refer to the
 * comments within the sample code below.
 * 
 */

import java.util.ResourceBundle;

import com.vindicia.client.*;
import com.vindicia.soap.v5_0.*;

public class TransactionFetcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ResourceBundle rb = ResourceBundle.getBundle("properties.Environment");
		com.vindicia.client.ClientConstants.SOAP_LOGIN = rb.getString("soap_login");
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = rb.getString("soap_password");
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL= rb.getString("soap_url");
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = 300000; // keep time out longer for bulk data fetch requests 
		
		// Typically Transaction fetches are run as cron or similarly regularly scheduled jobs
		// During each run of the job transactions that were created or changed in status
		// over a fixed time interval in the past are retrieved using a series of fetchDeltaSince
		// calls made in a loop with that time interval as input, but incrementing page number for
		// each iteration of the loop. Size (number of records returned) of a page is passed in as
		// an input parameter to each call in the series and should remain constant for the series.

		
		
		// In this sample we fetch all transactions created or changed in status
		// between 8 am and noon on June 2, 2014. This job could be run, for example
		// at 4 pm on June 6, 2014 to ensure that all transaction changes in our fetch time window
		// are in the past
		
		// The fetch window in this example is 4 hours long. Longer the time window, longer the query
		// will take on the server side to return - correspondingly you must increase the timeout
		// set on your client library. Typically most Vindicia merchants run fetch jobs several times
		// a day - each job querying for transaction changes ranging over several hours in the past
		
		long fetchWindowInMilliSec = 4*3600*1000; // 4 hours - make this a configurable parameter of your job
		
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
		int pageSize = 100;  // make this an externally configurable parameter for your fetch job, 50-100 is a typical number
		int timeoutRetryCount = 0;
		int recordTotal = 0;
		
		com.vindicia.client.Transaction[] txs = null;
		
		System.out.println("Fetching transaction changes between " + getPrintableDate(fetchWindowStart) + " and " + getPrintableDate(fetchWindowEnd));
		do {
			try {
				txs = com.vindicia.client.Transaction.fetchDeltaSince(fetchWindowStart, fetchWindowEnd, page, pageSize, null);
			}
			catch (VindiciaReturnException vre) {
					
				System.out.println("Transaction fetch failed on page number " + page + ", return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
				break;
			}
			catch (Exception e) {
						
				if (e.getCause().getCause().toString().contains("Read timed out") && timeoutRetryCount < 1) {
					
					// Since the exception is due to time out , there are two thing we can do
					// wait for a few seconds and retry retrieval of the same page
					// and dynamically increase the time out
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
					// We have got a timeout on retry - stop the job
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
			if (txs != null ) {
				for (int i = 0; i < txs.length; i++) {
					
					// Here we process each Transaction object retrieved
					// You may save various Transaction object members in your local database
					// Make sure you key your local database records with Transaction.merchantTransactionId
					// If a local record for this Transaction already exists, this may be just status update
					// for the same transaction - in which case you are interested only in the latest transaction
					// status
					
					System.out.print("Transaction ID: " + txs[i].getMerchantTransactionId() + "  ") ; // This should be the key of your local database record of the transaction
					System.out.print("Transaction total amount: $" + txs[i].getAmount().floatValue() + "  ") ;
					System.out.println("Latest Transaction status: " + txs[i].getStatusLog()[0].getStatus().getValue());
					// Transaction line items
					for (int j = 0; j<txs[i].getTransactionItems().length; j++) {
						System.out.print("  Description: " + txs[i].getTransactionItems()[j].getName());
						System.out.println("  Price: " + txs[i].getTransactionItems()[j].getPrice().floatValue());
					}
					recordTotal++;

				}
				page++; 
				timeoutRetryCount = 0; // reset for next page
			}
			else {
				// This means we are done fetching all transactions for this job
				System.out.println("Transaction fetch did not return any transactions on page number " + page );
				System.out.println("Done. Fetched total " + recordTotal + " transactions");
				recordTotal = 0 ; // reset for next run
				break;
			}
		} while(true);
				
	
	}
	
	protected static String getPrintableDate( java.util.Calendar cal ) {
		java.text.DateFormat df = new java.text.SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
		return df.format(cal.getTime());
	}
	


}
