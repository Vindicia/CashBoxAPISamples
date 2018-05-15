import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import com.vindicia.client.AutoBillMakePaymentReturn;
import com.vindicia.client.AutoBillUpdateReturn;
import com.vindicia.client.VindiciaReturnException;

public class AutoBillMakePayment {
	
	public static void main(String[ ] args) {
		System.out.println("*****AutoBill Make Payment with MAP Example*******");
		
		
		com.vindicia.client.ClientConstants.SOAP_LOGIN = "XXXXXXXXXX";
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = "XXXXXXXXXX";
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL= "https://soap.prodtest.sj.vindicia.com";
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = 20000;
		
		
		//set the ABID from the AB.updateMAP call
		com.vindicia.client.AutoBill aBill = new com.vindicia.client.AutoBill();
		aBill.setMerchantAutoBillId("AutoBillID250");
		
		//set the MAP PM ID from the AB.updateMAP call
		com.vindicia.client.PaymentMethod pm = new com.vindicia.client.PaymentMethod();
		pm.setMerchantPaymentMethodId("MAPID250");
		
		String currency = "GBP";
		
		//invoice ID is optional
		String invoiceID = "AutoBillID250-00000000";
		//String overageDisposition = "applyToThisAutoBill";
		boolean usePMForFutureBilling = false;
		String note = "payment against $0 invoice";
		
		
		try {
			
			//AB.MakePayment
			AutoBillMakePaymentReturn abReturn = new AutoBillMakePaymentReturn();
			abReturn = aBill.makePayment("", pm, new java.math.BigDecimal(14.99), currency, invoiceID, com.vindicia.soap.v22_0.Vindicia.PaymentOverageDisposition.applyToThisAutoBill, usePMForFutureBilling, note);
					
					 
			if (abReturn.getReturnObject().getReturnCode().getValue() ==200) {
				
				System.out.println("AB MakePayment return code is : " + abReturn.getReturnObject().getReturnCode().getValue());
				System.out.println("AB MakePayment SOAP ID is :" + abReturn.getReturnObject().getSoapId());
				System.out.println("AB MakePayment  ID is :" + aBill.getMerchantAutoBillId());
				
			}else {
				System.out.println("AB MakePayment call failed");
				System.out.println("AB MakePayment return code is : " + abReturn.getReturnObject().getReturnCode().getValue());
			}
		}
	
		catch (VindiciaReturnException vre) {
			// VindiciaReturnException indicates a non-200 response code from the server
			
			System.out.println("AB MakePayment failed, return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
			//System.out.println("AddChildren failed, return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
		}
		catch (Exception e) {
			// Handle more serious exception such as timeout or network drop here
			System.out.println("Autobill MakePayment failed" );
			e.printStackTrace();
			
		}
		
		
		
	}


}
