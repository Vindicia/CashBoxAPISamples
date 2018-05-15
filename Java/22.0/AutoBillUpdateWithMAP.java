import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import com.vindicia.client.AutoBillUpdateReturn;
import com.vindicia.client.VindiciaReturnException;

public class AutoBillUpdateWithMAP {
	
	public static void main(String[ ] args) {
		System.out.println("*****AutoBill Update with MAP  Example*******");
		
		
		com.vindicia.client.ClientConstants.SOAP_LOGIN = "XXXXXXXXXX";
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = "XXXXXXXXXX";
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL= "https://soap.prodtest.sj.vindicia.com";
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = 20000;
		
		// create instance of Random class
        Random rand = new Random();
 
        // Generate random integers in range 0 to 999
        int rand_int1 = rand.nextInt(1000);
        
        com.vindicia.client.Address addr = new com.vindicia.client.Address();
		
		addr.setAddr1("2226 Campus Drive");
		addr.setCity("London");
		addr.setPostalCode("SW789 SJ6");
		addr.setDistrict("England");
		addr.setCountry("GB");
		
		
		
		//Assign the existing Account ID created before via transaction.auth call
		com.vindicia.client.Account acct = new com.vindicia.client.Account();
		acct.setMerchantAccountId("AccountID921");  
		
		//Assign the existing Product
		com.vindicia.client.Product prod = new com.vindicia.client.Product();
		prod.setMerchantProductId("ExperianProductID2");
		
		//Assign the existing BP with first month free cycle
		com.vindicia.soap.v22_0.Vindicia.BillingPlan bPlan = new com.vindicia.soap.v22_0.Vindicia.BillingPlan();
		bPlan.setMerchantBillingPlanId("Experian_Monthly_BP_1freeCycle");
		
		
		//create a Payment Method of type MAP
		com.vindicia.client.PaymentMethod pm = new com.vindicia.client.PaymentMethod();
		com.vindicia.soap.v22_0.Vindicia.MerchantAcceptedPayment mapPayment = new com.vindicia.soap.v22_0.Vindicia.MerchantAcceptedPayment();
		
		//required fields to be set on MAP object
		mapPayment.setAmount(new java.math.BigDecimal(0.00));
		mapPayment.setPaymentType("Pay By invoice");
		
		
		pm.setMerchantPaymentMethodId("MAPID" +rand_int1);
		pm.setAccountHolderName("MAP Accout Holder Name");
		pm.setCurrency("GBP");
		pm.setCustomerDescription("MAP");
		pm.setBillingAddress(addr);
		pm.setType(com.vindicia.soap.v22_0.Vindicia.PaymentMethodType.MerchantAcceptedPayment);
		pm.setMerchantAcceptedPayment(mapPayment);
		
		
		
		//AB stuff
		
		com.vindicia.soap.v22_0.Vindicia.AutoBillItem item = new com.vindicia.soap.v22_0.Vindicia.AutoBillItem();
		item.setProduct(prod);
		item.setMerchantAutoBillItemId("ABItemID"+rand_int1);
		item.setCurrency("GBP");
		
		com.vindicia.soap.v22_0.Vindicia.AutoBillItem[]  abItemArray = new com.vindicia.soap.v22_0.Vindicia.AutoBillItem[] {item};
		
	
		
		com.vindicia.client.AutoBill aBill = new com.vindicia.client.AutoBill();
		aBill.setMerchantAutoBillId("AutoBillID" +rand_int1);
		aBill.setAccount(acct);
		aBill.setBillingPlan(bPlan);
		aBill.setItems(abItemArray);
		aBill.setCurrency("GBP");
		aBill.setSourceIp("123.123.123.123");
		
		//set the MAP payment method on Autobill so Autobill will use MAP instead of default PM on the account
		//if MAP is set on account object then Autobill is unable to use MAP and will use default PM (sortorder = 0)
		
		aBill.setPaymentMethod(pm);
		
		
		//aBill.setMerchantAffiliateId("400");
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");	
		//Calendar calendar = new GregorianCalendar(2018,05,05);
		//aBill.setStartTimestamp(calendar);
		
		aBill.setStatementFormat(com.vindicia.soap.v22_0.Vindicia.StatementFormat.Inline);
		aBill.setStatementTemplateId("EXP-InvoiceTest");
		
		boolean validateForFuturePayment = false;
		int fraudScore = 100;
		boolean ignoreAVS = false; 
		boolean ignoreCVN = false;
		String campaign = null;
		boolean dryrun = false;
		String cancelCode = null;
		
		
		
		try {
			
			//AB.update
			AutoBillUpdateReturn abReturn = new AutoBillUpdateReturn();
			//abReturn = aBill.update("",com.vindicia.soap.v21_0.Vindicia.ImmediateAuthFailurePolicy.putAutoBillInRetryCycle,validateForFuturePayment,fraudScore,ignoreAVS,ignoreCVN,campaign,dryrun,cancelCode,com.vindicia.soap.v21_0.Vindicia.InitialAuthStrategy.AuthImminentBilling); 
			
			abReturn = aBill.update("",com.vindicia.soap.v22_0.Vindicia.ImmediateAuthFailurePolicy.putAutoBillInRetryCycle,validateForFuturePayment,fraudScore,ignoreAVS,ignoreCVN,campaign,dryrun,cancelCode,com.vindicia.soap.v22_0.Vindicia.InitialAuthStrategy.AuthImminentBilling); 
			if (abReturn.getReturnObject().getReturnCode().getValue() ==200) {
				
				System.out.println("AB update return code is : " + abReturn.getReturnObject().getReturnCode().getValue());
				System.out.println("AB update SOAP ID is :" + abReturn.getReturnObject().getSoapId());
				System.out.println("AB update  ID is :" + aBill.getMerchantAutoBillId());
				
			}else {
				System.out.println("AB update call failed");
				System.out.println("AB update return code is : " + abReturn.getReturnObject().getReturnCode().getValue());
			}
		}
	
		catch (VindiciaReturnException vre) {
			// VindiciaReturnException indicates a non-200 response code from the server
			
			System.out.println("AB update with MAP failed, return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
		}
		catch (Exception e) {
			// Handle more serious exception such as timeout or network drop here
			System.out.println("Autobill update with MAP failed" );
			e.printStackTrace();
			
		}
		
		
		
	}


}
