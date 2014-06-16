import java.util.ResourceBundle;

import com.vindicia.client.*;

/*
 * This sample illustrates how you can create or update a CashBox Product.
 * Most merchants create/update CashBox Products using the web portal, but it is also possible to do so via
 * CashBox API call as illustrated in this sample code
 */
public class ProductCreator {

	public static void main(String[] args) {
		ResourceBundle rb = ResourceBundle.getBundle("properties.Environment");
		com.vindicia.client.ClientConstants.SOAP_LOGIN = rb.getString("soap_login");
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = rb.getString("soap_password");
		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL= rb.getString("soap_url");
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = Integer.parseInt(rb.getString("soap_timeout"));
		
		com.vindicia.client.Product p = new com.vindicia.client.Product();
		
		p.setMerchantProductId("UniqueProductID");  // specify a unique product ID i.e. SKU
		
		// Product description is mandatory - this becomes line item description in the transaction processed
		// when a customer is billed for this Product
		com.vindicia.soap.v5_0.Vindicia.ProductDescription descr = new com.vindicia.soap.v5_0.Vindicia.ProductDescription();
		descr.setLanguage("EN"); // ISO language code
		descr.setDescription("Monthly service");
		
		p.setDescriptions(new com.vindicia.soap.v5_0.Vindicia.ProductDescription[] {descr});
		
		// Specify Product's price in various currencies
		// If you are going to specify prices exclusively on BillingPlans, you should specify price 0
		com.vindicia.soap.v5_0.Vindicia.ProductPrice priceUSD = new com.vindicia.soap.v5_0.Vindicia.ProductPrice();
		priceUSD.setAmount(new java.math.BigDecimal(20.00));
		priceUSD.setCurrency("USD");
		
		// Specify Product's price in various currencies
		com.vindicia.soap.v5_0.Vindicia.ProductPrice priceCAD = new com.vindicia.soap.v5_0.Vindicia.ProductPrice();
		priceCAD.setAmount(new java.math.BigDecimal(22.00));
		priceCAD.setCurrency("CAD");
		
		p.setPrices(new com.vindicia.soap.v5_0.Vindicia.ProductPrice[] { priceUSD, priceCAD});
		
		// If you are using CashBox's entitlement management ...
		// Create ID meaningful to you in your application describing customer's entitlement when he/she purchases this product
		com.vindicia.soap.v5_0.Vindicia.MerchantEntitlementId entitlementId = new com.vindicia.soap.v5_0.Vindicia.MerchantEntitlementId();
		entitlementId.setId("MonthlyAccess");
		entitlementId.setDescription("Customer gets access to regular monthly subscription");
		
		p.setMerchantEntitlementIds(new com.vindicia.soap.v5_0.Vindicia.MerchantEntitlementId[] {entitlementId} );
		
		// Set status to active - optional - this is for information purposes only
		p.setStatus(com.vindicia.soap.v5_0.Vindicia.ProductStatus.Active);

		try {
			boolean created = p.update(null); //duplicateBehavior input parameter is no op - hence set to null
			// if we are here, we got a 200-OK response from the server
			if (created) {
				System.out.print("Successfully created product " + p.getMerchantProductId());
				System.out.println(", Vindicia assigned Product ID " + p.getVID());
			}
			else {
				// This product already existed in CashBox and was only updated by this call
				System.out.print("Successfully updated product " + p.getMerchantProductId());
			}
			
		}
	
		catch (VindiciaReturnException vre) {
			// VindiciaReturnException indicates a non-200 response code from the server
			
			System.out.println("Product creation/update failed, return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
			
		}
		catch (Exception e) {
			// Handle more serious exception such as timeout or network drop here
			System.out.println("Product creation/update failed" );
			e.printStackTrace();
			
		}
		
	}

}
