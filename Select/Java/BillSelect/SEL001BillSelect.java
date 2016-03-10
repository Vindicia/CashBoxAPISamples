//
//		Use Case SEL-001: Submit Failed Transactions for recovery
//
//		SEL-001_BillSelect.java
//
//		This sample was built against a java library that was generated
//		using the Axis wsdl2java utility against the WSDL endpoint shown.
//
//		To run this sample it is required to first generate the java library.
//
import com.vindicia.soap.v1_1.select.*;
import com.vindicia.soap.v1_1.selecttypes.*;

import java.io.*; 
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.axis2.transport.http.HTTPConstants;


class SEL001BillSelect {

	static Authentication auth = new Authentication();
	static String endpoint;
	static String login;
	static String password;
	static String version;
	static String userAgent;
	
	static int timeOutInMilliSeconds;
	static SelectStub select;

	static FileWriter writer;

	static Boolean paymentMethodIsTokenized = Boolean.FALSE;

	static String request_header;
	static String request_path;
	static String request_file;
	static String[] hdrs;

	public static int iHDR_timestamp;
	public static int iHDR_amount;
	public static int iHDR_currency;
	public static int iHDR_status;
	public static int iHDR_divisionNumber;
	public static int iHDR_merchantTransactionId;
	public static int iHDR_subscriptionId;
	public static int iHDR_customerId;
	public static int iHDR_paymentMethodId;
	public static int iHDR_creditCardAccount;
	public static int iHDR_creditCardExpirationDate;
	public static int iHDR_billingAddressLine1;
	public static int iHDR_billingAddressLine2;
	public static int iHDR_billingAddressLine3;
	public static int iHDR_billingAddressCity;
	public static int iHDR_billingAddressDistrict;
	public static int iHDR_billingAddressPostalCode;
	public static int iHDR_billingAddressCountry;
	public static int iHDR_authCode;
	public static int iHDR_avsCode;
	public static int iHDR_cvnCode;

	
    /**
     * Properties supported in Environment.properties:
     *
     *		soap_url		- Soap endpoint, one of ProdTest, Staging, Production.
     *		soap_login		- Soap login user for environment selected by soap_url.
     *		soap_password	- Soap password for login for environment selected above.
     *
     *	Optional:
     *
     *		use_tokens		- set to True to use Tokens, defaults to False (use Credit Cards).
     *
     *		request_header	- Comma separated list of column headers for request file.
     *						  When this property is set, a request file will be read
     *						  taking values as indicated by the ordered list of column
     *						  headers.  Each line read is processed as one Transaction.
     *
     *		request_path	- Specifies a different directory for request file than cwd.
     *		request_file	- Filename to read as the input request file.
     *
     */
    static {
		ResourceBundle rb = ResourceBundle.getBundle("Environment");
		login = rb.getString("soap_login");
		password = rb.getString("soap_password");

		//endpoint = "https://soap.prodtest.sj.vindicia.com/soap.pl";
		endpoint = rb.getString("soap_url");

		version = "1.1";
		userAgent = "BillSelect (Select API)";
			
		auth.setLogin(login);
		auth.setPassword(password);
		auth.setVersion(version);
		auth.setUserAgent(userAgent);
		
		// Connection Properties:
		timeOutInMilliSeconds = 300000;	// keep time out longer for bulk data requests 
		
		log("\n\tendpoint=" + endpoint + "\n\tlogin=" + login +
		"\n\n\tversion=" + version + "\n\tuserAgent=" + userAgent +
		"\n\ttimeOutInMilliSeconds=" + timeOutInMilliSeconds + "\n");

		try {
			String use_tokens = rb.getString("use_tokens");
			paymentMethodIsTokenized = Boolean.valueOf(use_tokens);
		}
		catch (Exception e) {}
		
		try {
			request_header = rb.getString("request_header");
			log("Found request_header property:\t=> File Based:\n");
			log("Header format:\n" + request_header);
			hdrs = request_header.split(",");
			//String row = logRow(hdrs);
			String hdr = "";
			log(hdr);
			for (int i=0; i < hdrs.length; i++) {
				hdr += "hdrs[" + i + "]=" + hdrs[i] + "\n";
			}
			//log(hdr);
			
			iHDR_timestamp = find(hdrs, "timestamp");
			iHDR_amount = find(hdrs, "amount");
			iHDR_currency = find(hdrs, "currency");
			iHDR_status = find(hdrs, "status");
			iHDR_divisionNumber = find(hdrs, "divisionNumber");
			iHDR_merchantTransactionId = find(hdrs, "merchantTransactionId");
			iHDR_subscriptionId = find(hdrs, "subscriptionId");
			iHDR_customerId = find(hdrs, "customerId");
			iHDR_paymentMethodId = find(hdrs, "paymentMethodId");
			iHDR_creditCardAccount = find(hdrs, "creditCardAccount");
			iHDR_creditCardExpirationDate = find(hdrs, "creditCardExpirationDate");
			iHDR_billingAddressLine1 = find(hdrs, "billingAddressLine1");
			iHDR_billingAddressLine2 = find(hdrs, "billingAddressLine2");
			iHDR_billingAddressLine3 = find(hdrs, "billingAddressLine3");
			iHDR_billingAddressCity = find(hdrs, "billingAddressCity");
			iHDR_billingAddressDistrict = find(hdrs, "billingAddressDistrict");
			iHDR_billingAddressPostalCode = find(hdrs, "billingAddressPostalCode");
			iHDR_billingAddressCountry = find(hdrs, "billingAddressCountry");
			iHDR_authCode = find(hdrs, "authCode");
			iHDR_avsCode = find(hdrs, "avsCode");
			iHDR_cvnCode = find(hdrs, "cvnCode");

			String dir = System.getProperty("user.dir");
			log("\nWorking directory:\n\t" + dir);
			try {
				request_path = rb.getString("request_path");
				log("\n\trequest_path=" + request_path + "\n");
			}
			catch ( Exception e ) {
				// default to current directory
				request_path = dir;
			}
			request_file = rb.getString("request_file");
			log("\n\trequest_file=" + request_file + "\n");
		}
		catch ( Exception e ) {
			log("No request_header property found:\t=> Single Transaction Based:\n");
		}
    }    

