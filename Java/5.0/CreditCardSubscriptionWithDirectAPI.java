import com.vindicia.client.*;

/**
 * 
 * This code sample illustrates how to create an AutoBill representing a subscription paid by a credit card
 * using the CashBox API directly (i.e. without using Vindicia's HOA mechanism)
 *
 */

public class CreditCardSubscriptionWithDirectAPI {
	
	public static void main (String[] args) {

		
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL = "https://soap.prodtest.sj.vindicia.com";
		com.vindicia.client.ClientConstants.SOAP_LOGIN = "XXXXXXX_soap";
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = "XXXXXXXXXXXX";
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = 25000; // ms
	
		long curTime = System.currentTimeMillis(); // For this sample we will use system time in ms for generating a unique ID
	
		
		// Specify ID of the Product customer has selected to subscribe. A Product with this ID must have been 
		// created in CashBox using portal or API before hand

		String productId = "regular-avataxed-product"; 
		
		// Specify ID of a BillingPlan customer will be billed with. A BillingPlan with this ID must have been created in CashBox before hand
		// If you will be using the default billing plan associated with the Product above, you can leave out specifying
		// a billing plan for the AutoBill object here.
		
		String billingPlanId = "billMeMonthly"; 
		
		String campaignCode = null; //Coupon or promotion code provided by customer if using CashBox's Campaigns feature
		
		// Customer (subscriber)'s account information.
		// If an Account object for the customer already exists in CashBox, simply specify the merchantAccountId
		// If an Account object for the customer does not exist, it will be newly created with the info
		// populated here
	
		
		com.vindicia.client.Account  acct = new com.vindicia.client.Account();
		acct.setMerchantAccountId("vin_test_acct_" + curTime);  // Unique Account. In this sample Account is created with AutoBill.

		// Specify customer's email address here . This the address where customer
		// will receive CashBox generated emails
		acct.setEmailAddress("nomail@vindicia.com");
		acct.setName("Simulated Customer-" + curTime );
		
		
		// This is the credit card customer will pay for this subscription with
		// If the account already exists and already has a good credit card payment method
		// and customer wants to use the same payment method to pay for this subscription
		// the payment method can be left out

		com.vindicia.client.PaymentMethod pm = new com.vindicia.client.PaymentMethod();
		pm.setAccountHolderName("CC Payer");
		pm.setActive(true);
		pm.setType(com.vindicia.soap.v5_0.Vindicia.PaymentMethodType.CreditCard);

		// Billing address for the payment method - necessary for AVS checking
		com.vindicia.client.Address addr = new com.vindicia.client.Address();
		addr.setAddr1("123 Copley Dr");
		addr.setCity("Lancaster");
		addr.setDistrict("PA");
		addr.setPostalCode("17601");
		addr.setCountry("US");
		pm.setBillingAddress(addr);

		// Specify unique ID for the payment method - could be derived from account id and payment type
		String merchantPmId = acct.getMerchantAccountId() + "_credit_card";

		pm.setMerchantPaymentMethodId(merchantPmId);
		
		com.vindicia.soap.v5_0.Vindicia.CreditCard cc = new com.vindicia.soap.v5_0.Vindicia.CreditCard();
		cc.setAccount("4111111111111111"); // credit card number
		cc.setExpirationDate("201608"); // expiration date (YYYYMM format)
		pm.setCreditCard(cc);
		
		com.vindicia.soap.v5_0.Vindicia.NameValuePair cvvNvp = new com.vindicia.soap.v5_0.Vindicia.NameValuePair();
		cvvNvp.setName("CVN");
		cvvNvp.setValue("123"); // card security code (CVV code) value provided by the customer
		
		pm.setNameValues(new com.vindicia.soap.v5_0.Vindicia.NameValuePair[] {cvvNvp});
		
		// Now construct the AutoBill object
		
		com.vindicia.client.AutoBill abill = new com.vindicia.client.AutoBill();
		abill.setMerchantAutoBillId("vin_test_abill" + curTime);	//Unique subscription ID, required
		abill.setAccount(acct);  // customer who is purchasing this subscription
		abill.setPaymentMethod(pm); 
		abill.setSourceIp("233.56.67.23"); // customer's IP address - necessary for fraud screening
		abill.setCurrency("USD");  // price in this currency must be specified on Product or BillingPlan


		
		com.vindicia.client.BillingPlan bp = new com.vindicia.client.BillingPlan();
		bp.setMerchantBillingPlanId(billingPlanId);
		abill.setBillingPlan(bp); // can be left null if using default billing plan on the Product
		
		// This subscription uses a single Product
		com.vindicia.soap.v5_0.Vindicia.AutoBillItem item = new com.vindicia.soap.v5_0.Vindicia.AutoBillItem();
		com.vindicia.client.Product prod = new com.vindicia.client.Product();
		prod.setMerchantProductId(productId);
		item.setProduct(prod);
		// Unique ID for the AutoBill item
		item.setMerchantAutoBillItemId(abill.getMerchantAutoBillId() + "-" + prod.getMerchantProductId() + "-1");
		
		abill.setItems(new com.vindicia.soap.v5_0.Vindicia.AutoBillItem[] {item});
		
		// Now make the CashBox SOAP API call to create the AutoBill in CashBox
		try {
			
				System.out.println("Creating autobill...");

				com.vindicia.client.AutoBillUpdateReturn abur = abill.update(com.vindicia.soap.v5_0.Vindicia.DuplicateBehavior.Fail, 
						true, // validate PaymentMethod, if Full Amount Auth setting for merchant is turned on 
						      // this results into first cycle's bill being fully authorized
						100, // fraud score tolerance - scoring turned off here
						false, // do not ignore avs policy evaluation
						false, // do not ignore cvv policy evaluation
						campaignCode, // promo or coupon code
						false // no dry run
						);
				
				// If we are here, we got a 200 response back from the server - meaning
				// payment method validation, avs check, and cvv check succeeded
				// 
				if (abur.getReturnObject().getReturnCode().getValue() == 200) {
					System.out.println("Successfully created autobill with id " + abill.getMerchantAutoBillId());					
					System.out.print("First bill amount: ");
					System.out.print(abur.getFirstBillAmount() != null ? abur.getFirstBillAmount().floatValue() : "N/A");
					System.out.println(abur.getFirstBillingCurrency() != null ? " " + abur.getFirstBillingCurrency() : "");
					System.out.print("First bill date: ");
					System.out.println(abur.getFirstBillDate() != null ? getPrintableDate(abur.getFirstBillDate()) : "N/A");
					// Log call's soap id for troubleshooting
					System.out.println("AutoBill.update call's soap ID: " + abur.getReturnObject().getSoapId());
				}
				else {
					// We should not reach here
					System.out.println("AutoBill creation failed, return code: " + abur.getReturnObject().getReturnCode() + " return string: '" + abur.getReturnObject().getReturnString() + "'");
					System.out.println("Soap id " + abur.getReturnObject().getSoapId());
				}
			
		}
		catch (VindiciaReturnException vre) {
			// If we are here, we should assume that the AutoBill creation failed
			// Example below shows various return codes from CashBox and what they mean
			
			String mainMessage = "AutoBill creation failed: ";
			
			if (vre.getReturnCode().equals("408")) {
				//CVV check failed
				System.out.println("Vindicia response string: " + vre.getMessage() + " , Call SOAP ID: " + vre.getSoapId());
					
			}
			else if (vre.getReturnCode().equals("407")) {

				//AVS Check Failed
				System.out.println("Vindicia response string: " + vre.getMessage() + " , Call SOAP ID: " + vre.getSoapId());
			}
			else if (vre.getReturnCode().equals("409")){

				//AVS and CVV Check Failed	
				System.out.println("Vindicia response string: " + vre.getMessage() + " , Call SOAP ID: " + vre.getSoapId());
			}
			else if (vre.getReturnCode().equals("410")) {
				//AVS and CVV check could not be performed
				System.out.println("Vindicia response string: " + vre.getMessage() + " , Call SOAP ID: " + vre.getSoapId());
					
			}
			else if (vre.getReturnCode().equals("402")) {
				// Card authorization failed
				System.out.println("Vindicia response string: " + vre.getMessage() + " , Call SOAP ID: " + vre.getSoapId());
			}
			else if (vre.getReturnCode().equals("400")) {
				// Other failure
				System.out.println("Vindicia response string: " + vre.getMessage() + " , Call SOAP ID: " + vre.getSoapId());
				
			}
		}
		catch (Exception e) {
			// System exception such as time out or network connectivity issue 
			// Here we have not received any response or unexpected response from our request
			e.printStackTrace();
		}
		
	}
	
	
	protected static String getPrintableDate( java.util.Date dt ) {
		java.text.DateFormat df = new java.text.SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
		return df.format(dt);
	}
	

}
