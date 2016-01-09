using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VindiciaSelectIntegration.com.vindicia.soap;

namespace VindiciaSelectIntegration
{
    class SEL001BillSelect
    {
        public string run(Select select, Authentication auth, string transactionId)
        {
            int returnCode;
            string returnString;
            string soapId = null;

            log( timestamp() + " BillSelect.run(): merchantTransactionId=" + transactionId );

            select.Timeout = 10000;

            // get Transaction array to submit:
            Transaction[] transactions = new Transaction[1];
            for (int i=0; i < transactions.Length; i++) {
                transactions[i] = getTransaction( transactionId + i );
            }

            for (int i = 0; i < transactions.Length; i++)
            {
                Transaction tx = transactions[i];
                TransactionStatusType status = tx.status;
                log("merchantTransactionId " + tx.merchantTransactionId
                    + " with status " + tx.status
                    + " , authCode " + tx.authCode
                    + " on " + tx.timestamp
                    + " for " + tx.amount + " " + tx.currency
                    );
            }

            try {
                // make the call to billTransactions:
                log("Beginning request to bill transactions");
                TransactionValidationResponse[] transactionValidationResponse;
                Return _return = select.billTransactions(auth, transactions, out transactionValidationResponse);
		
                log("Completed request to bill transactions");
                // Return.returnCode
                returnCode = _return.returnCode;
                // Return.returnString
                returnString = _return.returnString;
                // Return.soapId
                soapId = _return.soapId;

                // returnCode is numeric value as in: if (returnCode == 200)
                log("billTransactions ReturnCode: " + returnCode);
                log("billTransactions ReturnString: " + returnString);
                log(timestamp() + " billTransactions soapId: " + soapId);

                if (200 == returnCode)
                {
                    for (int j=0; null != transactionValidationResponse && j < transactionValidationResponse.Length; j++) {
                        log("validationResult[" + j + "]=" + transactionValidationResponse[j].code + ", "
                            + transactionValidationResponse[j].description + ", "
                            + "merchantTransactionId=" + transactionValidationResponse[j].merchantTransactionId);
                    }
                }
                else
                {
                    log(timestamp() + " SEL001_BillSelect: billTransactions failed: " + returnCode +
                            " - " + returnString + ", soapId: " + soapId + "\n");
                }
            }
            catch (Exception e)
            {
                log(timestamp() + " SEL001_BillSelect: billTransactions failed: " + e.Message + "\n");
            }

            return soapId;
        }

        public static void log(string message)
        {
            Console.WriteLine(message);
        }

        public static void log(Exception e)
        {
            log(e.Message);
            log(e.StackTrace);
        }

        public static String getSelectVersion(Authentication auth)
        {
            return auth.version;
        }

        public static string timestamp()
        {
            DateTime now = DateTime.Now;
            return now.ToString();
        }

        public static String dateString(String name, DateTime date)
        {

            return (name + ": " + date);
        }

        public static string whyFail_Soft = "Decline";
        public static string whyFail_Hard = "Hard";

        public static Transaction getTransaction(string transactionId)
        {
            Transaction trans = new Transaction();

            trans.merchantTransactionId = transactionId;
            trans.timestamp = DateTime.Now;
            trans.amount = new Decimal(10.99);
            trans.currency = "USD";

            TransactionStatusType status = TransactionStatusType.Failed;
            trans.status = status;

            string divisionNumber = "5498";
            trans.divisionNumber = divisionNumber;

            NameValuePair nvp = new NameValuePair();
            nvp.name = "litle:reportGroup";
            nvp.value = "Test";
            trans.nameValues = new NameValuePair[1];
            trans.nameValues[0] = nvp;

            string subscriptionId = transactionId;
            trans.subscriptionId = subscriptionId;

            string customerId = transactionId;
            trans.customerId = customerId;

            string paymentMethodId = transactionId;
            trans.paymentMethodId = paymentMethodId;

            TransactionStatusType resultType;
            string why;

            // split requested results into Captured & Failed: Hard & Soft Fails
            char c = transactionId[transactionId.Length-1];
            int index = c - 0x30;
            switch (index % 2)
            {
                case 0:
                    // To use a Card that will be Captured:
                    resultType = TransactionStatusType.Captured;
                    why = null;
                    break;
                default:
                    // To use a Card that will be Failed:
                    resultType = TransactionStatusType.Failed;
                    switch (index % 3)
                    {
                        case 0:
                            // Soft Fail:
                            why = whyFail_Soft;
                            break;
                        default:
                            // Hard Fail:
                            why = whyFail_Hard;
                            break;
                    }
                    break;
            }
            string creditCardAccount = getCreditCardAccount(resultType, why);
            trans.creditCardAccount = creditCardAccount;

            string creditCardExpirationDate = "202208";
            trans.creditCardExpirationDate = creditCardExpirationDate;

            string authCode = "302";
            trans.authCode = authCode;


            // Additional data members:
            string affiliateId = "Affiliate" + divisionNumber;
            trans.affiliateId = affiliateId;
            string affiliateSubId = "SubAffiliate" + divisionNumber;
            trans.affiliateSubId = affiliateSubId;
            string billingAddressCity = "Any City";
            trans.billingAddressCity = billingAddressCity;
            string billingAddressCountry = "US";
            trans.billingAddressCountry = billingAddressCountry;
            string billingAddressCounty = "Any County";
            trans.billingAddressCounty = billingAddressCounty;
            string billingAddressDistrict = "Any State (i.e. District)";
            trans.billingAddressDistrict = billingAddressDistrict;
            string billingAddressLine1 = "123 Main (Address Line 1)";
            trans.billingAddressLine1 = billingAddressLine1;
            string billingAddressLine2 = "Suite 5 (Address Line 2)";
            trans.billingAddressLine2 = billingAddressLine2;
            string billingAddressLine3 = "Internet Widgets Co. Ltd. (Address Line 3)";
            trans.billingAddressLine3 = billingAddressLine3;
            string billingAddressPostalCode = "94002";
            trans.billingAddressPostalCode = billingAddressPostalCode;
            DateTime startDate = DateTime.Now.AddYears(-1);
            trans.subscriptionStartDate = startDate;

            return trans;
        }

        public static string getCreditCardAccount(TransactionStatusType resultType, string why)
        {
            string creditCardAccount = null;
            if (resultType == TransactionStatusType.Captured)
            {
                creditCardAccount = "4111111111111111";
            }
            if (resultType == TransactionStatusType.Failed)
            {
                bool ignoreCase = true;
                if (null != why)
                {
                    if (string.Compare(why, whyFail_Soft, ignoreCase) == 0)
                        creditCardAccount = "6011555555555553";	// Soft Fail
                    else if (string.Compare(why, whyFail_Hard, ignoreCase) == 0)
                        creditCardAccount = "4555555555555550";	// Hard Fail
                }
                if (null == creditCardAccount)
                {
                    // Luhn Check Fail Validation
                    creditCardAccount = "1111111111111111";
                }
            }
            return creditCardAccount;
        }
    }
}
