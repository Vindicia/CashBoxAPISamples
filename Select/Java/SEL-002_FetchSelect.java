//
//		Use Case SEL-002: Retrieve results of Select processing
//
//		SEL-002_FetchSelect.java
//
//		This sample was built against a java library that was generated
//		using the Axis wsdl2java utility against the WSDL endpoint shown.
//
//		To run this sample it is required to first generate the java library.
//
import com.vindicia.soap.v1_0.select.*;
import com.vindicia.soap.v1_0.selecttypes.*;

import java.rmi.RemoteException;
import java.io.*; 
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.axis2.transport.http.HTTPConstants;


class SEL-002_FetchSelect {

	private static final java.util.logging.Logger log
       =
	java.util.logging.Logger.getLogger(FetchSelect.class.getName());

	static Authentication auth = new Authentication();
	static String endpoint;
	static String login;
	static String password;
	static String version;
	static String userAgent;
	
	static int timeOutInMilliSeconds;
	static SelectStub select;

    static {
		ResourceBundle rb = ResourceBundle.getBundle("properties.Environment");
		login = = rb.getString("soap_login");
		password = rb.getString("soap_password");

		endpoint = "https://soap.prodtest.sj.vindicia.com/v1.0/soap.pl";
		//endpoint = rb.getString("soap_url");

		version = "1.0";
		userAgent = "FetchSelect (Select API)";
			
		log("endpoint=" + endpoint + "\nlogin=" + login);

		auth.setLogin(login);
		auth.setPassword(password);
		auth.setVersion(version);
		auth.setUserAgent(userAgent);
		
		// Connection Properties:
		timeOutInMilliSeconds = 300000;
		
    }    

	public static void main(String[] args) { 

		String transactionId = "";
		BufferedReader in = 
			new BufferedReader(new InputStreamReader(System.in)); 
		if ( args.length > 0 ) {
			transactionId = args[0];
		}
		else {
			transactionId = "TEST" + System.currentTimeMillis();
		}
		
		if ( !( transactionId.length() > 0 ) ) {
			System.out.print("Enter transactionId: "); 
			try {transactionId = in.readLine();}
			catch(Exception e) {
				System.out.println("Caught an exception!"); 
			}
		}
		run();
	}

	public static void log(String message) {

		//System.out.println(message);
		log.severe( message );

	}
	
	public static void log(Throwable t) {

		String s = "\n";
		if ( null == t )
			s += "Throwable (null)\n" ;
		else if ( null == t.getStackTrace() )
			s += "Throwable StackTrace (null)\n";
		else { //( null != t )
			StackTraceElement[] ste = t.getStackTrace();
			for (int i=0; (null != ste) && i < ste.length; i++) {
				s += (ste[i].toString() + "\n");
			}
		}
		log( s );
	}

	public static Timestamp timestamp()
	{
		java.util.Date date= new java.util.Date();
		return ( new Timestamp(date.getTime()) );
	}
  
	public static Calendar getToday() {
		return Calendar.getInstance();  
	}
	
	public static String wrapValue(String value) {
		String left = "-[";
		String right = "]-";
		String wrappedValue = left + value + right;
		return wrappedValue;
	}
	
	public static void pause(int milliseconds)
	{
		try {
    		//thread to sleep for the specified number of milliseconds
    		Thread.sleep(milliseconds);
		} catch ( java.lang.InterruptedException ie) {
    		System.out.println(ie);
		}
	}
	
	public static int fetchResults(Calendar start, Calendar end, int pageSize, int page) throws RemoteException {

		int nResults = 0;
		FetchBillingResultsResponse response = null;
		
		log("start=" + new Timestamp(start.getTime().getTime())
					+ ", end=" + new Timestamp(end.getTime().getTime())
					+ ", pageSize=" + pageSize + ", page=" + page);
		
		FetchBillingResults fetchResults = new FetchBillingResults();
		fetchResults.setAuth(auth);
		
		fetchResults.setTimestamp(start);
		fetchResults.setEndTimestamp(end);
		fetchResults.setPage(page);
		fetchResults.setPageSize(pageSize);
		
		log("Fetching " + wrapValue(String.valueOf(pageSize)) + " Billing Results ");
		response = select.fetchBillingResults(fetchResults);
		//response = SelectHelper.fetchBillingResults(auth, start, end, pageSize, page);
		log("Completed fetchBillingResults request: start="
					+ new Timestamp(start.getTime().getTime())
					+ ", end=" + new Timestamp(end.getTime().getTime())
					+ ", pageSize=" + pageSize + ", page=" + page);
		Return ftxsReturn = response.get_return();
		log("Result=" + ftxsReturn.getReturnCode().getValue() + ", " + ftxsReturn.getReturnString());
		log("soapId=" + ftxsReturn.getSoapId());
		nResults = reportResults(response.getTransactions(), page);
		
		return nResults;

	}

