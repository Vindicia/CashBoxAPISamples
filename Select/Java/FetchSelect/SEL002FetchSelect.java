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
import com.vindicia.soap.v1_1.select.*;
import com.vindicia.soap.v1_1.selecttypes.*;

import java.rmi.RemoteException;
import java.io.*;
import java.lang.Math;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.axis2.transport.http.HTTPConstants;


class SEL002FetchSelect {

	private static final java.util.logging.Logger log
       =
	java.util.logging.Logger.getLogger(SEL002FetchSelect.class.getName());

	static Authentication auth = new Authentication();
	static String endpoint;
	static String login;
	static String password;
	static String version;
	static String userAgent;
	
	static int timeOutInMilliSeconds;
	static SelectStub select;

	static FileWriter writer;

	static String response_header;
	static String response_path;
	static String response_file;
	static String[] hdrs;
	static String[] data;

	public static int iHDR_timestamp;
	public static int iHDR_amount;
	public static int iHDR_currency;
	public static int iHDR_status;

	public static int iHDR_merchantTransactionId;
	public static int iHDR_subscriptionId;
	public static int iHDR_customerId;

	public static int iHDR_authCode;
	public static int iHDR_avsCode;
	public static int iHDR_cvnCode;
	public static int iHDR_selectTransactionId;

	// The following string values must match the strings included
	// in the response header specified by response_header:
	public static String sHDR_timestamp = "timestamp";
	public static String sHDR_amount = "amount";
	public static String sHDR_currency = "CURR";
	public static String sHDR_status = "status";

	public static String sHDR_merchantTransactionId = "merchantTransactionId";
	public static String sHDR_subscriptionId = "subscriptionId";
	public static String sHDR_customerId = "customerId";

	public static String sHDR_authCode = "AUTH_RESPONSE_CODE";
	public static String sHDR_avsCode = "avsCode";
	public static String sHDR_cvnCode = "cvnCode";
	public static String sHDR_selectTransactionId = "SELECTTRANSACTI";

	// Sample value for response header matching above strings:
	// response_header=MERCHANTTRANSACTIONID,SUBSCRIPTIONID,CUSTOMERID,SELECTTRANSACTI,STATUS,AUTH_RESPONSE_CODE,AMOUNT,CURR

	public static int num_days;			// defaults to 1 day of data
	public static int end_time;			// defaults to 0 (now), < 0 midnight PT: -1 last night, -2 night before etc...
	public static int start_min_back;	// defaults to 0
	public static int end_min_back;		// defaults to 0 unless start_min_back set
	public static int page_size;		// defaults to 100

