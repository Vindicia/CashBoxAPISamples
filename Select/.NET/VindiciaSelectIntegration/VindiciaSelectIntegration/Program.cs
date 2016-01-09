using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VindiciaSelectIntegration.com.vindicia.soap;


namespace VindiciaSelectIntegration
{
    class Program
    {
        static void Main(string[] args)
        {
            Select select = new Select();

            Authentication auth = new Authentication();
            auth.version = "1.1";   // Specify which Select API Version (1.0 or 1.1)

            PropertyFile rb = PropertyFile.getBundle("Environment");
            select.Url = rb.get("soap_url");
            auth.login = rb.get("soap_login");
            auth.password = rb.get("soap_password");

            // ProdTest
            //select.Url = "https://soap.prodtest.sj.vindicia.com/v1.0/soap.pl";
            //select.Url = "https://soap.prodtest.sj.vindicia.com/soap.pl";
            //auth.login = "xxx_soap";
            //auth.password = "";

            // Staging:
            //select.Url = https://soap.staging.sj.vindicia.com/soap.pl
            //auth.login = xxx_soap
            //auth.password = "";

            // Production:
            //select.Url = https://soap.vindicia.com/soap.pl
            //auth.login = xxx_soap
            //auth.password = "";

            Console.WriteLine("soapVersion=" + SEL001BillSelect.getSelectVersion(auth));
            Console.WriteLine();
            Console.WriteLine("Using version=" + auth.version);
            Console.WriteLine("soapURL=" + select.Url);
            Console.WriteLine("soapLogin=" + auth.login);

            Boolean bBill = true;
            Boolean bFetch = true;

            if (bBill)
            {
                SEL001BillSelect bill = new SEL001BillSelect();

                string startMerchantTransactionId = "FAILED" + DateTime.Now.DayOfYear + DateTime.Now.Hour + DateTime.Now.Minute + DateTime.Now.Second;
                string soapId = bill.run(select, auth, startMerchantTransactionId);

                Console.WriteLine("billTransactions soapId=" + soapId + "\n");
            }

            if (bFetch)
            {
                SEL002FetchSelect.run(select, auth);

                Console.WriteLine("fetchBillingResults completed.\n");
            }

            Console.ReadLine();
        }
    }
}
