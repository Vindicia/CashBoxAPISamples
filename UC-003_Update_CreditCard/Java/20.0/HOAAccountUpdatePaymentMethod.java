//
//		Use Case UC-003: Update Credit Card on File
//
//		HOAAccountUpdatePaymentMethod.java
//
//		This sample was built against the Vindicia java library.
//
//			https://github.com/Vindicia/CashBoxAPISamples/tree/master/
//					UC-003_Update_CreditCard/Java/20.0
//
//
import com.vindicia.client.ClientConstants;

import com.vindicia.client.WebSession;
//	public VindiciaReturn finalize_via_SOAP()	// invokes WebSession.finalize soap request
import com.vindicia.client.VindiciaReturn;
//	inherits from com.vindicia.soap.v20_0.Vindicia._return (see below):
import com.vindicia.client.Transaction;
import com.vindicia.client.AutoBill;
import com.vindicia.client.PaymentMethod;
import com.vindicia.client.VindiciaReturn;

import com.vindicia.soap.v20_0.Vindicia._return;
//	public ReturnCode getReturnCode()			// WebSession.apiReturn.returnCode
//	public java.lang.String getReturnString()	// WebSession.apiReturn.returnString
//	public java.lang.String getSoapId()			// WebSession.apiReturn.soapId
import com.vindicia.soap.v20_0.Vindicia.ReturnCode;
//import com.vindicia.soap.v20_0.Vindicia.WebSession;
//	public _return getApiReturn()						// WebSession.apiReturn
//	public WebSessionMethodReturn getApiReturnValues()	// WebSession.apiReturnValues
//	public NameValuePair[] getPrivateFormValues()		// WebSession.privateFormValues
//	public NameValuePair[] getMethodParamValues()		// WebSession.methodParamValues
import com.vindicia.soap.v20_0.Vindicia.NameValuePair;
import com.vindicia.soap.v20_0.Vindicia.WebSessionMethodReturn;
//	public AutoBillUpdate getAutoBillUpdate()	// WebSession.apiReturnValues.autobillUpdate
import com.vindicia.soap.v20_0.Vindicia.AutoBillUpdate;
//	public AutoBillUpdate getAutoBillUpdate()	// WebSession.apiReturnValues.accountUpdatePaymentMethod
import com.vindicia.soap.v20_0.Vindicia.AccountUpdatePaymentMethod;
//	public AutoBill getAutobill()	// WebSession.apiReturnValues.autoBillUpdate.autobill
//	public java.lang.Integer getScore()		// WebSession.apiReturnValues.autobillUpdate.score
//	public ScoreCode[] getScoreCodes()		// WebSession.apiReturnValues.autobillUpdate.scoreCodes
//	public TransactionStatus getAuthStatus()	// WebSession.apiReturnValues.autobillUpdate.authStatus
import com.vindicia.soap.v20_0.Vindicia.ScoreCode;
	
//import com.vindicia.soap.v20_0.Vindicia.AutoBill;
// Note: In 4.1, futureRebills is now nextBilling:
//	public Transaction getNextBilling()
		// WebSession.apiReturnValues.autoBillUpdate.autobill.nextBilling
//import com.vindicia.soap.v20_0.Vindicia.Transaction;

import com.vindicia.soap.v20_0.Vindicia.TransactionStatus;
import com.vindicia.soap.v20_0.Vindicia.TransactionStatusType;
//	public TransactionStatusCreditCard getCreditCardStatus()
		// WebSession.apiReturnValues.autobillUpdate.authStatus.creditCardStatus

import com.vindicia.soap.v20_0.Vindicia.TransactionStatusCreditCard;
//	public java.lang.String getAuthCode()
		// WebSession.apiReturnValues.autobillUpdate.authStatus.creditCardStatus.authCode
//	public java.lang.String getAvsCode()
		// WebSession.apiReturnValues.autobillUpdate.authStatus.creditCardStatus.avsCode
//	public java.lang.String getCvnCode()
		// WebSession.apiReturnValues.autobillUpdate.authStatus.creditCardStatus.cvnCode

import com.vindicia.client.Account;
import com.vindicia.client.Address;
import com.vindicia.soap.v20_0.Vindicia.BillingPlan;	// 4.3: for addEligibleBillingPlan
import com.vindicia.soap.v20_0.Vindicia.Campaign;	// 4.3: for addEligibleBillingPlan
import com.vindicia.soap.v20_0.Vindicia.CampaignType;	// 4.3: for addEligibleBillingPlan
import com.vindicia.soap.v20_0.Vindicia.Product;
import com.vindicia.soap.v20_0.Vindicia.AutoBillItem;
import com.vindicia.soap.v20_0.Vindicia.EmailPreference;
import com.vindicia.soap.v20_0.Vindicia.CreditCard;

import com.vindicia.client.VindiciaReturnException;
import com.vindicia.client.VindiciaServiceException;


import java.io.*;
import java.lang.StackTraceElement;
import java.net.*;
import javax.net.ssl.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.*;


public class HOAAccountUpdatePaymentMethod {

	private static final java.util.logging.Logger log
       =
	java.util.logging.Logger.getLogger(HOAAccountUpdatePaymentMethod.class.getName());

	public static String endpoint;
	public static String hoaUrl;
	public static String login;
	public static String password;

	public static String DEFAULT_VINDICIA_HOA_SERVICE_URL;

