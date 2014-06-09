import com.vindicia.client.*;

/*
 * This class illustrates how you can use Vindicia's Java Client Library for CashBox API version 5.0 to retrieve all
 * Products you have defined in your CashBox merchant account
 * 
 */
public class AllProductFetcher {

	public static void main(String[] args) {

		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL = "https://soap.prodtest.sj.vindicia.com";
		com.vindicia.client.ClientConstants.SOAP_LOGIN = "XXXXXX_soap";
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = "XXXXXXXXXXXXXXXXX";
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = 25000; // ms
		
		com.vindicia.client.Product[] prods = null;
		int page = 0;
		int pageSize = 10; // adjust based on how many products you have, too large of a page size may cause time outs
		boolean hasMore = true;
		int totalProductCount = 0;
		
		// Loop through the pages until the server has no more data to return
		
		while (hasMore) {
			try {
				prods = com.vindicia.client.Product.fetchAll(page, pageSize);
				// if we are here we did not get a bad (non-200) response code
				if (prods != null) {
					for (int j=0; j<prods.length; j++) {
						
						// process fetched Products here
						// In this sample we will simply print Product's ID and description to the command line
						
						System.out.print("Product ID: " + prods[j].getMerchantProductId());
						if (prods[j].getDescriptions() != null) {
							
							System.out.println(", Description: " + prods[j].getDescriptions(0).getDescription());
						}
						totalProductCount++;
						
					}
					page++;
				}
				else {
					// we should never reach here since no products means 404 return code and a VindiciaReturnException
					// but to be full proof
					System.out.print("No products returned on page " + page);
					hasMore = false;
					break;
					
				}
			}
			catch (VindiciaReturnException vre) {
				if (vre.getReturnCode().equals("404")) {
					// no more pages;
					System.out.print("No products returned on page " + page);
					hasMore = false;
					break;
				}
			}
			catch (Exception e) {
				// a more serious exception such as time out or network drop out
				System.out.println("Product fetch failed on page " + page );
				e.printStackTrace();
				hasMore = false;
				break;
				
			}
					
		} // end while
		System.out.println("\n\nFetched total " + totalProductCount + " products");
		
	}

}