	public static void actionFetchResults(int num, int numStart, int numStartMin, int numEndMin) throws RemoteException {
		log("Beginning request to fetch billing results from the last " + wrapValue(String.valueOf(num))
						+ " days, starting from " + wrapValue(String.valueOf(numStart)) + " day(s) ago, "
						+ wrapValue(String.valueOf(numStartMin)) + " minutes before current minute."
						+ " ending "
						+ wrapValue(String.valueOf(numEndMin)) + " minutes before current minute.");
		Calendar start = Calendar.getInstance();  
		start.add(Calendar.MINUTE, 0-numStartMin);
		start.add(Calendar.DATE, 0-numStart);
		//start.add(Calendar.DATE, -30);
		start.add(Calendar.DATE, 0-num);
		Calendar end = getToday();
		if (0 == numEndMin) numEndMin = numStartMin;
		end.add(Calendar.MINUTE, 0-numEndMin);
		end.add(Calendar.DATE, 0-numStart);
		int pageSize = 100;	//Integer.parseInt(Resources.getMessage("fetch.billingResults.pageSize"));
		int page = 0;
		
		boolean bFail = true;
		int numTimeouts = 0;
		int nRecords = 0;
		int nTotalRecords = 0;
		try {
			do {
				nTotalRecords += nRecords;
				do {
					try {
						nRecords = fetchResults(start, end, pageSize, page);
						bFail = false;
					} catch (RemoteException e) {
						log(e);
						bFail = true;
				
						int msec = 900000;
						log("[" + numTimeouts++ + "]: Wait " + msec/60000 + " minutes for initial query to finish: ");
						pause(msec);
					}
				} while ( bFail );
				page++;
			} while ( nRecords > 0 );
			log("Completed request to fetch billing results from the last " + wrapValue(String.valueOf(num))
						+ " days, starting from " + wrapValue(String.valueOf(numStart)) + " day(s) ago, "
						+ wrapValue(String.valueOf(numStartMin)) + " minutes before current minute.");
			log("numTimeouts=" + numTimeouts);
			log("nTotalRecords=" + nTotalRecords);
			log("Number of pages=" + page);
			log("Page Size=" + pageSize);
		} catch (Exception e) {
			log(e);
		}

	}
	
	public static int reportResults(Transaction[] results, int page) {
		int nRecords = 0;
		if (results != null) {
			
			Map<TransactionStatusType, Integer> freq = new HashMap<TransactionStatusType, Integer>();
			
			nRecords = results.length;
			log("Retreived " + nRecords + ", page [" + page + "]:");
			int n = 0;
			for (Transaction tx : results) {
				TransactionStatusType status = tx.getStatus();
				//log(wrapValue(tx.getMerchantTransactionId()) + " created " + wrapValue(tx.getSelectTransactionId()) + " with value " + wrapValue(tx.getStatus().toString()));
				log("[" + page + ":" + n++ + "]: merchantTransactionId " + wrapValue(tx.getMerchantTransactionId())
					+ " created selectTransactionId " + wrapValue(tx.getSelectTransactionId())
					+ " with status " + wrapValue(tx.getStatus().toString())
					+ " , authCode " + wrapValue(tx.getAuthCode().toString())
					+ " on " + new Timestamp(tx.getTimestamp().getTime().getTime())
					+ " for " + tx.getAmount() + " " + tx.getCurrency()
					);
				NameValuePair[] nameValues = tx.getNameValues();
				for (int i=0; i < nameValues.length; i++) {
					log( "\tnameValues[" + i + "]: " +
					nameValues[i].getName() + " = " + nameValues[i].getValue());
				}
				int count = freq.containsKey(status) ? freq.get(status) : 0;
				freq.put(status, count + 1);
			}
			
			Set<Map.Entry<TransactionStatusType, Integer>> s = freq.entrySet();
			Iterator<Map.Entry<TransactionStatusType, Integer>> it = s.iterator();
			while (it.hasNext()) {
				Map.Entry<TransactionStatusType, Integer> m = (Map.Entry<TransactionStatusType, Integer>)it.next();
				TransactionStatusType type = m.getKey();
				Integer cnt = (Integer)m.getValue();
				log(wrapValue(type.toString() + ":  " + cnt.toString()));
			}
			
		} else {
			log("Nothing to fetch - the Transactions object is null.");
		}
		return nRecords;
	}
	
