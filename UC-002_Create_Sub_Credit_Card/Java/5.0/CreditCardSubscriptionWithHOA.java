import com.vindicia.client.*;
import com.vindicia.soap.v5_0.*;
import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.util.*;


/*
 * This sample illustrates creation of a credit card paid CashBox AutoBill (subscription) using Vindicia's HOA technology
 * With HOA you can post credit card details directly to Vindicia bypassing your servers thus relieving your servers off PCI compliance issues
 * 
 * In this sample we make the AutoBill.update() call used to create a new AutoBill using HOA. With HOA we have to do this in 3 steps
 * 1. Initialize WebSession and prepopulate it with some data in to present 
 * 2. Present payment form to the customer (this sample simulates the form and its submission)
 * 3. Finalize the WebSession and handle results
 * 
 */



public class CreditCardSubscriptionWithHOA {
	
	public static void main (String[] args) {
		
		
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL = "https://soap.prodtest.sj.vindicia.com";
		com.vindicia.client.ClientConstants.SOAP_LOGIN = "XXXXXX_soap";
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = "XXXXXXXXXXXXXXXXXX";
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = 25000;
		String HOA_FORM_SUBMIT_URL = "https://secure.prodtest.sj.vindicia.com/vws";
		
		 // Initialize WebSession
		 String webSessionId = init();   // see details on how the WebSession is initialized in the init() function below
		 System.out.println("\n ***************************************** \n");
		 
		 // Simulate payment form submission
		 // Form submission will result into a redirect to a URL you specified when you initialized the WebSession
		 // The WebSession ID is retrievable from the URL string of redirect
		 
		 String webSessionIdFromRedirect = null;
		 if (webSessionId != null) {
			 webSessionIdFromRedirect = paymentFormSubmitSim(webSessionId, HOA_FORM_SUBMIT_URL ); // session id needs to be included in the payment form submission
		 }
		 else {
			 System.out.println("Can not render payment form, no web session ID available after initialization");
			 return;
		 }
		 System.out.println("\n ***************************************** \n");
		 
		 // Finalize web session
		 if(webSessionIdFromRedirect != null) {
			 System.out.println("Got web session ID from payment form submission redirect. Using it to finalize web session");
			 finalizeWebSession(webSessionIdFromRedirect); // see details about result handling in the finalizeWebSession() function below
		 }
		 else {
			 System.out.println("Could not finalize web session since no web session id is retrievable from payment form submission and redirect");
		 }

	}
	
	
	/*
	 * This function initializes a WebSession object and prepares it to handle an AutoBill.update() call to create a subscription
	 */
	