    /**
     * Properties supported in Environment.properties:
     *
     *		soap_url		- Soap endpoint, one of ProdTest, Staging, Production.
     *		soap_login		- Soap login user for environment selected by soap_url.
     *		soap_password	- Soap password for login for environment selected above.
     *
     *	Optional:
     *
     *		response_header	- Comma separated list of column headers for response file.
     *						  When this property is set, a response file will be written
     *						  writing response values as indicated by the ordered list
     *						  of column headers.  Each Transaction result received from
     *						  Select is written to a row containing the Transaction result.
     *
     *		response_path	- Specifies a different directory for response file than cwd.
     *		response_file	- Filename to write as the output response file.
     *
     *
     *
     *	fetch.billingResults.numdays	- Number of days to fetch, defaults to 1 day
     *	fetch.billingResults.end		- End time for fetch, defaults to 0 (now),
     *									  < 0 midnight PT: -1 last night, -2 night before etc...
     *	fetch.billingResults.endmin		- # minutes to move end time back, defaults to 0
     *	fetch.billingResults.startmin	- # minutes to move start time back, defaults to 0 (unless startmin set)
     *
     *	fetch.billingResults.pageSize	- pageSize for fetchBillingResults, defaults to 100
     *
     */
    static {
		ResourceBundle rb = ResourceBundle.getBundle("Environment");
		login = rb.getString("soap_login");
		password = rb.getString("soap_password");

		//endpoint = "https://soap.prodtest.sj.vindicia.com/soap.pl";
		endpoint = rb.getString("soap_url");

		version = "1.1";
		userAgent = "FetchSelect (Select API)";
			
		auth.setLogin(login);
		auth.setPassword(password);
		auth.setVersion(version);
		auth.setUserAgent(userAgent);
		
		// Connection Properties:
		timeOutInMilliSeconds = 300000;
		
		log("\n\tendpoint=" + endpoint + "\n\tlogin=" + login +
		"\n\n\tversion=" + version + "\n\tuserAgent=" + userAgent +
		"\n\ttimeOutInMilliSeconds=" + timeOutInMilliSeconds + "\n");

		try {
			response_header = rb.getString("response_header");
			log("Found response_header property:\t=> File Based:\n");
			log("Header format:\n" + response_header);
			hdrs = response_header.split(",");
			data = new String[hdrs.length];
			//String row = logRow(hdrs);
			String hdr = "";
			log(hdr);
			for (int i=0; i < hdrs.length; i++) {
				hdr += "hdrs[" + i + "]=" + hdrs[i] + "\n";
			}
			//log(hdr);
			
			iHDR_timestamp = find(hdrs, sHDR_timestamp);
			iHDR_amount = find(hdrs, sHDR_amount);
			iHDR_currency = find(hdrs, sHDR_currency);
			iHDR_status = find(hdrs, sHDR_status);

			iHDR_merchantTransactionId = find(hdrs, sHDR_merchantTransactionId);
			iHDR_subscriptionId = find(hdrs, sHDR_subscriptionId);
			iHDR_customerId = find(hdrs, sHDR_customerId);

			iHDR_authCode = find(hdrs, sHDR_authCode);
			iHDR_avsCode = find(hdrs, sHDR_avsCode);
			iHDR_cvnCode = find(hdrs, sHDR_cvnCode);
			iHDR_selectTransactionId = find(hdrs, sHDR_selectTransactionId);

			String dir = System.getProperty("user.dir");
			log("\nWorking directory:\n\t" + dir);
			try {
				response_path = rb.getString("response_path");
				log("\n\tresponse_path=" + response_path + "\n");
			}
			catch ( Exception e ) {
				// default to current directory
				response_path = dir;
			}
			response_file = rb.getString("response_file");
			log("\n\tresponse_file=" + response_file + "\n");
		}
		catch ( Exception e ) {
			log("No response_header property found:\t=> Not File Based:\n");
		}
		
		// num_days: defaults to 1 day of data
		// end_time: defaults to 0 (now), < 0 midnight PT: -1 last night, -2 night before etc...
		// start_min_back: defaults to 0
		// end_min_back: defaults to 0 unless start_min_back set
		try {
			num_days = Integer.parseInt(rb.getString("fetch.billingResults.numdays"));
		}
		catch ( Exception e ) {	num_days = 1;	}		// 1 day of data
		try {
			end_time = Integer.parseInt(rb.getString("fetch.billingResults.end"));
		}
		catch ( Exception e ) {	end_time = 0;	}		// now
		try {
			start_min_back = Integer.parseInt(rb.getString("fetch.billingResults.startmin"));
		}
		catch ( Exception e ) {	start_min_back = 0;	}	// do not start any # minutes before start
		try {
			end_min_back = Integer.parseInt(rb.getString("fetch.billingResults.endmin"));
		}
		catch ( Exception e ) {	end_min_back = 0;	}	// do not end any # minutes before end
		try {
			page_size = Integer.parseInt(rb.getString("fetch.billingResults.pageSize"));
		}
		catch ( Exception e ) {	page_size = 100;	}	// default to 100

		log("\tnum_days=" + num_days + " day" + (num_days > 1 ? "s" : "") );
		log("\tend_time=" + end_time + " (< 0: prior midnight PT, -1 last night, -2 night before...)");
		log("\tstart_min_back=" + start_min_back );
		log("\tend_min_back=" + end_min_back );
		log("\tpage_size=" + page_size + "\n");

    }
    