    static {
		ResourceBundle rb = ResourceBundle.getBundle("Environment");
		login = rb.getString("soap_login");
		password = rb.getString("soap_password");

		// ProdTest:
		//endpoint = "https://soap.prodtest.sj.vindicia.com/soap.pl";
		//hoaUrl = "https://secure.prodtest.sj.vindicia.com/vws";

		// Production:
		//endpoint = "https://soap.vindicia.com/soap.pl";
		//hoaUrl = "https://secure.vindicia.com/vws";

		endpoint = rb.getString("soap_url");
		hoaUrl = rb.getString("hoa_url");

        ClientConstants.SOAP_LOGIN = login;
        ClientConstants.SOAP_PASSWORD = password;

        ClientConstants.DEFAULT_TIMEOUT = 300000;
        ClientConstants.DEFAULT_VINDICIA_SERVICE_URL = endpoint;

		DEFAULT_VINDICIA_HOA_SERVICE_URL = hoaUrl;
    }    

	public static void main(String[] args) { 

        System.out.println("JAVA Version: " + System.getProperty("java.version") +"\n"+
			"SOAP Version: " + ClientConstants.getVersion() +"\n"+
			"SOAP_LOGIN: " + ClientConstants.SOAP_LOGIN + "\n" +"\n"+
			"SOAP URL: " + ClientConstants.DEFAULT_VINDICIA_SERVICE_URL + "\n"+
			"HOA URL: " + DEFAULT_VINDICIA_HOA_SERVICE_URL + "\n");

		String sessionId = "";
		String webSessionIdFromRedirect = null;

		HOAAccountUpdatePaymentMethod hoa = new HOAAccountUpdatePaymentMethod();

		BufferedReader in = 
			new BufferedReader(new InputStreamReader(System.in)); 
		if ( args.length > 0 ) {
			sessionId = args[0];
		}
		else {
			// Test initialize():
			sessionId = hoa.testInitialize();

				// HOA must redirect to your HOA Success URL, and that code needs to pull in the
				// sessionId from the redirect URL, and then the sessionId is passed to the code
				// below.  For one pass through, it will be the same sessionId as returned from
				// the call to hoa.testInitialize() above:

			// end Test initialize()
			log("sessionId = " + sessionId);
		 	log("\n ***************************************** \n");
		 
		 	// Simulate payment form submission
		 	if (sessionId != null) {
				webSessionIdFromRedirect = paymentFormSubmitSim(sessionId);
		 	}
		 	else {
			 	log("Can not render payment form, no web session retrievable from initialization");
			 	return;
		 	}
		 	log("\n ***************************************** \n");
		}
		log("webSessionIdFromRedirect = " + webSessionIdFromRedirect);
		
        Boolean billSuccess = hoa.finalize(webSessionIdFromRedirect);	// obtained from redirect URL
        log("WebSession.finalize() billing " + (billSuccess ? "Succeeded." : "Failed!") + "\n");
	}

	public String testInitialize() {

		// from Constants.java:
		String HOA_UPDATE_RETURN_URL = "http://localhost:8080/HOA/success_update.jsp";		
		String HOA_UPDATE_CANCEL_URL = "http://localhost:8080/HOA/error_update.jsp";
		String IP_ADDRESS = "206.173.193.34";	//"67.161.35.57";
		String MERCHANT_ACCOUNT_ID_PREFIX = "TestAccount-";

		String uniqueId = "" + System.currentTimeMillis();
		String acctId = MERCHANT_ACCOUNT_ID_PREFIX + uniqueId; // merchant acct id
									
		// use existing Account:
		//acctId = "testaccount7057";		// Account without an AutoBill
		acctId = "testaccount3219";		// Account with an AutoBill
		
		String merchantPaymentMethodId = acctId;	// use the same value for merchantPaymentMethodId

		log("acctId="+acctId +"\n"+
			"merchantPaymentMethodId="+merchantPaymentMethodId);
		
		// Now create a websession with Account VID and other info
		String sessionVID = initialize(
					HOA_UPDATE_RETURN_URL,		// returnURL
					HOA_UPDATE_CANCEL_URL,		// errorURL,
					getCashBoxVersion(),		// version
					IP_ADDRESS,					// sourceIp
					acctId,						// merchantAccountId
					merchantPaymentMethodId,	// merchantPaymentMethodId
					"CatchUp",					// updateBehavior,
					"false",					// ignoreAvsPolicy,
					"false",					// ignoreCvnPolicy,
					"true"						// replaceOnAllAutoBills
					);
					
		log("sessionVID="+sessionVID);

        return sessionVID;
	}

	public static void log(String message) {

		//System.out.println(message);
		log.severe( message );

	}

	public static void log(Throwable t) {

		StackTraceElement[] ste = t.getStackTrace();
		for (int i=0; i < ste.length; i++)
			log( ste[i].toString() );
	}

	public static String getCashBoxVersion()
	{
		return ClientConstants.getVersion();
	}
	
	public static Timestamp timestamp()
	{
		java.util.Date date= new java.util.Date();
		return ( new Timestamp(date.getTime()) );
	}