	public static String init() {
		String wsId = null;
		
		com.vindicia.client.WebSession ws = new com.vindicia.client.WebSession();
		
		com.vindicia.soap.v5_0.Vindicia.NameValuePair[] params;
		com.vindicia.soap.v5_0.Vindicia.NameValuePair[] privates;
		
		ws.setMethod("AutoBill_Update"); // CashBox API call AutoBill.update() will be made upon finalization of this HOA or WebSession
		ws.setVersion("5.0");
		ws.setReturnURL("http://good.com");  // The URL  (hosted by merchant) to which customer's browser will be redirected upon successful payment form submission
		ws.setErrorURL("http://bad.com"); // The URL (hosted by merchant) to which customer's browser will be redirected upon an invalid payment form submission
		
		// Set method parameters for the AutoBill.update() call on the WebSession object
		
		com.vindicia.soap.v5_0.Vindicia.NameValuePair param1 = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
		param1.setName("AutoBill_Update_validatePaymentMethod");
		param1.setValue("true");  // credit card will be validated either by running full first bill amount transaction or using a $1/$0 validation transaction
		
		com.vindicia.soap.v5_0.Vindicia.NameValuePair param2 = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
		param2.setName("AutoBill_Update_minChargebackProbability");
		param2.setValue("100");	// turning off MaxMind fraud checking. Value of less than 100 will result in fraud eval, and if evaluated score is more than
								// this number, AutoBill creation will fail
		
		com.vindicia.soap.v5_0.Vindicia.NameValuePair param3 = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
		param3.setName("AutoBill_Update_dryRun");
		param3.setValue("false");
		
		com.vindicia.soap.v5_0.Vindicia.NameValuePair param5 = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
		param5.setName("AutoBill_Update_ignoreAvsPolicy");
		param5.setValue("false");   // respecting Vindicia's default AVS success/fail policy
		
		com.vindicia.soap.v5_0.Vindicia.NameValuePair param6 = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
		param6.setName("AutoBill_Update_ignoreCvnPolicy"); // respecting Vindicia's default CVV check success/fail policy
		param6.setValue("false");

		// AutoBill.update() takes in one more parameter - campaignCode
		// We will collect campaign code from the payment form since typically the customer has to provide it.
		// We will populate the campaignCode back into the WebSession prior to finalization
		
		params = new com.vindicia.soap.v5_0.Vindicia.NameValuePair[] {param1, param2, param3, param5, param6};
		
		ws.setMethodParamValues(params);
		
		String uniqueId = "" + System.currentTimeMillis();   // simple mechanism to generate a unique subscription ID

		// Unique subscription aka AutoBill ID
		com.vindicia.soap.v5_0.Vindicia.NameValuePair private1 = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
		private1.setName("vin_AutoBill_merchantAutoBillId");
		String merchantAbId = "vin_test_ab_" + uniqueId;
		private1.setValue(merchantAbId);
		
		
		// This AutoBill will use only one item i.e. Product
		// Customer can choose one from only the comma separated list of product IDs
		// * Products with these IDs must be created in CashBox a priori *
		
		com.vindicia.soap.v5_0.Vindicia.NameValuePair private2 = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
		private2.setName("vin_AutoBill_items_0_Product_merchantProductId");
		private2.setValue("monthlyProduct1,monthlyProduct2");
		
		// Unique ID for the AutoBillItem
		
		com.vindicia.soap.v5_0.Vindicia.NameValuePair private21 = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
		private21.setName("vin_AutoBill_items_0_merchantAutoBillItemId");
		private21.setValue("item-1-" + uniqueId);

		
		// Unique ID for customer Account. If this Account has previously been created, just pass its ID
		// This sample assumes that a customer Account with this ID has not previously been created
		// It sets unique ID for the Account to be created here while initializing the webSession
		// Rest of Account information is collected in the payment form
		com.vindicia.soap.v5_0.Vindicia.NameValuePair private3 = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
		private3.setName("vin_Account_merchantAccountId");
		private3.setValue("vin_test_acct_" + uniqueId);
		
		
		com.vindicia.soap.v5_0.Vindicia.NameValuePair private4 = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
		private4.setName("vin_PaymentMethod_type");
		private4.setValue("CreditCard");
		
		// Unique ID for the payment method - this could be variation on the account id
		com.vindicia.soap.v5_0.Vindicia.NameValuePair private41 = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
		private41.setName("vin_PaymentMethod_merchantPaymentMethodId");
		private41.setValue("vin_test_acct_cc_" + uniqueId);
		
		// Our AutoBill to be created needs a billing plan
		// Customer can choose one from only the comma separated list of billingPlan IDs
		// * BillingPlans with these IDs must be created in CashBox a priori *
		
		com.vindicia.soap.v5_0.Vindicia.NameValuePair private5 = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
		private5.setName("vin_BillingPlan_merchantBillingPlanId");
		private5.setValue("billMeMonthly,billMeMonthlyWithFreeTrial");	
		
		privates = new com.vindicia.soap.v5_0.Vindicia.NameValuePair[] {private1, private2, private21, private3, private4, private41, private5};
		
		ws.setPrivateFormValues(privates);
		ws.setIpAddress("192.168.228.1"); // Customer's IP address
		
		try {
			
			com.vindicia.client.VindiciaReturn vr = ws.initialize();
			
			// If we are here, got a 200 response code from Vindicia - indicating success of the call
			System.out.println("\n     Successfully initialized web session with VID " + ws.getVID());
			
			// We need VID of the WebSession object ... this needs to be embedded in the payment form served to the customer
			// Thus when customer submits the form to CashBox directly, CashBox knows which WebSession the submission is for
			
			wsId = ws.getVID();
			
			System.out.println("     Soap id of webSession.initialize() call" + vr.getSoapId());
			
		}
		
		catch (VindiciaReturnException vre) {
			
			System.out.println("Web session init failed: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'");
			System.out.println("Soap id " + vre.getSoapId());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return wsId;

	}
	
	
	/*
	 *  This function simulates submission of the payment form from customer's web browser
	 *  directly to Vindicia server bypassing your servers so the credit card number entered
	 *  in the form does not touch your server relieving you of PCI compliance
	 */

	
	static String paymentFormSubmitSim(String wsId, String formSubmitUrl) {
		String webSessionIdFromRedirect = null;
		try{
			
			  // Vindicia server URL where payment form will be submitted
			  // This will change depending on the Vindicia environment you are working with
		      URL url = new URL(formSubmitUrl);
		      
		      HttpsURLConnection hConnection = (HttpsURLConnection) url.openConnection();
		      HttpsURLConnection.setFollowRedirects( true );
		      HttpsURLConnection.setDefaultAllowUserInteraction(true);
		      hConnection.setDoOutput( true );
		      hConnection.setDoInput(true);
		      hConnection.setRequestMethod("POST");
		
		  
		      DataOutputStream out = new DataOutputStream ( hConnection.getOutputStream() );
		      
		      Map<String, String> data = new HashMap<String, String>();
		      
		      // Each name-value pair in this Map represents a field in the payment form. The name what you should use for
		      // the payment form field and value is either prepopulated or entered by customer in the form
		      
		      
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
		      data.put("vin_PaymentMethod_creditCard_expirationDate", "201511");
		      
		      // Card security/CVV code
		      data.put("vin_PaymentMethod_nameValues_cvn", "123");
		      
		      // Billing address
		      data.put("vin_PaymentMethod_billingAddress_addr1", "845 Cuesta Dr");
		      data.put("vin_PaymentMethod_billingAddress_city", "Mountain View");
		      data.put("vin_PaymentMethod_billingAddress_district", "CA");
		      data.put("vin_PaymentMethod_billingAddress_postalCode", "94040");
		      data.put("vin_PaymentMethod_billingAddress_country", "US");
		      
		      // Product selected by customer for purchase. Since during initialization we specified
		      // the two values this field can have, its value should be one of them
		      
		      data.put("vin_AutoBill_items_0_Product_merchantProductId", "monthlyProduct2");
		      
		      // Billing plan selected by customer. Since during initialization we specified
		      // the two values this field can have, its value should be one of them
		      
		     // data.put("vin_BillingPlan_merchantBillingPlanId", "billMeMonthly");
		      data.put("vin_BillingPlan_merchantBillingPlanId", "billMeMonthlyWithFreeTrial");
		      
		      // Promo or coupon code - this will be passed in as campaignCode input parameter to the AutoBill.update() call
		      data.put("AutoBill_Update_campaignCode", "XYZ"); 
		      
		      
		      // Submit the form directly to CashBox's HOA submission processor
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
					  webSessionIdFromRedirect = nvs.get("session_id");
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
      return webSessionIdFromRedirect;
	}
	
	
	/*
	 * This function finalizes WebSession - a process that you should complete when customer's web browser is
	 * redirected to your site ('returnUrl' configured in the WebSession object at initialization). It handles 
	 * results of finalization - ensuring the undelying AutoBill.update() call's results are handled too
	 */
	
	static void finalizeWebSession(String wsId) {
		
		// first fetch the WebSession object with given ID
		// Normally we should be able to finalize the web session directly without fetching it
		// But we collected some additional account information and campaign code in the web form which needs to
		// go into webSession.privateFormValues and webSession.methodParamValues respectively
		// So from the web session stored in Vindicia we need to get some pieces of into posted from the form
		// and then we need to put them in the right places in the web session object before finalizing
		
		String campaignCode = null;
		String customerName = null;
		String customerEmail = null;
		try {
		
			com.vindicia.client.WebSession fetchedWs = com.vindicia.client.WebSession.fetchByVid(wsId);
			
			com.vindicia.soap.v5_0.Vindicia.NameValuePair[] postedFormData = fetchedWs.getPostValues();
			
			if (postedFormData != null && postedFormData.length > 0) {
				for (int i = 0; i < postedFormData.length; i++) {
					if (postedFormData[i].getName().equalsIgnoreCase("vin_Account_name")) {
						customerName = postedFormData[i].getValue();
					}
					else if (postedFormData[i].getName().equalsIgnoreCase("vin_Account_emailAddress")) {
						customerEmail = postedFormData[i].getValue();
						
					}
					else if (postedFormData[i].getName().equalsIgnoreCase("AutoBill_Update_campaignCode")) {
						campaignCode = postedFormData[i].getValue();;
					}
				}
			}
			else {
				System.out.println("No form post data found");
				return;
			}
		}
		catch (VindiciaReturnException vex) {
			System.out.println("Websession fetch failed: " + vex.getReturnCode() + " return string: '" + vex.getMessage() + "'");
			System.out.println("Soap id " + vex.getSoapId());
			return;
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		
		// OK, now stuff some of the non credit card related data customer entered in the payment form into
		// right places in the web session object before finalizing it
		
		com.vindicia.client.WebSession webSession = new com.vindicia.client.WebSession();
		webSession.setVID(wsId);
		
		java.util.ArrayList<com.vindicia.soap.v5_0.Vindicia.NameValuePair> privates = new java.util.ArrayList<com.vindicia.soap.v5_0.Vindicia.NameValuePair>();
		
		if (customerName != null) {
			com.vindicia.soap.v5_0.Vindicia.NameValuePair private1 = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
			private1.setName("vin_Account_name");
			private1.setValue(customerName);
			privates.add(private1);
		}
		
		if (customerEmail != null) {
			com.vindicia.soap.v5_0.Vindicia.NameValuePair private2 = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
			private2.setName("vin_Account_emailAddress");
			private2.setValue(customerEmail);
			privates.add(private2);
		}
		if (privates.size() > 0)
			webSession.setPrivateFormValues(privates.toArray(new com.vindicia.soap.v5_0.Vindicia.NameValuePair[privates.size()]));
		
		if (campaignCode != null) {
			com.vindicia.soap.v5_0.Vindicia.NameValuePair param1 = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
				param1.setName("AutoBill_Update_campaignCode");
				param1.setValue(campaignCode);	
				webSession.setMethodParamValues(new com.vindicia.soap.v5_0.Vindicia.NameValuePair[] {param1});
		}
		
		try {
			webSession.finalize_via_SOAP(); // in Java this call is named finalize_via_SOAP, but in SOAP the call is called just finalize()
			
			// If we are here the finalize() call succeeded - but we still need to check if the underlying AutoBill.update() call succeeded
			System.out.println("     Web session finalized ... ");
			System.out.println("     Checking if AutoBill.update() succeeded ....");
			
			if (webSession.getApiReturn().getReturnCode().getValue() == 200) {
				
				// AutoBill.update() call was successful
				com.vindicia.soap.v5_0.Vindicia.AutoBillUpdate abillUpdateReturn = 
									webSession.getApiReturnValues().getAutoBillUpdate();

				com.vindicia.soap.v5_0.Vindicia.AutoBill autoBill = abillUpdateReturn.getAutobill();
				System.out.println("Successfully created subscription ID " + autoBill.getMerchantAutoBillId());
				
				// ------------------------------ Optional ------------------------------------------
				// Let's show customer how much we billed him/her today 
				try {
					com.vindicia.client.AutoBill txFetcherBill = new com.vindicia.client.AutoBill();
					txFetcherBill.setMerchantAutoBillId(autoBill.getMerchantAutoBillId());
					com.vindicia.client.Transaction[] txns = com.vindicia.client.Transaction.fetchByAutobill(txFetcherBill);
					
					// Since we just created the autobill , there should be only one transaction associated with it
					if (txns != null && txns.length > 0) {
						System.out.println("Billed total " + txns[0].getAmount().floatValue() +  " " + txns[0].getCurrency());
					}
					else {
						System.out.println("This subscription has not billed yet");
					}
						
				}
				catch (VindiciaReturnException vre) {
					
					System.out.println("    Could not fetch AutoBill's transactions: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'");
					System.out.println("    Soap id " + vre.getSoapId());
					
				}
				catch (Exception ex) {
					System.out.println("     Could not fetch AutoBill's transactions:");
					ex.printStackTrace();
				}
				
				// ------------------------------ End  ------------------------------------------
				
				
				if (autoBill.getNextBilling() != null) {
					System.out.print("Next estimated bill " + autoBill.getNextBilling().getAmount().floatValue() + " " + autoBill.getNextBilling().getCurrency());
					System.out.println(" is scheduled to be processed on " + getPrintableDate(autoBill.getNextBilling().getTimestamp()));
				}				
					
			}
			
			// for non-200 response codes, sample code below illustrates various responses
			// CashBox will return. For AVS,CVV, card validation errors it is not a good idea
			// to return exact reason to customer since fraudsters may use it to run card testing on your site
			
			else if (webSession.getApiReturn().getReturnCode().getValue() == 408) {
				System.out.println("AutoBill creation failed: CVV check failed");
				System.out.println("     Call SOAP ID: " + webSession.getApiReturn().getSoapId());
					
			}
			else if (webSession.getApiReturn().getReturnCode().getValue() == 407) {

				System.out.println("AutoBill creation failed: AVS Check Failed");	
				System.out.println("     Call SOAP ID: " + webSession.getApiReturn().getSoapId());
			}
			else if (webSession.getApiReturn().getReturnCode().getValue() == 409) {

				System.out.println("AutoBill creation failed: AVS and CVV Check Failed");	
				System.out.println("     Call SOAP ID: " + webSession.getApiReturn().getSoapId());
			}
			else if (webSession.getApiReturn().getReturnCode().getValue() == 410) {
				System.out.println("AutoBill creation failed: AVS and CVV check could not be performed");
				System.out.println("     Call SOAP ID: " + webSession.getApiReturn().getSoapId());
					
			}
			else if (webSession.getApiReturn().getReturnCode().getValue() == 402) {
				System.out.println("AutoBill creation failed: Card authorization failed");
				System.out.println("     Call SOAP ID: " + webSession.getApiReturn().getSoapId());
			}
			else if (webSession.getApiReturn().getReturnCode().getValue() == 400) {
				System.out.print("AutoBill creation failed: ");
				System.out.println(webSession.getApiReturn().getReturnString());
				System.out.println("     Call SOAP ID: " + webSession.getApiReturn().getSoapId());
				
			}
			
			
			
		}
		catch (VindiciaReturnException vre) {
			
			System.out.println("Web finalize failed: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'");
			System.out.println("Soap id " + vre.getSoapId());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Helper functions to format printable date from Calendar object

	protected static String getPrintableDate( java.util.Calendar cal ) {
		java.text.DateFormat df = 
			new java.text.SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
		return df.format(cal.getTime());
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
	

}
