# This sample was run using python suds 0.4:
# 1. Download suds.
#
#	https://fedorahosted.org/releases/s/u/suds/python-suds-0.4.tar.gz
#
# 2. This sample may be found on GitHub at:
#
#	https://github.com/Vindicia/CashBoxAPISamples/tree/master/Select/Python/SEL002FetchSelect.py
#
#
#	Reference:
#
#		http://developer.vindicia.com/docs/soap/Select.html?ver=1.1 (fetchBillingResults)
#
#
from suds.client import Client
client = Client('https://soap.vindicia.com/1.1/Select.wsdl')
client.set_options(location='https://soap.prodtest.sj.vindicia.com/soap.pl')

authentication = client.factory.create('vin:Authentication')
authentication.login = 'xxx_soap'
authentication.password = 'xxxxxx'
authentication.version = '1.1'
authentication.userAgent = 'Vindicia Python Select v1.1 Library'
authentication.evid = None

print
print '\tsoap login = ' + authentication.login
print '\tsoap version = ' + authentication.version
print '\tuserAgent = ' + authentication.userAgent
print


start = '2016-02-05T10:41:39.000Z'	# Change to timestamp of last successful execution 
end = '2016-03-02T10:41:39.000Z'	# Change to timestamp of now (or prior midnight)
pageSize = 100

merchantTransactionId = 'TEST1234'	# Change this to your unique id of the Failed Transaction
# Select will only process a Failed Transaction once per unique id specified in merchantTransactionId
#
# If a Failed Transaction is specified with a value for merchantTransactionId previously submitted
# in a call to billTransactions, the response while returning a top level 200 returnCode, will also
# include a TransactionValidationResponse element with code 400 as shown below:
#(reply){
#   return = 
#      (Return){
#         returnCode = "200"
#         soapId = "d71eee02e9398f0172e37e9bf845084c31f5ad91"
#         returnString = "OK"
#      }
#   response[] = 
#      (TransactionValidationResponse){
#         merchantTransactionId = "TEST1234"
#         code = 400
#         description = "Billing has already been attempted for Transaction ID TEST1234"
#      },
# }

def log(message):

    print message

def wrapValue(value):

    left = "-["
    right = "]-"
    wrappedValue = left + str(value) + right
    return wrappedValue
	
def fetchResults(start, end, pageSize, page):

    nResults = 0
		
    log("start=" + start + ", end=" + end
			+ ", pageSize=" + str(pageSize) + ", page=" + str(page) )
				
    log("------------------------------------------------------------------------\n" +
		"[Page " + str(page) + "]: Fetching " + wrapValue(pageSize) + " Billing Results")

    #	Reference:
    #
    #		http://developer.vindicia.com/docs/soap/Select.html?ver=1.1 (fetchBillingResults)
    #
    response = client.service.fetchBillingResults(authentication, start, end, page, pageSize)
    print response
    if response['return'].returnCode == '200':
		print 'Successfully submitted!'

    log("Completed fetchBillingResults request[" + str(page) + "]:\n\tstart="
					+ start
					+ ", end=" + end
					+ ",\n\tpageSize=" + str(pageSize) + ", page=" + str(page) + "\n")
    ftxsReturn = response['return']
    log("\n\tResult=" + ftxsReturn.returnCode + ", " + ftxsReturn.returnString +
    "\n\tsoapId=" + ftxsReturn.soapId + "\n")
    nResults = reportResults(response['transactions'], page)
		
    return nResults


def reportResults(results, page):

    nRecords = 0;
    if (results != None):
			
    	nRecords = len(results)
    	log("Retreived " + str(nRecords) + ", page [" + str(page) + "]:")
    	n = 0

    	# Reference:
    	#
    	#	http://developer.vindicia.com/docs/soap/AllDataTypes.html?pf=1&ver=1.1&type=Transaction
    	#
    	for tx in results:
    	    status = tx.status
    	    log("[" + str(page) + ":" + str(n) + "]: merchantTransactionId " + wrapValue(tx.merchantTransactionId)
					+ " created selectTransactionId " + wrapValue(tx.selectTransactionId)
					+ " with status " + wrapValue(status)
					+ " , authCode " + wrapValue(tx.authCode)
					+ " on " + str(tx.timestamp)
					+ " for " + str(tx.amount)
					+ " " + tx.currency
					)
    	    n += 1

    	    # Reference:
    	    #
    	    #	http://developer.vindicia.com/docs/soap/AllDataTypes.html?pf=1&pop=yes&ver=1.1&type=NameValuePair
    	    #
    	    nameValues = tx.nameValues
    	    for (i, nvp) in enumerate(nameValues):
    	    	log( "\tnameValues[" + str(i) + "]: " +
    	    		nvp.name+ " = " + nvp.value )
    	    log("")

    else:
    	log("Nothing to fetch - the Transactions object is null.\n")

    return nRecords


fetchResults(start, end, pageSize, 0)