	public static String timestamp(java.util.Calendar calendar)
	{
		return ( null != calendar ? new Timestamp(calendar.getTime().getTime())+"" : "(null)" );
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

	public static Transaction[] fetchTransactions(com.vindicia.client.AutoBill autobill)
	{
		Transaction[] transactions = null;
		try
		{
            Transaction transaction = new Transaction();

            transactions = transaction.fetchByAutobill( null, autobill );

            if (null == transactions)
            {
                log("\tno Transactions performed for AutoBill.");
            }
            else
			{
				for (int i=0; i < transactions.length; i++)
				{
					log( "\ttransaction[" + i + "]: " + transactions[i].getMerchantTransactionId() +
						" " + new Timestamp(transactions[i].getTimestamp().getTime().getTime()) +
						" for " + transactions[i].getAmount() + " " + transactions[i].getCurrency() +
						" status: " + transactions[i].getStatusLog()[0].getStatus() );
				}
			}
		}
		catch (VindiciaReturnException vre) {
			// Got a non 200 response to Transaction.fetchByAutoBill() call
			// restart order placement process
            log(timestamp() + " fetchTransactions(): Transaction fetchByAutobill:\n" +"\n"+
			"\tReturn code: " + vre.getReturnCode() +"\n"+
			"\tReturn Message: " + vre.getMessage() +"\n"+
			"\tSoap call ID: " + vre.getSoapId() );
		}
		catch (VindiciaServiceException vse)
		{
			log(timestamp()
				+ "\tException in fetching Transactions for AutoBill: " + " " + vse);
			log(vse);
		}

		return transactions;
	}

    private static Boolean statusSuccess(TransactionStatus t)
    {
        Boolean bSuccess = false;

        bSuccess = (
                        (t.getStatus() == TransactionStatusType.Authorized)
                    ||  (t.getStatus() == TransactionStatusType.AuthorizedForValidation)

                        // Other values for TransactionStatusType, may need to be considered:
                                // AuthorizationPending (applies to PayPal - until finalizePayPalAuth() called)
                                // AuthorizedPending (ECP, HML w/Global Collect only)
                                // Cancelled    (this is a decline - must reject)
                                // Captured (could not be Captured yet - need batch to run)
                                // New      (would not occur since authorization in real time)
                                // Pending  (not expected)
                                // Refunded (should not be Refunded yet - just created)
                                // Settled  (not generated by CashBox - only reported)
                    );

        log("statusSuccess() result: " + bSuccess + " for TransactionStatus: " + t.getStatus());

        return bSuccess;
    }

	public static void logPaymentMethod(String s, com.vindicia.soap.v20_0.Vindicia.PaymentMethod paymentMethod)
	{
		if ( null != paymentMethod ) {
			PaymentMethod p = PaymentMethod.narrowSoapPaymentMethodObject(paymentMethod);
			CreditCard cc = p.getCreditCard();
			log(s +
				"\t\tmerchantPaymentMethodId=" + p.getMerchantPaymentMethodId() + "\n" +
				"\t\tActive=" + p.getActive() + "\n" +
				"\t\tVID=" + p.getVID() + "\n" +
				"\t\tSortOrder=" + p.getSortOrder() + "\n" +
				"\t\tcreditCard=" + cc.getAccount() + "\n" +
				"\t\texpirationDate=" + cc.getExpirationDate() + "\n");
		}
	}
	
	public static Boolean logAccount(com.vindicia.client.Account account)
	{
        Boolean bSuccess = false;

		if ( null != account ) {
			log( timestamp()
				+ "\taccount: merchantAccountId=" + account.getMerchantAccountId() );

            com.vindicia.soap.v20_0.Vindicia.PaymentMethod[] paymentMethods = account.getPaymentMethods();

			if ( null != paymentMethods ) {
			
				for ( int i=0; i < paymentMethods.length; i++ ) {
				
					logPaymentMethod( "\tpaymentMethod["+i+"]: \n", paymentMethods[i] );
				}
			}

            // set the successful boolean flag here so it may be returned as
            // the result from the call to finalize():
            bSuccess = true;
                    
		}
		else {
			log( timestamp()
				+ "\tNo account (i.e. null) found or returned!" );
		}

        log("logAccount() returning boolean result: " + bSuccess );

        return bSuccess;
	}
  
	public static Boolean logAutoBill(com.vindicia.client.AutoBill autobill)
	{
        Boolean billSuccess = false;

        com.vindicia.soap.v20_0.Vindicia.Transaction nextBilling = null;
        Transaction transaction = null;
        TransactionStatus transactionStatus = null;

		if ( null != autobill ) {
			//log( timestamp()
			//	+ "\tautobill: merchantAutoBillId=" + autobill.getMerchantAutoBillId() );

			//logPaymentMethod( "\tautobill paymentMethod: \n", autobill.getPaymentMethod() );

			logPaymentMethod( timestamp()
				+ "\n\tautobill: merchantAutoBillId=" + autobill.getMerchantAutoBillId()
				+ "\n\tautobill paymentMethod: \n", autobill.getPaymentMethod() );

			// Note: In 4.1, futureRebills is now nextBilling:
			// autobill.nextBilling
			nextBilling = autobill.getNextBilling();

            Transaction[] transactions = fetchTransactions(autobill);

			log( "\tnextBilling: " + ( null != nextBilling ?
					new Timestamp(nextBilling.getTimestamp().getTime().getTime()) +
					" for " + nextBilling.getAmount() + " " + nextBilling.getCurrency()
					: null )
				);

            if (null != transactions)
            {
                transaction = transactions[0];
                transactionStatus = transaction.getStatusLog()[0];
            }

            // set the successful billing flag here so it may be returned as
            // the result from the call to finalize():
            billSuccess = (
                    (null != nextBilling) && (nextBilling.getAmount().intValue() > 0)   // next billing
                    || ( (null != transactions)             // or successful Transaction
                        && (transaction.getAmount().intValue() > 0)
                        && ( statusSuccess(transactionStatus) ))
                    
                );
            // if neither of these conditions is true, then this is a single cycle
            // AutoBill that is free or no charge.  This case is not handled here,
            // but to support it, one additional check for a single cycle free AutoBill
            // to also return true, would be required above.

		}
		else {
			log( timestamp()
				+ "\tNo autobill (i.e. null) found or returned!" );
		}

        log("logAutoBill() returning billing result: " + billSuccess
                    + " for nextBilling on: " +
                    	( null != nextBilling ? new Timestamp(nextBilling.getTimestamp().getTime().getTime()) : "" )
                    + ", TransactionStatus: " +
                    	( null != transactionStatus ? transactionStatus.getStatus() : "" ) );

        return billSuccess;
	}
  
	public Account createCCAccount(String merchantAccountId, Address address, String email) 
	{
		boolean success = false;
		
		Account account = new Account (	
				null, // vid
				merchantAccountId, // merchant acct id
				null,	// parent merchant acct id
				null,	// default currency
				email,
				EmailPreference.html,
				"en", // pref language
				new Boolean(true), // warn before billing
				null, // company
				address.getName(),
				address,
				null, 
				null,
				null,	// tax exemptions
				null,	// token balances
				null,	// credit
				null,	// entitlements
				null	// taxType
				);
		
		try {
			success = account.update( null );
			log("createCCAccount(): Account creation succeeded ="+success +"\n"+
			"createCCAccount(): Merchant account ID ="+account.getMerchantAccountId() +"\n"+
			"createCCAccount(): Merchant account VID ="+account.getVID());
			return account;
			
		} catch (VindiciaReturnException e) {
			// Non-200 Return Code for account.update():
			log("createCCAccount(): Return code for Account update ="+e.getReturnCode() +"\n"+
			"createCCAccount(): Return string for Account update ="+e.getMessage() +"\n"+
			"\tcreateCCAccount(): Soap call ID: " + e.getSoapId() );
			return account;
		} catch (VindiciaServiceException e) {
			//System exception. 
			log("createCCAccount(): VindiciaServiceException for Account.update() ="+e.getMessage());
			log(e);
		
			Throwable t = e.getCause();
			int i=0;
			do {
				log(timestamp() + " Cause[" + i + "]: ");
				log(t);
				t = t.getCause();
				i++;
			} while ( t != null );
			return account;
		}
	}

	private int setNameValue(int i, NameValuePair[] nvps, String name, String value)
	{
		if ( null != value  ) {
			//log("setNameValue: used i (of "+nvps.length+" ) ="+i+", name="+name+",value="+value);
			nvps[i] = new NameValuePair();
			nvps[i].setName( name );
			nvps[i++].setValue( value );
			//log("setNameValue: next i (of "+nvps.length+" ) ="+i+", getName(i-1)="+nvps[i-1].getName()+", getValue(i-1)="+nvps[i-1].getValue());
		}
		return i;
	}
		
	private NameValuePair[] pruneNvpArray(int i, NameValuePair[] nvps)
	{
		// i=# used in NameValuePair array nvps
		//
		// Enforce that the # used in NameValues array matches the
		// # allocated, to prevent 500 error due to empty elements:
		NameValuePair nvps1[] = new NameValuePair[i];
		for ( int j=0; j < nvps1.length; j++ )
			nvps1[j] = nvps[j];
		// return the new array with exact # used = # allocated:
		return nvps1;
	}
		
	public void logNameValues(NameValuePair nvps[], String nvpsName)
	{
		String s = "\n";
		if ( null == nvps ) return;
		for (int i=0; i < nvps.length; i++) {
			s += (nvpsName + "[" + i + "]: ");
			s += (nvps[i].getName() + " = " + nvps[i].getValue() + "\n");
		}
		log( s );
	}
		
	public String initialize(	String returnURL,
								String errorURL,
								String version,
								String sourceIp,
								String merchantAccountId,
								String merchantPaymentMethodId,
								String updateBehavior,
								String ignoreAvsPolicy,
								String ignoreCvnPolicy,
								String replaceOnAllAutoBills )
	{
		boolean success = false;
		String sessionId = null;
		
		WebSession ws = new WebSession();
		
		ws.setMethod("Account_UpdatePaymentMethod");
		ws.setReturnURL(returnURL);
		ws.setErrorURL(errorURL);
		ws.setVersion(version);
		ws.setIpAddress(sourceIp);
		log("WebSession.setIpAddress ="+ws.getIpAddress());
		
		// Temporary NameValuePair array, increase size if passing more nvp's below:
		int MAX_NAME_VALUES=20;
		
		NameValuePair nvps[] = new NameValuePair[MAX_NAME_VALUES];

		// privateFormValues
		int i = 0;
		i = setNameValue(i, nvps, "vin_Account_merchantAccountId", merchantAccountId);
		i = setNameValue(i, nvps, "vin_PaymentMethod_merchantPaymentmethodId",
																merchantPaymentMethodId);
		i = setNameValue(i, nvps, "vin_PaymentMethod_type", "CreditCard");
		
		// obtain new NameValuePair array with exact # used=# allocated for soap request:
		NameValuePair nvps1[] = pruneNvpArray(i, nvps);	// # used=# allocated, no 500 error

		ws.setPrivateFormValues(nvps1);

		// methodParamValues
		i = 0;	// start over, re-use temporary NameValuePair array:
		i = setNameValue(i, nvps, "Account_updatePaymentMethod_updateBehavior",
																updateBehavior);
		i = setNameValue(i, nvps, "Account_updatePaymentMethod_ignoreAvsPolicy", ignoreAvsPolicy);
		i = setNameValue(i, nvps, "Account_updatePaymentMethod_ignoreCvnPolicy", ignoreCvnPolicy);
		i = setNameValue(i, nvps, "Account_updatePaymentMethod_replaceOnAllAutoBills",
																replaceOnAllAutoBills);

		// obtain new NameValuePair array with exact # used=# allocated for soap request:
		NameValuePair nvps2[] = pruneNvpArray(i, nvps);	// # used=# allocated, no 500 error
		
		ws.setMethodParamValues(nvps2);
		
		VindiciaReturn vr;
		try {
			vr = ws.initialize( null );
			sessionId = ws.getVID();
			
			log("Return code for websession initialize ="+vr.getReturnCode() +"\n"+
				"Return string for websession initialize ="+vr.getReturnString() +"\n"+
				"Web session VID ="+sessionId +"\n"+
				"Soap ID for websession initialize - "+vr.getSoapId());
			
			ReturnCode retCode = vr.getReturnCode();
			return sessionId;	// (retCode.getValue() == 200);
			
		} catch (VindiciaReturnException vre) {
			// Got a non 200 response to finalize() call
			// restart order placement process
			log(timestamp() + " initialize(): WebSession ID: " + sessionId +"\n"+
				"\tReturn code: " + vre.getReturnCode() +"\n"+
				"\tReturn Message: " + vre.getMessage() +"\n"+
				"\tSoap call ID: " + vre.getSoapId() );
			return sessionId;
		} catch (VindiciaServiceException vse) {
			//System exception. 
			log(timestamp() + " initialize(): WebSession ID: " + sessionId + " " + vse.toString());
			//vse.printStackTrace();
			log(vse);
			return sessionId;
		}
	}
	
	/*
	 *  This function simulates submission of the payment form from customer's web browser
	 *  directly to Vindicia server bypassing your servers so the credit card number entered
	 *  in the form does not touch your server relieving you of PCI compliance
	 */
	static String paymentFormSubmitSim(String wsId) {
		String webSessionId = null;
		try{
			
			  // Vindicia server URL where payment form will be submitted
			  // This will change depending on the Vindicia environment you are working with
		      URL url = new URL( DEFAULT_VINDICIA_HOA_SERVICE_URL );
		      
		      HttpsURLConnection hConnection = (HttpsURLConnection) url.openConnection();
		      HttpsURLConnection.setFollowRedirects( true );
		      HttpsURLConnection.setDefaultAllowUserInteraction(true);
		      hConnection.setDoOutput( true );
		      hConnection.setDoInput(true);
		      hConnection.setRequestMethod("POST");
		
		  
		      DataOutputStream out = new DataOutputStream ( hConnection.getOutputStream() );
		      
		      Map<String, String> data = new HashMap<String, String>();
		      
		      // Embed web session ID obtained during web session initialization in the form as hidden field
		      data.put("vin_WebSession_VID", wsId);
		      
		      // Collect some basic customer account information customer's account has not previously been created in CashBox
		      
		      data.put("vin_Account_name", "Jane Doe");
		      data.put("vin_Account_emailAddress", "jdoe@nomail.net");
		      
		      // Name on customer's credit card
		      data.put("vin_PaymentMethod_accountHolderName", "Card Payer");
		      
		      // Credit card account number
		      data.put("vin_PaymentMethod_creditCard_account", "4111111111111111"); // good card
		      //data.put("vin_PaymentMethod_creditCard_account", "5255555555555550"); // bad card

		      // Credit card expiration date in YYYYMM format
		      data.put("vin_PaymentMethod_creditCard_expirationDate", "202211");
		      
		      // Card security/CVV code
		      data.put("vin_PaymentMethod_nameValues_cvn", "123");
		      
		      // Billing address
		      //data.put("vin_PaymentMethod_billingAddress_addr1", "845 Cuesta Dr");
		      data.put("vin_PaymentMethod_billingAddress_addr1", "123 Cuesta Dr");
		      data.put("vin_PaymentMethod_billingAddress_city", "Mountain View");
		      data.put("vin_PaymentMethod_billingAddress_district", "CA");
		      data.put("vin_PaymentMethod_billingAddress_postalCode", "94040");
		      data.put("vin_PaymentMethod_billingAddress_country", "US");
		      //data.put("vin_PaymentMethod_billingAddress_country", "GB");
		      
			  Set keys = data.keySet();
			  Iterator keyIter = keys.iterator();
			  String content = "";
			  for(int i=0; keyIter.hasNext(); i++) {
				  Object key = keyIter.next();
				  if(i!=0) {
					content += "&";
				}
				content += key + "=" + URLEncoder.encode(data.get(key), "UTF-8");
			  }
			  //System.out.println(content);
			  out.writeBytes(content);
			  out.flush();
			  out.close();
		
			  BufferedReader in = new BufferedReader(new InputStreamReader(hConnection.getInputStream()));
			  int status = hConnection.getResponseCode();

			  if (status != HttpURLConnection.HTTP_OK) 
			  {
					if (status == HttpURLConnection.HTTP_MOVED_TEMP
							|| status == HttpURLConnection.HTTP_MOVED_PERM
								|| status == HttpURLConnection.HTTP_SEE_OTHER) 
					{
	
					  String newUrl = hConnection.getHeaderField("Location");
					  System.out.println("Got redirected to url: " + newUrl);
					  System.out.println("Grabbing WebSession VID from the redirect URL ...");
					  java.util.Map<String, String> nvs = splitQuery(new java.net.URL(newUrl));
					  webSessionId = nvs.get("session_id");
					}
					else {
						System.out.println("Did not get correctly redirected from Vindicia after payment form submission");
					}
				  
			  }
			  else {
				  System.out.println("Did not get correctly redirected from Vindicia after payment form submission");
			  }
			  
			  in.close();
			  
		}
		catch(Exception ex)
		{
    	
			ex.printStackTrace();
       
		}
      return webSessionId;
	}	
	
	// Helper function to parse a URL and extract query string name-value pairs 
	
	public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String query = url.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
	
	//	Java specific code handling of HOA WebSession Method finalize processing
	//	------------------------------------------------------------------------
	//
	// In the section of the Design Document describing finalizing the WebSession
	// it states: "The finalize call returns an updated WebSession object."  This
	// is correct in that it refers to the WebSession.finalize soap request and the
	// WebSession.finalizeResponse soap response as defined in the WSDL and Online
	// Soap Documentation at:
	//
	//	http://developer.vindicia.com/docs/soap/index.html?ver=4.1
	//
	// However, specific to the CashBox Java Client library, this translates into
	// the mapping into the Java API method to invoke the WebSession.finalize soap
	// request, and mapping of the WebSession.finalizeResponse into the Java WebSession
	// object accessible using the Java Client library:
	//
	// 1) Java API method to invoke the WebSession.finalize soap request:
	//
	//	com.vindicia.client.WebSession.finalize_via_SOAP()
	//
	// 2) Mapping of WebSession.finalizeResponse soap response to Java WebSession:
	//
	//	The WebSession.finalizeResponse soap response data values are copied
	//	to the com.vindicia.client.WebSession Java object.
	//	
	//	Following a successful call to finalize_via_SOAP(), the values from the
	//	WebSession.finalizeResponse soap response are then accessible using the
	//	get methods on the inherited com.vindicia.soap.v20_0.Vindicia.WebSession.
	//
	// Note that the WebSession data members from the WSDL are documented in the
	// Online Soap documentation for the WebSession datatype below:
	//
	//	http://developer.vindicia.com/docs/soap/AllDataTypes.html?pf=1&ver=4.1&type=WebSession
	//
	// ---
	//
	// HOA uses the following 3 steps:
	//		1. WebSession.initialize (initialize & obtain a sessionId for the WebSession)
	//		2. HOA Form Post (Present Form to buyer with hidden sessionId, buyer posts to HOA)
	//		3. Redirect to HOA success page (sessionId from redirect for WebSession.finalize)
	//
	// Below from the Design Document describes the handling of Step 3, HOA success page,
	// where the sessionId from the redirect URL is passed to the finalize() method below:
	//
	// 6.	Upon payment form submission if customer’s browser is redirected to the Return URL
	//      hosted by you and specified in the WebSession object. On this page finalize the
	//      WebSession object as follows:
	//
	// 		a.	The redirect URL string contains WebSession’s VID as the value associated with
	//			name ‘session_id’.  Use the VID to make the finalize() call below:
	//
	// ---
	//
	//	HOA WebSession Method: Account_UpdatePaymentMethod
	//
	public Boolean finalize(String sessionId)
	{
        Boolean bSuccess = false;

		String sessionVID = sessionId; // obtained from redirect url (session_id)

		WebSession ws = new WebSession();

		ws.setVID(sessionVID);

	  try {
		log( timestamp()
				+ " Before calling ws.finalize_via_SOAP(), ClientConstants.DEFAULT_TIMEOUT = "
				+ ClientConstants.DEFAULT_TIMEOUT );
		VindiciaReturn vr = ws.finalize_via_SOAP( null );	// invoke WebSession.finalize soap request

		// WebSession.finalizeResponse.return.returnCode
		ReturnCode returnCode = vr.getReturnCode();
		// returnCode.getValue() for numeric value as in: if (returnCode.getValue() == 200)
		log( "WebSession.finalize ReturnCode: " + returnCode );
		// WebSession.finalizeResponse.return.returnString
		String returnString = vr.getReturnString();
		log( "WebSession.finalize ReturnString: " + returnString );
		// WebSession.finalizeResponse.return.soapId
		String finalize_soapId = vr.getSoapId();
		log( timestamp() + " WebSession.finalize soapId: " + finalize_soapId );
			

		// log soap id if available in the return values of this call
		// finalize call succeeded
		// check if underlying AutoBill.update call succeeded as
		// described in step (ii)
	
		// here are the methods to call to implement step (ii):
	
		// Note: the successful call to finalize_via_SOAP() above due
		// to no Exceptions being thrown means the WebSession.finalizeResponse
		// soap response values were copied to the same WebSession object on
		// which the finalize_via_SOAP() method was called.  In this case,
		// the ws object data members now contain the WebSession.finalizeResponse
		// values.
	
		// WebSession.apiReturn
		_return apiReturn = ws.getApiReturn();
		ReturnCode apiReturnCode = returnCode;
		// WebSession.apiReturn.returnString
		String apiReturnString = "";

		// WebSession.apiReturn.soapId
		String soapId = "";	
		if ( null != apiReturn ) {
			// WebSession.apiReturn.returnCode
			apiReturnCode = apiReturn.getReturnCode();
			// call apiReturnCode.getValue() for numeric as: if (apiReturnCode.getValue() == 200)
			// WebSession.apiReturn.returnString
			apiReturnString = apiReturn.getReturnString();

			// WebSession.apiReturn.soapId
			soapId = apiReturn.getSoapId();	
		}
	
		// WebSession.method
		String method = ws.getMethod();

		// WebSession.apiReturnValues
		WebSessionMethodReturn apiReturnValues = ws.getApiReturnValues();

		// WebSession.privateFormValues
		NameValuePair[] privateFormValues = ws.getPrivateFormValues();
		if ( null == privateFormValues ) privateFormValues = new NameValuePair[0];
		// WebSession.methodParamValues
		NameValuePair[] methodParamValues = ws.getMethodParamValues();
		if ( null == methodParamValues ) methodParamValues = new NameValuePair[0];
		// WebSession.postValues
		NameValuePair[] postValues = ws.getPostValues();
		if ( null == postValues ) postValues = new NameValuePair[0];


		// Now you can add code here to follow the response handling as described
		// in the finalize() handling in the Design Document:
	
		log( "apiReturnCode: " + apiReturnCode +"\n"+
			"apiReturnString: " + apiReturnString +"\n"+
			"method: " + method );
			
		log( timestamp() + " apiReturn soapId: " + soapId );

		logNameValues( privateFormValues, "privateFormValues" );

		String merchantAccountId = "";
		String merchantPaymentmethodId = "";
		for (int i=0; i < privateFormValues.length; i++) {
			log( "privateFormValues[" + i + "]: " +
				privateFormValues[i].getName() + " = " + privateFormValues[i].getValue());
			if ( "vin_Account_merchantAccountId".equalsIgnoreCase( privateFormValues[i].getName() ) )
			{
				merchantAccountId = privateFormValues[i].getValue();
				log("Found: merchantAccountId=" + merchantAccountId);
			}
			else if ( "vin_PaymentMethod_merchantPaymentmethodId".equalsIgnoreCase( privateFormValues[i].getName() ) )
			{
				merchantPaymentmethodId = privateFormValues[i].getValue();
				log("Found: merchantPaymentmethodId=" + merchantPaymentmethodId);
			}
		}
		logNameValues( methodParamValues, "methodParamValues" );
		logNameValues( postValues, "postValues" );

		if (returnCode.getValue() == 200) {
		
			// WebSession successfully finalized, retrieve API values returned below:
			
			if ( !method.equalsIgnoreCase("Account_UpdatePaymentMethod") ) {
		
				log( "WebSession Method is NOT Account_UpdatePaymentMethod, found: " + method +"\n"+
						"finalized WebSession ID: " + sessionId +"\n"+
						"  Return code: " + returnCode +"\n"+
						"  Return Message: " + returnString +"\n"+
						"  " + timestamp() + " Soap call ID: " + finalize_soapId +"\n"+
						"Account/AutoBill PaymentMethod NOT updated! aborting..." );
	
				return bSuccess;
			}

			// WebSession.apiReturnValues.accountUpdatePaymentMethod
			AccountUpdatePaymentMethod accountUpdatePaymentMethod = apiReturnValues.getAccountUpdatePaymentMethod();

			// WebSession.apiReturnValues.accountUpdatePaymentMethod.account
			com.vindicia.soap.v20_0.Vindicia.Account account = accountUpdatePaymentMethod.getAccount();
			
			// WebSession.apiReturnValues.accountUpdatePaymentMethod.validated
			Boolean bValidated = accountUpdatePaymentMethod.getValidated();
		
			log( "accountUpdatePaymentMethod.validated: " + bValidated );

			// 1a) Check for apiReturnCode == 200, if so: AutoBill was created...
			//		get additional details from:
			//			autobillUpdate
			//			autobill
			// 			nextBilling	(Note: In 4.1, futureRebills is now nextBilling)
			//
			if ( bValidated ) {	// PaymentMethod was validated...
		
				log( "PaymentMethod was validated:" );
				
				Account resAccount = Account.narrowSoapAccountObject(account);
				
				// display Account & associated PaymentMethod(s)
                bSuccess = logAccount( resAccount );

				AutoBill[] autobills = AutoBill.fetchByAccount( null, resAccount, true, null, null ) ;
				// srd, Account, includeChildren, page, pageSize
				
				for ( int i=0; i < autobills.length; i++ ) {
				
					// display AutoBill & associated Transaction(s), set success
					logAutoBill( autobills[i] );
					// could introduce a check here to verify that:
					//	1) Each AutoBill has the same value for merchantPaymentmethodId
					//	   as requested in the original HOA Account_UpdatePaymentMethod
					//	   (from the private form values above)
					// and that:
					//	2) The last 4 digits of the associated Credit Card for the
					// 	   PaymentMethod match the vin_PaymentMethod_creditCard_lastDigits
					//	   in the Form Post parameters...as this would reflect successful
					//	   application of the "replaceOnAllAutoBills=true" parameter invoked
					//	   on the internal Account.updatePaymentMethod() soap method call.
				}

			}
			// 1b) If apiReturnCode is not 200, PaymentMethod not validated...
			//		get additional details from:
			//		i)	returnString
			//		ii)
			//			timestamp
			//			soapId
			//		iii)
			//			privateFormValues
			//			methodParamValues
			//
			else {	// apiReturnCode is not 200, AutoBill not created...
		
				log( "PaymentMethod NOT validated!" +"\n"+
						"  apiReturnCode: " + apiReturnCode +"\n"+
						"  apiReturnString: " + apiReturnString +"\n"+
						"  " + timestamp() + " apiReturn soapId: " + soapId +"\n"+
						"  " + timestamp() + " finalize soapId: " + finalize_soapId );
		
			}
		}
		else {	// returnCode is not 200, WebSession not finalized...
		
			log( "Unable to finalize WebSession ID: " + sessionId +"\n"+
					"  Return code: " + returnCode +"\n"+
					"  Return Message: " + returnString +"\n"+
					"  " + timestamp() + " Soap call ID: " + finalize_soapId );
		
		}
	
	  }
	  catch (VindiciaReturnException vre) {

		// Got a non 200 response to finalize() call
		// restart order placement process

		log(timestamp() + " WebSession ID: " + sessionId +"\n"+
				"\tReturn code: " + vre.getReturnCode() +"\n"+
				"\tReturn Message: " + vre.getMessage() +"\n"+
				"\tSoap call ID: " + vre.getSoapId() );

	  }
	  catch(VindiciaServiceException vse) {

		//System exception. 
		log(timestamp() + " finalize(): WebSession ID: " + sessionId + " " + vse.toString());
		//vse.printStackTrace();
		log(vse);
		
		Throwable t = vse.getCause();
		log(timestamp() + " Cause: " + t);
		if ( (null != t) && t.toString().startsWith("java.net.Socket") ) {
			// Timeout occurred...code below attempts to recover:
			log(timestamp() + " Cause: " + t.toString());
			t.printStackTrace();
			
			// This code does not seem to be needed, as the PaymentMethod is usually already there
			//int msec = 10000;
			//log(timestamp() + " Wait " + msec/1000 + " seconds for Account/AutoBill update to finish: ");
			//pause(msec);
			
			log(timestamp() + " finalize(): Fetch Account to see if successfully updated: ");

			try {
				// The following lines of code are here only to obtain merchantAutoBillId
				ws = WebSession.fetchByVid( null, sessionId );
				NameValuePair[] privateFormValues = ws.getPrivateFormValues();
				if ( null == privateFormValues ) privateFormValues = new NameValuePair[0];
				String merchantAccountId = "";
				String merchantPaymentmethodId = "";
				for (int i=0; i < privateFormValues.length; i++) {
					log( "privateFormValues[" + i + "]: " +
						privateFormValues[i].getName() + " = " + privateFormValues[i].getValue());
					if ( "vin_Account_merchantAccountId".equalsIgnoreCase( privateFormValues[i].getName() ) )
					{
						merchantAccountId = privateFormValues[i].getValue();
						log("Found: merchantAccountId=" + merchantAccountId);
					}
					else if ( "vin_PaymentMethod_merchantPaymentmethodId".equalsIgnoreCase( privateFormValues[i].getName() ) )
					{
						merchantPaymentmethodId = privateFormValues[i].getValue();
						log("Found: merchantPaymentmethodId=" + merchantPaymentmethodId);
					}
				}
				// Now that we have the merchantAutoBillId value, we can proceed to fetch it:
				
				Account account = Account.fetchByMerchantAccountId( null, merchantAccountId );
				
                bSuccess = logAccount(account);	// display Account & associated PaymentMethod(s), set success

				AutoBill[] autobills = AutoBill.fetchByAccount( null, account, true, null, null );
				// srd, Account, includeChildren, page, pageSize
				
				for ( int i=0; i < autobills.length; i++ ) {
				
					// display AutoBill & associated Transaction(s), set success
					logAutoBill( autobills[i] );
					// could introduce a check here to verify that:
					//	1) Each AutoBill has the same value for merchantPaymentmethodId
					//	   as requested in the original HOA Account_UpdatePaymentMethod
					//	   (from the private form values above)
					// and that:
					//	2) The last 4 digits of the associated Credit Card for the
					// 	   PaymentMethod match the vin_PaymentMethod_creditCard_lastDigits
					//	   in the Form Post parameters...as this would reflect successful
					//	   application of the "replaceOnAllAutoBills=true" parameter invoked
					//	   on the internal Account.updatePaymentMethod() soap method call.
				}
			}
	  		catch (VindiciaReturnException vre) {

				// Got a non 200 response to fetch call
				// restart order placement process

				log(timestamp() + " finalize(): Result of fetch Account, WebSession ID: " + sessionId +"\n"+
						"\tReturn code: " + vre.getReturnCode() +"\n"+
						"\tReturn Message: " + vre.getMessage() +"\n"+
						"\tSoap call ID: " + vre.getSoapId() );

	  		}
			catch (VindiciaServiceException vse2) {
				log( timestamp()
					+ "\tException in fetching Account or AutoBill: " + " " + vse2.toString() );
				//vse2.printStackTrace();
				log(vse2);
			}
		}

	  }
	  catch(Exception e) {

		//System exception. 
		log(timestamp() + " finalize(): WebSession ID: " + sessionId + " Other Exception: " + e.toString());
		e.printStackTrace();
		log(e);
		
	  }

        return bSuccess;
	}

}

