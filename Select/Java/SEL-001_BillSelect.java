import com.vindicia.soap.v1_0.select.*;
import com.vindicia.soap.v1_0.selecttypes.*;

import java.io.*; 
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.axis2.transport.http.HTTPConstants;


class SEL-001_BillSelect {

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
		userAgent = "BillSelect";
			
		log("endpoint=" + endpoint + "\nlogin=" + login);

		auth.setLogin(login);
		auth.setPassword(password);
		auth.setVersion(version);
		auth.setUserAgent(userAgent);
		
		// Connection Properties:
		timeOutInMilliSeconds = 300000;	// keep time out longer for bulk data requests 
		
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
	
	public static Timestamp timestamp()
	{
		java.util.Date date= new java.util.Date();
		return ( new Timestamp(date.getTime()) );
	}
  
	public static void run( String transactionId ) {
  

	  try {
		log( timestamp() + " BillSelect.run(): merchantTransactionId=" + transactionId );
		
		// setup connection:
		select = new SelectStub(endpoint);
		select._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, Boolean.FALSE);
		select._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, new Integer(timeOutInMilliSeconds));
		select._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(timeOutInMilliSeconds));

		// get Transaction array to submit:
		Transaction transaction = getTransaction( transactionId );
		Transaction[] transactions = new Transaction[1];
		transactions[0] = transaction;
		
		BillTransactions billTransactions = new BillTransactions();
		
		billTransactions.setAuth(auth);
		billTransactions.setTransactions(transactions);
		
		for (int i=0; i < transactions.length; i++) {
			Transaction tx = transactions[i];
			TransactionStatusType status = tx.getStatus();
				log("merchantTransactionId " + tx.getMerchantTransactionId()
					+ " with status " + tx.getStatus().toString()
					+ " , authCode " + tx.getAuthCode().toString()
					+ " on " + new Timestamp(tx.getTimestamp().getTime().getTime())
					+ " for " + tx.getAmount() + " " + tx.getCurrency()
					);
		}
		
		// make the call to billTransactions:
		log("Beginning request to bill transactions");
		BillTransactionsResponse billTransactionsResponse = select.billTransactions(billTransactions);
		
		log("Completed request to bill transactions");
		Return btxsReturn = billTransactionsResponse.get_return();
		log("Result=" + btxsReturn.getReturnCode().getValue() + ", " + btxsReturn.getReturnString());
		
		TransactionValidationResponse[] response = billTransactionsResponse.getResponse();
		for (int i=0; null != response && i < response.length; i++) {
			log("validationResult[" + i + "]=" + response[i].getCode() + ", " + response[i].getDescription());
		}
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
