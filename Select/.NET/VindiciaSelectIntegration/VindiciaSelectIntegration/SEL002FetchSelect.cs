using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VindiciaSelectIntegration.com.vindicia.soap;

namespace VindiciaSelectIntegration
{
    class SEL002FetchSelect
    {
        private static DateTime next_start;

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

        public static string wrapValue(string value)
        {
            string left = "-[";
            string right = "]-";
            string wrappedValue = left + value + right;
            return wrappedValue;
        }
	
        public static void pause(int milliseconds)
        {
            //thread to sleep for the specified number of milliseconds
            System.Threading.Thread.Sleep(milliseconds);
        }

        public static String dateString(String name, DateTime date)
        {

            return (name + ": " + date);
        }

        public static int fetchResults(Select select, Authentication auth,
            DateTime start, DateTime end, int pageSize, int page, out string soapId)
        {
            int nResults = 0;

            select.Timeout = 1000;

            int returnCode;
            string returnString;
		
            log("start=" + start
					+ ", end=" + end
					+ ", pageSize=" + pageSize + ", page=" + page);

            // make the call to fetchBillingResults:
            log(timestamp() + " Beginning request to fetchBillingResults");
            Transaction[] transactions;
            Return _return = select.fetchBillingResults(auth, start, end, page, true, pageSize, true, out transactions);

            log(timestamp() + " Completed fetchBillingResults request: start="
                        + start
                        + ", end=" + end
                        + ", pageSize=" + pageSize + ", page=" + page);

            // Return.returnCode
            returnCode = _return.returnCode;
            // Return.returnString
            returnString = _return.returnString;
            // Return.soapId
            soapId = _return.soapId;

            log("Result=" + returnCode + ", " + returnString);
            log("soapId=" + soapId);

            nResults = reportResults(transactions, page);

            return nResults;
        }

        private static DateTime load_next_start()
        {
            log("load_next_start(): Update to read from database - next_start=:" + next_start);
            return next_start;      // replace this with call to database query
        }

        private static void save_next_start(DateTime start)
        {
            log("save_next_start(): Update to write to database - next_start=:" + start);
            next_start = start;     // replace this with call to database update
            return;
        }

        public static string[] actionFetchResults(Select select, Authentication auth,
                                    int num, int numStart, int numStartMin, int numEndMin)
        {
            log(timestamp() + " Beginning request to fetch billing results from the last "
                        + wrapValue("" + num) + " days, starting from " + wrapValue("" + numStart)
                        + " day(s) ago, " + wrapValue("" + numStartMin) + " minutes before current minute."
						+ " ending " + wrapValue("" + numEndMin) + " minutes before current minute.");

            Queue<string> soapIdQ = new Queue<string>();
            String soapId = null;

            // read next_start from database, set from last call
            DateTime start = load_next_start();

            if (default(DateTime) == start)     // if next_start not set in db, intialize here...
            {
                start = DateTime.Today.AddDays(-1);    // yesterday
                log("SEL002_FetchSelect: Initializing " + dateString("next_start", start) + "\n");
            }

            DateTime end = DateTime.Now;	// now

            start.AddMinutes(-numStartMin);
            start.AddDays(-numStart);
            start.AddDays(-num);

            if (0 == numEndMin) numEndMin = numStartMin;
            end.AddMinutes(-numEndMin);
            end.AddDays(-numStart);

		    int pageSize = 100;
		    int page = 0;
		
            bool bFail = true;
		    int numTimeouts = 0;
		    int nRecords = 0;
		    int nTotalRecords = 0;

			do {
				nTotalRecords += nRecords;
				do {
					try {
                        nRecords = fetchResults(select, auth, start, end, pageSize, page, out soapId);
                        soapIdQ.Enqueue(soapId);
						bFail = false;
					} catch (Exception e) {
						log(e);
						bFail = true;
				
						int msec = 900000;
                        log(timestamp() + " [" + numTimeouts++ + "]: Wait " + msec / 60000 + " minutes for initial query to finish: ");
						pause(msec);
					}
				} while ( bFail );
				page++;
			} while ( nRecords > 0 );
            log(timestamp() + " Completed request to fetch billing results from the last "
                        + wrapValue("" + num)
						+ " days, starting from " + wrapValue("" + numStart) + " day(s) ago, "
						+ wrapValue("" + numStartMin) + " minutes before current minute.");
			log("numTimeouts=" + numTimeouts);
			log("nTotalRecords=" + nTotalRecords);
			log("Number of pages=" + page);
			log("Page Size=" + pageSize);

            string[] soapIds = new string[soapIdQ.Count];
            soapIdQ.CopyTo(soapIds, 0);

            return soapIds;
		}
	
        public static int reportResults(Transaction[] results, int page)
        {
		    int nRecords = 0;
		    if (results != null)
            {	
			    Dictionary<TransactionStatusType, int> freq = new Dictionary<TransactionStatusType, int>();
			
			    nRecords = results.Length;
			    log("Retreived " + nRecords + ", page [" + page + "]:");
			    int n = 0;
                for (int i = 0; i < results.Length; i++)
                {
                    Transaction tx = results[i];
                    TransactionStatusType status = tx.status;
				    log("[" + page + ":" + n++ + "]: merchantTransactionId " + wrapValue(tx.merchantTransactionId)
					        + " created selectTransactionId " + wrapValue(tx.selectTransactionId)
					        + " with status " + wrapValue(tx.status.ToString())
					        + " , authCode " + wrapValue(tx.authCode)
					        + " on " + tx.timestamp
					        + " for " + tx.amount + " " + tx.currency
					);
				    NameValuePair[] nameValues = tx.nameValues;
				    for (int j=0; null != nameValues && j < nameValues.Length; j++) {
					    log( "\tnameValues[" + j + "]: " +
					    nameValues[j].name + " = " + nameValues[j].value);
				    }
				    int count = freq.ContainsKey(status) ? freq[status] : 0;
				    freq[status] = count + 1;
			    }

                // Use var keyword to enumerate dictionary.
                foreach (var m in freq)
                {
                    TransactionStatusType type = m.Key;
                    int cnt = m.Value;
                    log(wrapValue(type + ":  " + cnt));
                }
			
		    } else {
			    log("Nothing to fetch - the Transactions object is null.");
		    }
		    return nRecords;
	    }

        public static void run(Select select, Authentication auth)
        {
            try
            {
                DateTime startTime = DateTime.Now;

                log(timestamp() + " FetchSelect.run():");

                int num = 1;
                int numStart = 1;
                int numStartMin = 0;
                int numEndMin = 0;

                string[] soapIds =
                        actionFetchResults(select, auth, num, numStart, numStartMin, numEndMin);

                DateTime endTime = DateTime.Now;

                log("SEL002_FetchSelect.run: fetchBillingResults soapId's[]:\n");
                log("Start: " + startTime);
                log("End: " + endTime);

                for (int i = 0; i < soapIds.Length; i++)
                {
                    log("soapId[" + i + "]: " + soapIds[i] + "\n");
                }

            }
            catch (Exception e)
            {

                //System exception. 
                log(timestamp() + e);
                log(e.StackTrace);
            }
        }

    }
}
