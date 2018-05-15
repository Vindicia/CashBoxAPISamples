import com.vindicia.client.TransactionAuthReturn;
import com.vindicia.client.TransactionCaptureReturn;
import com.vindicia.client.VindiciaReturnException;
import java.util.*;

public class TransactionCapture {
	
	
	public static void main(String[ ] args) {
		System.out.println("*****Transaction CAPTURE Example ******");
		
		
		com.vindicia.client.ClientConstants.SOAP_LOGIN = "XXXXXXXXXX";
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = "XXXXXXXXXX";
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL= "https://soap.prodtest.sj.vindicia.com";
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = 20000;
		
		
		com.vindicia.client.Transaction soapTX = new com.vindicia.client.Transaction();
		
		//get the TXIds from the Transaction.Auth API call and pass them here so they will be captured
		com.vindicia.client.Transaction tx1 = new com.vindicia.client.Transaction();
		tx1.setMerchantTransactionId("ExperianTXID921");
		
		//com.vindicia.client.Transaction tx2 = new com.vindicia.client.Transaction();
		//tx2.setMerchantTransactionId("ExperianTXID405");
		
		
		
		try {
			
			
			
			TransactionCaptureReturn txCaptureRtn  = new TransactionCaptureReturn();
			com.vindicia.client.Transaction[] transactionArray = new com.vindicia.client.Transaction[] {tx1};
			//com.vindicia.client.Transaction[] transactionArray = new com.vindicia.client.Transaction[] {tx1,tx2};
			txCaptureRtn = soapTX.capture("", transactionArray);
			
			
			System.out.println("txCaptureRtn ReturnCode value : " + txCaptureRtn.getReturnObject().getReturnCode().getValue() );
			System.out.println("txCaptureRtn soap ID is : " + txCaptureRtn.getReturnObject().getSoapId());
			System.out.println("txCaptureRtn return object is :" + txCaptureRtn.getReturnObject());
			
			
		}catch (VindiciaReturnException vre) {
			// VindiciaReturnException indicates a non-200 response code from the server
			
			System.out.println("txCaptureRtn  failed, return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
			//System.out.println("AddChildren failed, return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
		}
		catch (Exception e) {
			// Handle more serious exception such as timeout or network drop here
			System.out.println("txCaptureRtn  failed" );
			e.printStackTrace();
			
		}
		
		
		
		
		  
		
	}
	
}