	public static int find(String[] s, String val)
		throws IOException
	{
		if (null != val) {
			for (int i = 0; i < s.length; i++) {
				if ( val.equalsIgnoreCase(s[i]) ) {
					String msg = "iHDR_" + val;
					for (int j=val.length(); j < 40; j++) msg += ' ';
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
		run( transactionId );
	}

	public static void log(String message) {

		System.out.println(message);

	}

	public static void log(StringBuffer message) {

		log( message.toString() );

	}

	public static void logW(String message) {

		log(message);
		
		if ( null != writer )
			try { writer.append(message + "\n"); }
			catch (Exception e) {}

	}
	
	public static Timestamp timestamp()
	{
		java.util.Date date= new java.util.Date();
		return ( new Timestamp(date.getTime()) );
	}
  
	public static void run( String transactionId ) {
  

	  try {
		log( timestamp() + " BillSelect.run():\n\n\tmerchantTransactionId=" + transactionId + "\n");
		
		// setup connection:
		select = new SelectStub(endpoint);
		select._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, Boolean.FALSE);
		select._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, new Integer(timeOutInMilliSeconds));
		select._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(timeOutInMilliSeconds));

		Transaction[] transactions;
		
		if ( request_header == null ) {	// Single Transaction Based
			// get Transaction array to submit:
			Transaction transaction = getTransaction( transactionId );
			transactions = new Transaction[1];
			transactions[0] = transaction;
		}
		else {								// File Based
			// get Transaction array to submit:
			transactions = getTransactions();
		}

		BillTransactions billTransactions = new BillTransactions();
		
		billTransactions.setAuth(auth);
		billTransactions.setTransactions(transactions);
		
		for (int i=0; i < transactions.length; i++) {
			Transaction tx = transactions[i];
			TransactionStatusType status = tx.getStatus();
				logW("merchantTransactionId " + tx.getMerchantTransactionId()
					+ " with status " + tx.getStatus().toString()
					+ " , authCode " + tx.getAuthCode().toString()
					+ " on " + new Timestamp(tx.getTimestamp().getTime().getTime())
					+ " for " + tx.getAmount() + " " + tx.getCurrency()
					);
		}
		
		// make the call to billTransactions:
		logW("\n" + timestamp() + " Beginning request to bill transactions");
		BillTransactionsResponse billTransactionsResponse = select.billTransactions(billTransactions);
		
		logW(timestamp() + " Completed request to bill transactions");
		Return btxsReturn = billTransactionsResponse.get_return();
		logW("\n\tResult=" + btxsReturn.getReturnCode().getValue() + ", " + btxsReturn.getReturnString());
		logW("\tsoapId=" + btxsReturn.getSoapId());
		logW("\ttimestamp=" + timestamp() + "\n");
		
		TransactionValidationResponse[] response = billTransactionsResponse.getResponse();
		for (int i=0; null != response && i < response.length; i++) {
			logW("\t\tvalidationResult[" + i + "]=" + response[i].getCode() + ", " + response[i].getDescription());
		}
		if (writer != null) {
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

	public static Transaction getTransaction( String transactionId ) {
	
		String merchantTransactionId = transactionId;
		String customerId = transactionId;
		String subscriptionId = transactionId;
		String authCode = new String( "302" );
		String avsCode = new String( "" );
		String cvnCode = new String( "" );
		String creditCardAccount = getCreditCardAccount( TransactionStatusType.Captured, null );
		BigDecimal amount = new BigDecimal( "10.99" );
		String paymentMethodId = transactionId;
		String billingAddressLine1 = "123 Main (Address Line 1)";		
		String billingAddressLine2 = "Suite 5 (Address Line 2)";
		String billingAddressLine3 = "Internet Widgets Co. Ltd. (Address Line 3)";
		String billingAddressCity = "Any City";
		String billingAddressDistrict = "Any State (i.e. District)";
		String billingAddressCountry = "US";
		String billingAddressPostalCode = "94002";
		Calendar timestamp = Calendar.getInstance();
		String creditCardExpirationDate = "202208";
		String divisionNumber = "5698";
		String status = TransactionStatusType.Failed.toString();
		String currency = "USD";
		//Boolean paymentMethodIsTokenized = Boolean.TRUE;
		//Boolean paymentMethodIsTokenized = Boolean.FALSE;
		
		//creditCardAccount = "4111111111111111";

		Transaction trans = getTransaction(
								merchantTransactionId,
								customerId,
								subscriptionId,
								authCode,
								avsCode,
								cvnCode,
								creditCardAccount,
								amount,
								paymentMethodId,
								billingAddressLine1,
								billingAddressLine2,
								billingAddressLine3,
								billingAddressCity,
								billingAddressDistrict,
								billingAddressCountry,
								billingAddressPostalCode,
								timestamp,
								creditCardExpirationDate,
								divisionNumber,
								status,
								currency,
								paymentMethodIsTokenized
							);


		// Additional data members:
		String affiliateId = "Affiliate" + divisionNumber;
		trans.setAffiliateId(affiliateId);
		
		String affiliateSubId = "SubAffiliate" + divisionNumber;
		trans.setAffiliateSubId(affiliateSubId);
		
		String billingAddressCounty = "Any County";
		trans.setBillingAddressCounty(billingAddressCounty);
		
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


    /**
     * @see <a href="http://en.wikipedia.org/wiki/ISO_8601#Combined_date_and_time_representations">Combined Date and Time Representations</a>
     */
    public static final String ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    /**
     * 2015-09-23T01:39:00.000Z
     */

	public static Calendar getCalendar(String timestamp)
	{
		// To avoid the following Exception, edit format string to match
		//	java.text.ParseException: Unparseable date: "2015-09-23T01:39:00Z"
		//SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy hh:mm aa");	// 5/12/11 10:00 PM
		// Java 1.7 only:
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssXXX");// 2015-09-23T01:39:00Z
		// Java 1.6 & before:
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");// 2015-09-23T01:39:00Z
		Calendar calendar = Calendar.getInstance();
		try {
			log( "\nBefore: timestamp: " + timestamp );
			//if ( timestamp.endsWith("Z") )
			//	timestamp = timestamp.substring( 0, timestamp.length()-1 );
			//log( "\nAfter removal of 'Z': " + timestamp );
			//calendar.setTime( sdf.parse(timestamp) );
			calendar.setTime( sdf.parse(timestamp.replaceAll("Z$", "+0000")) );
			log( "After: calendar: " + sdf.format(calendar.getTime()) );
		} catch (ParseException e) {
			log( e.toString() );
		}
		return calendar;
	}

	public static String getBIN( String cc ) {
	
		return cc.substring(0, 6);		
	}		

	public static String mask( String cc ) {
	
		String maskedCC = getBIN(cc);
		
		maskedCC += String.format("%"+(cc.length()-6)+"s", "").replace(' ', 'X');
		
		return maskedCC;
	}		
	
	public static Transaction getTransaction(
		String merchantTransactionId,
		String customerId,
		String subscriptionId,
		String authCode,
		String avsCode,
		String cvnCode,
		String creditCardAccount,
		BigDecimal amount,
		String paymentMethodId,
		String billingAddressLine1,
		String billingAddressLine2,
		String billingAddressLine3,
		String billingAddressCity,
		String billingAddressDistrict,
		String billingAddressCountry,
		String billingAddressPostalCode,
		Calendar timestamp,
		String creditCardExpirationDate,
		String divisionNumber,
		String status,
		String currency,
		Boolean paymentMethodIsTokenized
	) {
	
		Transaction trans = new Transaction();
		trans.setMerchantTransactionId( merchantTransactionId );

		trans.setTimestamp( timestamp );	// Calendar
		trans.setAmount(amount);			// BigDecimal
		
		trans.setCurrency(currency);

		if ( !TransactionStatusType.Failed.toString().equalsIgnoreCase( status ) )
			log( "Unexpected Status: " + status );

		TransactionStatusType tstStatus = TransactionStatusType.Failed;
		trans.setStatus(tstStatus);
		
		trans.setDivisionNumber(divisionNumber);
		trans.setSubscriptionId(subscriptionId);
		trans.setCustomerId(customerId);
		trans.setPaymentMethodId(paymentMethodId);

		trans.setPaymentMethodIsTokenized( paymentMethodIsTokenized );

		if ( paymentMethodIsTokenized ) {
			creditCardAccount = getBIN( creditCardAccount );
		}
		
		trans.setCreditCardAccount(creditCardAccount);
		trans.setCreditCardExpirationDate(creditCardExpirationDate);
		
		trans.setAuthCode(authCode);
		trans.setAvsCode(avsCode);
		trans.setCvnCode(cvnCode);
		
		trans.setBillingAddressLine1(billingAddressLine1);
		trans.setBillingAddressLine2(billingAddressLine2);
		trans.setBillingAddressLine3(billingAddressLine3);
		trans.setBillingAddressCity(billingAddressCity);
		trans.setBillingAddressDistrict(billingAddressDistrict);
		trans.setBillingAddressCountry(billingAddressCountry);

		trans.setBillingAddressPostalCode(billingAddressPostalCode);
		
		String javaVersion = System.getProperty("java.version");
		String osVersion = System.getProperty("os.name") + " "
						+ System.getProperty("os.arch") + " "
						+ System.getProperty("os.version") + " "
						+ System.getProperty("sun.arch.data.model");
						
		log("\njavaVersion=" + javaVersion);
		log("osVersion=" + osVersion);
		log("");

		NameValuePair[] nvps = new NameValuePair[2];
		for ( int i=0; i < nvps.length; i++ ) 
			nvps[i] = new NameValuePair();
		nvps[0].setName("javaVersion");
		nvps[0].setValue(javaVersion);
		nvps[1].setName("osVersion");
		nvps[1].setValue(osVersion);
		trans.setNameValues(nvps);
	
		return trans;
	}

	
	public static Transaction getTransaction( int i, String[] request ) {

		String merchantTransactionId	= request[iHDR_merchantTransactionId];
		String customerId				= request[iHDR_customerId];
		String subscriptionId			= request[iHDR_subscriptionId];
		String authCode					= request[iHDR_authCode];
		String avsCode					= (iHDR_avsCode < 0 ? "" : request[iHDR_avsCode]);
		String cvnCode					= (iHDR_cvnCode < 0 ? "" : request[iHDR_cvnCode]);
		String creditCardAccount		= request[iHDR_creditCardAccount];
		String amount					= request[iHDR_amount];
		String paymentMethodId			= request[iHDR_paymentMethodId];
		String billingAddressLine1		= request[iHDR_billingAddressLine1];
		String billingAddressLine2		= request[iHDR_billingAddressLine2];
		String billingAddressLine3		= request[iHDR_billingAddressLine3];
		String billingAddressCity		= request[iHDR_billingAddressCity];
		String billingAddressDistrict	= request[iHDR_billingAddressDistrict];
		String billingAddressCountry	= request[iHDR_billingAddressCountry];
		String billingAddressPostalCode	= request[iHDR_billingAddressPostalCode];
		String timestamp				= request[iHDR_timestamp];
		String creditCardExpirationDate	= request[iHDR_creditCardExpirationDate];
		String divisionNumber			= request[iHDR_divisionNumber];
		String status					= request[iHDR_status];
		String currency					= request[iHDR_currency];
		//String paymentMethodIsTokenized	= "True";	//request[iHDR_paymentMethodIsTokenized];

		log( "\ngetTransaction(" + i + "):" );
		log( "merchantTransactionId     = " + merchantTransactionId );
		log( "customerId                = " + customerId );
		log( "subscriptionId            = " + subscriptionId );
		log( "authCode                  = " + authCode );
		log( "avsCode                   = " + avsCode );
		log( "cvnCode                   = " + cvnCode );
		log( "creditCardAccount         = " + mask(creditCardAccount) );
		log( "amount                    = " + amount );
		log( "paymentMethodId           = " + paymentMethodId );
		log( "billingAddressLine1       = " + billingAddressLine1 );
		log( "billingAddressLine2       = " + billingAddressLine2 );
		log( "billingAddressLine3       = " + billingAddressLine3 );
		log( "billingAddressCity        = " + billingAddressCity );
		log( "billingAddressDistrict    = " + billingAddressDistrict );
		log( "billingAddressCountry     = " + billingAddressCountry );
		log( "billingAddressPostalCode  = " + billingAddressPostalCode );
		log( "timestamp                 = " + timestamp );
		log( "creditCardExpirationDate  = " + creditCardExpirationDate );
		log( "divisionNumber            = " + divisionNumber );
		log( "status                    = " + status );
		log( "currency                  = " + currency );
		log( "paymentMethodIsTokenized  = " + paymentMethodIsTokenized );

		if ( !Character.isDigit(amount.charAt(0)) )
			amount = amount.substring(1);	// remove any currency symbol

		Transaction trans = getTransaction(
								merchantTransactionId,
								customerId,
								subscriptionId,
								authCode,
								avsCode,
								cvnCode,
								creditCardAccount,
								new BigDecimal( amount ),
								paymentMethodId,
								billingAddressLine1,
								billingAddressLine2,
								billingAddressLine3,
								billingAddressCity,
								billingAddressDistrict,
								billingAddressCountry,
								billingAddressPostalCode,
								getCalendar(timestamp),
								creditCardExpirationDate,
								divisionNumber,
								status,
								currency,
								Boolean.valueOf(paymentMethodIsTokenized)
							);

		return trans;
	}


	public static Transaction[] getTransactions() {
	
		Transaction[] txns = new Transaction[100];
		int i=0;

        try {
			String separator = System.getProperty("file.separator");
			String directoryPath = request_path;
			String file = request_file;
			// split filename into base + ext
			String[] fileElems = file.split("\\.(?=[^\\.]+$)");
			String inputFile	= directoryPath + separator + file;
			String outputFile	= directoryPath + separator + fileElems[0]
													+ "_output." + fileElems[1];
			log("\ninputFile="+inputFile);
			log("outputFile="+outputFile +"\n");
			FileInputStream fstream = new FileInputStream(inputFile);
			writer = new FileWriter(outputFile);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int iLine=0;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				iLine++;
				String[] request = strLine.split(",");
				if ( 1 == iLine ) {
					// header row, display
					log( iLine + ": " + strLine);
					log("");
					// writeRow( request );
					continue;
				}
				// Print the content on the console
				if ( request.length < hdrs.length ) {
					//log("Skipping..." + iLine + ": " + strLine);
					continue;
				}
				txns[i] = getTransaction( i, request );
				i++;

			}
			//Close the input stream
			in.close();
        }
        catch (Exception e){
        	System.out.println(""+e.getMessage());
            e.printStackTrace();
        }

		Transaction[] transactions = new Transaction[i];
		for ( int j=0; j < i; j++ )
			transactions[j] = txns[j];

		return transactions;
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

