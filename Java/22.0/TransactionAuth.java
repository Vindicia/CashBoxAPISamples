import com.vindicia.client.TransactionAuthReturn;
import com.vindicia.client.TransactionCaptureReturn;
import com.vindicia.client.VindiciaReturnException;
import java.util.*;

public class TransactionAuth {
	
	
	public static void main(String[ ] args) {
		System.out.println("*****Transaction Auth Example*******");
		
		
		com.vindicia.client.ClientConstants.SOAP_LOGIN = "XXXXXXXXXX";
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = "XXXXXXXXXX";
		
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL= "https://soap.prodtest.sj.vindicia.com";
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = 20000;
		
		
		
		// create instance of Random class
        Random rand = new Random();
 
        // Generate random integers in range 0 to 999
        int rand_int1 = rand.nextInt(1000);
		
		//create Address object
		com.vindicia.client.Address addr = new com.vindicia.client.Address();
		addr.setAddr1("2226 HillsDale Ave");
		addr.setCity("London");
		addr.setPostalCode("SW789 SJ6");
		addr.setDistrict("England");
		addr.setCountry("GB");
		
		
		//create Account object
		com.vindicia.client.Account acct = new com.vindicia.client.Account();
		acct.setMerchantAccountId("AccountID"+rand_int1);  
		//acct.setShippingAddress(addr);
		acct.setEmailAddress("testemail@vindicia.com");
		acct.setName("Acct Name "+rand_int1);
		
		
		//create CreditCard object
		com.vindicia.soap.v22_0.Vindicia.CreditCard cc = new com.vindicia.soap.v22_0.Vindicia.CreditCard();
		
		cc.setAccount("4111111111111111"); // credit card number
		cc.setExpirationDate("201908"); // expiration date (YYYYMM format)
		com.vindicia.soap.v22_0.Vindicia.NameValuePair NV1 = new com.vindicia.soap.v22_0.Vindicia.NameValuePair();
		NV1.setName("CVN");
		NV1.setValue("123");
		
		com.vindicia.soap.v22_0.Vindicia.NameValuePair[]  NVP = new com.vindicia.soap.v22_0.Vindicia.NameValuePair[] {NV1};
		
		//create PaymentMethod object
		com.vindicia.client.PaymentMethod pm = new com.vindicia.client.PaymentMethod();
		pm.setAccountHolderName("CCPayerName" +rand_int1);
		pm.setActive(true);
		pm.setType(com.vindicia.soap.v22_0.Vindicia.PaymentMethodType.CreditCard);
		pm.setCreditCard(cc);
		pm.setBillingAddress(addr);
		pm.setMerchantPaymentMethodId("Experian_PMID" +rand_int1);
		pm.setNameValues(NVP);
		
	
		
		//create Transaction object
		com.vindicia.client.Transaction transaction = new com.vindicia.client.Transaction();
		transaction.setAccount(acct);
		transaction.setSourcePaymentMethod(pm);
		transaction.setCurrency("GBP");
		transaction.setShippingAddress(addr);
		transaction.setSourceIp("123.123.123.123");
		transaction.setMerchantTransactionId("ExperianTXID" + rand_int1);
		
		//TransactionItem object aka Product, this product need NOT be present in the system
		com.vindicia.soap.v22_0.Vindicia.TransactionItem  txItem1 = new com.vindicia.soap.v22_0.Vindicia.TransactionItem();
		txItem1.setSku("ExperianProduct1");
		txItem1.setName("Experian Product 1");
		txItem1.setPrice(new java.math.BigDecimal(14.99));
		txItem1.setQuantity(new java.math.BigDecimal(1));
		
		
		com.vindicia.soap.v22_0.Vindicia.TransactionItem[]  txItemArray = new com.vindicia.soap.v22_0.Vindicia.TransactionItem[] {txItem1};
		
		transaction.setTransactionItems(txItemArray);
		transaction.setShippingAddress(addr);
		
		
		String campaignCode = null;
		boolean dryrun = false;
		int minChargebackProbability = 100;
		boolean sendEmailNotification = true;
		
		try {
			TransactionAuthReturn txreturn  = new TransactionAuthReturn();
			txreturn = transaction.auth("",minChargebackProbability,sendEmailNotification,campaignCode,dryrun);
			
			
			System.out.println("TxAuth ReturnCode value : " + txreturn.getReturnObject().getReturnCode().getValue() );
			System.out.println("TxAuth soap ID is : " + txreturn.getReturnObject().getSoapId());
			System.out.println("TxAuth Score value is : " + txreturn.getScore());
			System.out.println("TxAuth return object is :" + txreturn.getReturnObject());
			
		}catch (VindiciaReturnException vre) {
			// VindiciaReturnException indicates a non-200 response code from the server
			System.out.println("TxAuth  failed, return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
			}
		catch (Exception e) {
			// Handle more serious exception such as timeout or network drop here
			System.out.println("TxAuth  failed" );
			e.printStackTrace();
			
		}
		
		
		
		
		  
		
	}
	
}
