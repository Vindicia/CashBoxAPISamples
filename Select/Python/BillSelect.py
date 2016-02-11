# This sample was run using python suds 0.4:
# 1. Download suds.
#
#	https://fedorahosted.org/releases/s/u/suds/python-suds-0.4.tar.gz
#
# 2. This sample may be found on GitHub at:
#
#	https://github.com/Vindicia/CashBoxAPISamples/tree/master/Select/Python/BillSelect.py
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

transaction = client.factory.create('vin:Transaction')

timestamp = '2016-02-05T10:41:39.000Z'	# Change to timestamp of last Failure of the Failed Transaction

transaction.merchantTransactionId = 'TEST1234'	# Change this to your unique id of the Failed Transaction
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

transaction.customerId = 'CUST1234'	# Change this to your unique id of the Customer's account
transaction.subscriptionId = 'SUBS1234'	# Change this to your unique id of the Customer's subscription
transaction.authCode = '302'
transaction.avsCode = ''
transaction.cvnCode = ''
transaction.creditCardAccount = '4111111111111111'	# When using Tokens, change to the BIN (1st 6 digits of Card)
transaction.amount = '10.99'
transaction.paymentMethodId = 'PAYMETHOD1234'	# Change to your unique id of the Payment Method, or Token Id
transaction.billingAddressLine1 = '123 Main (Address Line 1)'		
transaction.billingAddressLine2 = 'Suite 5 (Address Line 2)'
transaction.billingAddressLine3 = 'Internet Widgets Co. Ltd. (Address Line 3)'
transaction.billingAddressCity = 'Any City'
transaction.billingAddressDistrict = 'Any State (i.e. District)'
transaction.billingAddressCountry = 'US'
transaction.billingAddressPostalCode = '94002'
transaction.timestamp = timestamp
transaction.creditCardExpirationDate = '202208'
transaction.divisionNumber = '5698'
transaction.status = 'Failed'
transaction.currency = 'USD'
transaction.billingFrequency = 'Monthly'
transaction.paymentMethodIsTokenized = 'False'	# Change to True when using Tokens

print transaction

response = client.service.billTransactions(authentication, transaction)
print response
if response['return'].returnCode == '200':
    print 'Successfully submitted!'

