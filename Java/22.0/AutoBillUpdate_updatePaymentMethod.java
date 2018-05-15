import com.vindicia.client.AutoBillUpdateReturn;
import com.vindicia.client.VindiciaReturnException;

public class AutoBillUpdate_updatePaymentMethod {
	
	public static void main(String[ ] args) {
		System.out.println("*****AutoBill Update Example*******");
		
		
		com.vindicia.client.ClientConstants.SOAP_LOGIN = "XXXXXX";
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = "XXXXXX";
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL= "https://soap.prodtest.sj.vindicia.com";
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = 20000;
		
		
		
		//Set the Payment Method ID from Tx.Auth call
		//the Payment method used in tx.auth call is a creditcard
		com.vindicia.client.PaymentMethod pm = new com.vindicia.client.PaymentMethod();
		pm.setActive(true);
		pm.setMerchantPaymentMethodId("ExperianPMID1122");
	
		
	
		//Set AB values on which the new PM should be applied
		//which has MAP as PaymentMethod initially
		
		com.vindicia.client.AutoBill aBill = new com.vindicia.client.AutoBill();
		aBill.setMerchantAutoBillId("AutoBillID1122");
		aBill.setSourceIp("123.123.123.123");
		aBill.setPaymentMethod(pm);
		
	
		
		
		boolean validateForFuturePayment = false;
		int fraudScore = 100;
		boolean ignoreAVS = true; 
		boolean ignoreCVN = true;
		String campaign = null;
		boolean dryrun = false;
		String cancelCode = null;
		
		
		
		try {
			
			//AB.update
			AutoBillUpdateReturn abReturn = new AutoBillUpdateReturn();
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
			
			System.out.println("AB update failed, return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
			
		}
		catch (Exception e) {
			// Handle more serious exception such as timeout or network drop here
			System.out.println("Autobill update failed" );
			e.printStackTrace();
			
		}
		
		
		
	}


}