    public static int find(String[] s, String val)
		throws IOException
	{
		if (null != val) {
			for (int i = 0; i < s.length; i++) {
				if ( val.equalsIgnoreCase(s[i]) ) {
					String msg = "iHDR_" + val;
					for (int j=val.length(); j < 30; j++) msg += ' ';
					//log(msg + "= " + i);
					return i;
				}
			}
		}
		return -1;
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

		System.out.println(message);
		//log.severe( message );

	}

	public static void log(StringBuffer message) {

		log( message.toString() );

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

    public static Calendar setMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
  
	public static Calendar Copy(Calendar calendar) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis( calendar.getTimeInMillis() );
		return cal;
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
	
	public static String amountToString(BigDecimal amount, String curr) {

		NumberFormat nf = NumberFormat.getCurrencyInstance(); 
		Currency currency = Currency.getInstance(curr);
		nf.setCurrency(currency);
		DecimalFormat df = (DecimalFormat) nf;
		DecimalFormatSymbols dfsymbols = df.getDecimalFormatSymbols();
		dfsymbols.setCurrencySymbol(""); // Don't use null.
		df.setDecimalFormatSymbols(dfsymbols);

		return df.format(amount);
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
		
		log("------------------------------------------------------------------------\n" +
		"[Page " + page + "]: Fetching " + wrapValue(String.valueOf(pageSize)) + " Billing Results");

		response = select.fetchBillingResults(fetchResults);

		log("Completed fetchBillingResults request[" + page + "]:\n\tstart="
					+ new Timestamp(start.getTime().getTime())
					+ ", end=" + new Timestamp(end.getTime().getTime())
					+ ",\n\tpageSize=" + pageSize + ", page=" + page + "\n");
		Return ftxsReturn = response.get_return();
		log("\n\tResult=" + ftxsReturn.getReturnCode().getValue() + ", " + ftxsReturn.getReturnString() +
		"\n\tsoapId=" + ftxsReturn.getSoapId() + "\n");
		nResults = reportResults(response.getTransactions(), page);
		
		return nResults;

	}

	public static void actionFetchResults(int num, int numStart, int numStartMin, int numEndMin) throws RemoteException {
		log("\n\tBeginning process to fetch billing results from the last\n\t" + wrapValue(String.valueOf(num))
						+ " days, starting from " + wrapValue(String.valueOf(numStart)) + " day(s) ago,\n\t"
						+ wrapValue(String.valueOf(numStartMin)) + " minutes before current minute."
						+ " ending "
						+ wrapValue(String.valueOf(numEndMin)) + " minutes before current minute.");

		Calendar start = getToday();

		if ( numStart < 0 ) {
			start =  setMidnight(start);
			numStart++;
			numStart = Math.abs(numStart);
		}
		start.add(Calendar.MINUTE, 0-numStartMin);
		start.add(Calendar.DATE, 0-numStart);
		Calendar end = Copy(start);
		start.add(Calendar.DATE, 0-num);
		if (0 == numEndMin) numEndMin = numStartMin;
		end.add(Calendar.MINUTE, 0-numEndMin);
		end.add(Calendar.DATE, 0-numStart);
		int pageSize = page_size;
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
			log("------------------------------------------------------------------------" +
			"\n\tCompleted process to fetch billing results from the last\n\t" + wrapValue(String.valueOf(num))
						+ " days, starting from " + wrapValue(String.valueOf(numStart)) + " day(s) ago,\n\t"
						+ wrapValue(String.valueOf(numStartMin)) + " minutes before current minute.\n" +
			"\n\tstart=" + new Timestamp(start.getTime().getTime()) +
			"\n\tend=" + new Timestamp(end.getTime().getTime()) +
			"\n\tpageSize=" + pageSize +
			"\n" +
			"\n\tnumTimeouts=" + numTimeouts +
			"\n\tnTotalRecords=" + nTotalRecords +
			"\n\tNumber of pages=" + page +
			"\n\tPage Size=" + pageSize + "\n");
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
					+ " for " + amountToString(tx.getAmount(), tx.getCurrency())
					+ " " + tx.getCurrency()
					);
				NameValuePair[] nameValues = tx.getNameValues();
				for (int i=0; i < nameValues.length; i++) {
					log( "\tnameValues[" + i + "]: " +
					nameValues[i].getName() + " = " + nameValues[i].getValue());
				}
				int count = freq.containsKey(status) ? freq.get(status) : 0;
				freq.put(status, count + 1);
				
				setValue( data, "timestamp", iHDR_timestamp, "" + new Timestamp(tx.getTimestamp().getTime().getTime()) );
				setValue( data, "amount", iHDR_amount, amountToString(tx.getAmount(), tx.getCurrency()) );
				setValue( data, "currency", iHDR_currency, tx.getCurrency() );
				setValue( data, "status", iHDR_status, tx.getStatus().toString() );

				setValue( data, "merchantTransactionId", iHDR_merchantTransactionId, tx.getMerchantTransactionId() );
				setValue( data, "subscriptionId", iHDR_subscriptionId, tx.getSubscriptionId() );
				setValue( data, "customerId", iHDR_customerId, tx.getCustomerId() );

				setValue( data, "authCode", iHDR_authCode, tx.getAuthCode().toString() );
				setValue( data, "avsCode", iHDR_avsCode, tx.getAvsCode() );
				setValue( data, "cvnCode", iHDR_cvnCode, tx.getCvnCode() );
				setValue( data, "selectTransactionId", iHDR_selectTransactionId, tx.getSelectTransactionId() );
				
				log("");

				if (writer != null) {
            		try {
						writeRow( data );
					}
					catch (Exception e) {		
            			e.printStackTrace();
					}
				}
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
			log("Nothing to fetch - the Transactions object is null.\n");
		}
		return nRecords;
	}

	public static void setValue(String[] data, String name, int i, String val ) {
	
		if ( null != data && ( i >= 0 && i < data.length ) ) {
			data[i] = new String(val);
		}
		else {
			log("Return value " + name + "=" + val + " skipped, not in response file format");
		}

	}
	
	public static void run() {
  

	  try {
		log( timestamp() + " FetchSelect.run():");
		
		// setup connection:
		select = new SelectStub(endpoint);
		select._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, Boolean.FALSE);
		select._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, new Integer(timeOutInMilliSeconds));
		select._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(timeOutInMilliSeconds));

		int num = num_days;					// defaults to 1 day of data
		int numStart = end_time;			// defaults to 0 (now), < 0 midnight PT:
											//		-1 last night, -2 night before etc...
		int numStartMin = start_min_back;	// defaults to 0
		int numEndMin = end_min_back;		// defaults to 0 unless start_min_back set

		String outputFile = "";

		if ( response_header != null ) {
			String separator = System.getProperty("file.separator");
			String directoryPath = response_path;
			String file = response_file;
			outputFile	= directoryPath + separator + file;
			log("outputFile="+outputFile +"\n");
			writer = new FileWriter(outputFile);
			writeRow(hdrs);
		}
		
		actionFetchResults(num, numStart, numStartMin, numEndMin);
		
		if (writer != null) {
			log("\nWrote response file to:\n\n"+outputFile +"\n");
			//Close the output stream
			writer.flush();
			writer.close();
		}
	  }
	  catch(Exception e) {

		//System exception. 
		log(timestamp() + e.toString());
		e.printStackTrace();

		if (writer != null) {
            try {
				writer.flush();
				writer.close();
			}
			catch (Exception e2) {		
            e2.printStackTrace();
			}
		}
	  }

	}

	public static String logRow(String[] s)
		throws IOException
	{
		StringBuffer row = new StringBuffer();
	
		for (int i = 0; i < s.length; i++) {
			if ( i > 0 ) {
				row.append(',');
			}
			row.append(s[i]);
		}
		row.append('\n');
		log(row);
		return row.toString();
	}

	public static void writeRow(String[] s)
		throws IOException
	{
		String row = logRow(s);
		writer.append( row );
	}
	
}