	public static void run() {
  

	  try {
		log( timestamp() + " FetchSelect.run():");
		
		// setup connection:
		select = new SelectStub(endpoint);
		select._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, Boolean.FALSE);
		select._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, new Integer(timeOutInMilliSeconds));
		select._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(timeOutInMilliSeconds));

		int num = 1;
		int numStart = 1;
		int numStartMin = 0;
		int numEndMin = 0;

		actionFetchResults(num, numStart, numStartMin, numEndMin);
		
	  }
	  catch(Exception e) {

		//System exception. 
		log(timestamp() + e.toString());
		e.printStackTrace();
	  }

	}

	public static Transaction getTransaction( String transactionId ) {
	
		Transaction trans = new Transaction();
		trans.setMerchantTransactionId( transactionId );

		Calendar timestamp = Calendar.getInstance();
		trans.setTimestamp(timestamp);

		BigDecimal amount = new BigDecimal( "10.99" );
		trans.setAmount(amount);
		
		String currency = "USD";
		trans.setCurrency(currency);

		TransactionStatusType status = TransactionStatusType.Failed;
		trans.setStatus(status);
		
		String divisionNumber = "5698";
		trans.setDivisionNumber(divisionNumber);

		String subscriptionId = transactionId;
		trans.setSubscriptionId(subscriptionId);

		String customerId = transactionId;
		trans.setCustomerId(customerId);
		
		String paymentMethodId = transactionId;
		trans.setPaymentMethodId(paymentMethodId);

		String creditCardAccount = getCreditCardAccount( TransactionStatusType.Captured, null );
		trans.setCreditCardAccount(creditCardAccount);
		
		String creditCardExpirationDate = "202208";
		trans.setCreditCardExpirationDate(creditCardExpirationDate);
		
		String authCode = new String( "302" );
		trans.setAuthCode(authCode);
		
		
		// Additional data members:
		String affiliateId = "Affiliate" + divisionNumber;
		trans.setAffiliateId(affiliateId);
		String affiliateSubId = "SubAffiliate" + divisionNumber;
		trans.setAffiliateSubId(affiliateSubId);
		String billingAddressCity = "Any City";
		trans.setBillingAddressCity(billingAddressCity);
		String billingAddressCountry = "US";
		trans.setBillingAddressCountry(billingAddressCountry);
		String billingAddressCounty = "Any County";
		trans.setBillingAddressCounty(billingAddressCounty);
		String billingAddressDistrict = "Any State (i.e. District)";
		trans.setBillingAddressDistrict(billingAddressDistrict);
		String billingAddressLine1 = "123 Main (Address Line 1)";
		trans.setBillingAddressLine1(billingAddressLine1);
		String billingAddressLine2 = "Suite 5 (Address Line 2)";
		trans.setBillingAddressLine2(billingAddressLine2);
		String billingAddressLine3 = "Internet Widgets Co. Ltd. (Address Line 3)";
		trans.setBillingAddressLine3(billingAddressLine3);
		String billingAddressPostalCode = "94002";
		trans.setBillingAddressPostalCode(billingAddressPostalCode);
		Calendar startDate = Calendar.getInstance();
		startDate.add(Calendar.YEAR, -1);
		trans.setSubscriptionStartDate(startDate);
	
		return trans;
	}

	public static String getCreditCardAccount(TransactionStatusType resultType, String why) {
		String creditCardAccount = null;
		if (resultType.equals( TransactionStatusType.Captured )) {
			creditCardAccount = new String("4111111111111111");
		}
		if (resultType.equals( TransactionStatusType.Failed )) {
			if ( null != why ) {
				if ( "Decline".equalsIgnoreCase(why) )
					creditCardAccount = new String("6011555555555553");	// Soft Fail
				else if ( "Hard".equalsIgnoreCase(why) )
					creditCardAccount = new String("4555555555555550");	// Hard Fail
			}
			if ( null == creditCardAccount ) {
				// Luhn Check Fail Validation
				creditCardAccount = new String("1111111111111111");
			}
		}
		return creditCardAccount;
	}
	
}

