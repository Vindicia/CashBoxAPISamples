#
#	SEL003CancelSubmittedSelectTransactions.py:
#
#		SEL-003	Cancel Submitted Select Transactions
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


timestamp = '2016-02-05T10:41:39-07:00'	# Change to timestamp of last Failure of the Failed Transaction

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
#         soapId = "c20228a068991106f4dd365581a60f36b6c77582"
#         returnString = "OK"
#      }
# }

def getTransaction(merchantTransactionId, customerId, subscriptionId,
    authCode, creditCardAccount, paymentMethodId, creditCardExpirationDate,
    amount, currency, timestamp, divisionNumber, billingFrequency,
    paymentMethodIsTokenized):

    # Reference:
    #
    #	http://developer.vindicia.com/docs/soap/AllDataTypes.html?pf=1&ver=1.1&type=Transaction
    #
    transaction = client.factory.create('vin:Transaction')

    transaction.merchantTransactionId = merchantTransactionId	# Change this to your unique id of the Failed Transaction
    transaction.customerId = customerId	# Change this to your unique id of the Customer's account
    transaction.subscriptionId = subscriptionId	# Change this to your unique id of the Customer's subscription
    transaction.authCode = authCode
    transaction.avsCode = ''
    transaction.cvnCode = ''
    transaction.creditCardAccount = creditCardAccount	# When using Tokens, change to the BIN (1st 6 digits of Card)
    transaction.amount = amount
    transaction.paymentMethodId = paymentMethodId	# Change to your unique id of the Payment Method, or Token Id
    transaction.billingAddressLine1 = '123 Main (Address Line 1)'		
    transaction.billingAddressLine2 = 'Suite 5 (Address Line 2)'
    transaction.billingAddressLine3 = 'Internet Widgets Co. Ltd. (Address Line 3)'
    transaction.billingAddressCity = 'Any City'
    transaction.billingAddressDistrict = 'Any State (i.e. District)'
    transaction.billingAddressCountry = 'US'
    transaction.billingAddressPostalCode = '94002'
    transaction.timestamp = timestamp
    transaction.creditCardExpirationDate = creditCardExpirationDate
    transaction.divisionNumber = divisionNumber
    transaction.status = 'Failed'
    transaction.currency = currency
    transaction.billingFrequency = billingFrequency
    transaction.paymentMethodIsTokenized = paymentMethodIsTokenized	# Change to True when using Tokens
    
    return transaction

customerId = 'CUST1234'	# Change this to your unique id of the Customer's account
subscriptionId = 'SUBS1234'	# Change this to your unique id of the Customer's subscription
authCode = '302'
creditCardAccount = '4111111111111111'	# When using Tokens, change to the BIN (1st 6 digits of Card)
amount = '10.99'
paymentMethodId = 'PAYMETHOD1234'	# Change to your unique id of the Payment Method, or Token Id
creditCardExpirationDate = '202208'
divisionNumber = '5698'
currency = 'USD'
billingFrequency = 'Monthly'
paymentMethodIsTokenized = 'False'	# Change to True when using Tokens

n = 2	# number of transactions to process

#
# First submit Transactions to Select via call to billTransactions:
#
print 'Submitting Failed Transactions to Select...'
print

transactions=[]
for i in range(n):
	trx = getTransaction(merchantTransactionId+('' if i==0 else '_'+str(i)),
		customerId, subscriptionId,
		authCode, creditCardAccount, paymentMethodId, creditCardExpirationDate,
		amount, currency, timestamp, divisionNumber, billingFrequency,
		paymentMethodIsTokenized)
	#print trx
  	transactions.append(trx)

print transactions

print
print 'Calling billTransactions...'
print

response = client.service.billTransactions(authentication, transactions)
print response
if response['return'].returnCode == '200':
    print 'Successfully submitted!'


#
# Oops: should not have submitted those to Select...ask to pull them back
#
#	Submit those two Transactions in call to refundTransactions to cancel processing:
#
print
print 'Cancelling Submission of Failed Transactions to Select...'
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

