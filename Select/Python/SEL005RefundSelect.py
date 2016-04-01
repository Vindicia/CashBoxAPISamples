#
#	SEL005RefundSelect.py:
#
#		SEL-005	Refund Select Transactions
#
# ---------------------------------------------------------------
#
# This sample was run using python suds 0.4:
# 1. Download suds.
#
#	https://fedorahosted.org/releases/s/u/suds/python-suds-0.4.tar.gz
#
# 2. This sample may be found on GitHub at:
#
#	https://github.com/Vindicia/CashBoxAPISamples/tree/master/Select/Python
#
#
#	Reference:
#
#		http://developer.vindicia.com/docs/soap/Select.html?ver=1.1 (refundTransactions)
#
#
from suds.client import Client
client = Client('https://soap.vindicia.com/1.1/Select.wsdl')
#client.set_options(location='https://soap.prodtest.sj.vindicia.com/soap.pl')

authentication = client.factory.create('vin:Authentication')
authentication.version = '1.1'
authentication.userAgent = 'Vindicia Python Select v1.1 Library'
authentication.evid = None

env = dict(line.strip().replace(' ', '').split('=') for line in open('Environment.properties')
	if not line.startswith('#') and not line.startswith('\n'))

#print env

login = env['soap_login']
password = env['soap_password']
endpoint = env['soap_url']

authentication.login = login
authentication.password = password
client.set_options(location=endpoint)

# uncomment to trigger timeout:
#client.set_options(timeout=0.1)

print
print '\tsoap login = ' + authentication.login
print '\tsoap version = ' + authentication.version
print '\tuserAgent = ' + authentication.userAgent
print


merchantTransactionId = 'TEST1234'	# Change this to your unique id of the Transaction

# Select will only process a Failed Transaction once per unique id specified in merchantTransactionId
#
# If a Failed Transaction is specified with a value for merchantTransactionId previously submitted
# in a call to billTransactions, the response while returning a top level 200 returnCode, will also
# include a TransactionValidationResponse element with code 400 as shown below:
#(reply){
#   return = 
#      (Return){
#         returnCode = "200"
#         soapId = "c20228a068991106f4dd365581a60f36b6c77582"
#         returnString = "OK"
#      }
# }

#
# These Transactions have already been recovered by Select & returned as
# Captured status from fetchBillingResults previously.
#
# Customer already paid, Duplicate Transactions...request Refunds in Select
#
#	Submit these two Transactions in call to refundTransactions to perform Refunds:
#
print
print 'Requesting Refunds to be performed in Select on previously recovered'
print '(i.e. Captured) Failed Transactions...'
print

transactions=[ merchantTransactionId, merchantTransactionId+'_1' ]

print transactions

print
print 'Calling refundTransactions...'
print

response = client.service.refundTransactions(authentication, transactions)
print response
if response['return'].returnCode == '200':
    print 'Successfully submitted!'

