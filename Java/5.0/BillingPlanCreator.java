import java.util.Calendar;

import com.vindicia.client.*;
import com.vindicia.soap.v5_0.*;
import com.vindicia.soap.v5_0.Vindicia.BillingPeriodType;
import com.vindicia.soap.v5_0.Vindicia.BillingPlanPeriod;
import com.vindicia.soap.v5_0.Vindicia.BillingPlanPrice;
import com.vindicia.soap.v5_0.Vindicia.BillingPlanStatus;
import com.vindicia.soap.v5_0.Vindicia.MerchantEntitlementId;

/*
 * This sample illustrates how you can create or update a CashBox Billing Plan.
 * Most merchants create/update CashBox Billing Plans using the web portal, but it is also possible to do so via
 * CashBox API call as illustrated in this sample code. This sample creates a billing plan that will bill a
 * customer recurrently every month with first month free.
 */

public class BillingPlanCreator {

	public static void main(String[] args) {

		com.vindicia.client.ClientConstants.DEFAULT_VINDICIA_SERVICE_URL = "https://soap.prodtest.sj.vindicia.com";
		com.vindicia.client.ClientConstants.SOAP_LOGIN = "XXXXXX_soap";
		com.vindicia.client.ClientConstants.SOAP_PASSWORD = "XXXXXXXXXXXXXXXXX";
		com.vindicia.client.ClientConstants.DEFAULT_TIMEOUT = 25000; // ms

		
		com.vindicia.client.BillingPlan monthlyWithFreeTrialMonthPlan = new com.vindicia.client.BillingPlan();
		
		monthlyWithFreeTrialMonthPlan.setMerchantBillingPlanId("UniquePlanID-" + System.currentTimeMillis()); // using system time to get a unique ID
		monthlyWithFreeTrialMonthPlan.setDescription("Monthly recurring with free first month"); // Optional
		monthlyWithFreeTrialMonthPlan.setStatus(com.vindicia.soap.v5_0.Vindicia.BillingPlanStatus.Active); // optional, for info purposes only

		// The plan contains 2 periods, first period is free and lasts for 1 month
		com.vindicia.soap.v5_0.Vindicia.BillingPlanPeriod freePeriod = new com.vindicia.soap.v5_0.Vindicia.BillingPlanPeriod();
		freePeriod.setFree(true);
	
		// we want 1 cycle of 1 month
		freePeriod.setType(com.vindicia.soap.v5_0.Vindicia.BillingPeriodType.Month);
		freePeriod.setQuantity(1); // a cycle is 1 Month
		freePeriod.setCycles(1); // 1 free cycle
		
		// second period is a paid period that contains infinite monthly cycles
		
		com.vindicia.soap.v5_0.Vindicia.BillingPlanPeriod paidPeriod = new com.vindicia.soap.v5_0.Vindicia.BillingPlanPeriod();

		paidPeriod.setType(com.vindicia.soap.v5_0.Vindicia.BillingPeriodType.Month);
		paidPeriod.setQuantity(1); // a cycle is 1 month
		paidPeriod.setCycles(0); // indicates infinite number of cycles
		
		// define prices in various currencies
		// If your prices are going to be specified on Product objects only, you still have to set a price of 0 here
		com.vindicia.soap.v5_0.Vindicia.BillingPlanPrice priceUSD = new com.vindicia.soap.v5_0.Vindicia.BillingPlanPrice();
		priceUSD.setAmount(new java.math.BigDecimal(10.00));
		priceUSD.setCurrency("USD");

		com.vindicia.soap.v5_0.Vindicia.BillingPlanPrice priceCAD = new com.vindicia.soap.v5_0.Vindicia.BillingPlanPrice();
		priceCAD.setAmount(new java.math.BigDecimal(12.00));
		priceCAD.setCurrency("CAD");
		
		paidPeriod.setPrices(new com.vindicia.soap.v5_0.Vindicia.BillingPlanPrice[] { priceUSD, priceCAD });
		
		monthlyWithFreeTrialMonthPlan.setPeriods(new com.vindicia.soap.v5_0.Vindicia.BillingPlanPeriod[]{freePeriod, paidPeriod});

		// Now make the CashBox SOAP API call to create the billing plan in the Vindicia system
		try {
			boolean created = monthlyWithFreeTrialMonthPlan.update();
			if (created) {
				System.out.print("Billing plan " + monthlyWithFreeTrialMonthPlan.getMerchantBillingPlanId());
				System.out.println(" successfully created. Vindicia assigned ID is " + monthlyWithFreeTrialMonthPlan.getVID());
			}
			else {
				// The plan already existed in CashBox.
				// We should never reach here if the merchantBillingPlanId used above was all new and unique
				System.out.print("Billing plan " + monthlyWithFreeTrialMonthPlan.getMerchantBillingPlanId());
				System.out.println(" updated");
				
			}
		}
		catch (VindiciaReturnException vre) {
					
			System.out.println("Billing plan creation failed, return code: " + vre.getReturnCode() + " return string: '" + vre.getMessage() + "'" + " Soap id: " + vre.getSoapId());
				
		}
		catch (Exception e) {
				System.out.println("Billing plan creation failed" );
				e.printStackTrace();				
		}
		
	}
	
}
