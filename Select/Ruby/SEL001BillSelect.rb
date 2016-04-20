# This sample was run using ruby 2.3.0 with Savon 2 :
#   http://savonrb.com

require 'savon'

client = Savon.client({:wsdl => "https://soap.vindicia.com/1.1/Select.wsdl", :endpoint => "https://soap.prodtest.sj.vindicia.com"})

authentication = {}
authentication[:login] = 'xxx_soap'
authentication[:password] = 'xxxxxx'
authentication[:version] = '1.1'
authentication[:userAgent] = 'Ruby Select v1.1 Library'
authentication[:evid] = nil


# Savon operates by assuming the XML is standard and thus can usually be handles as simple Hashs.
#  There are methods to create complex XML, but they are not needed here

transaction = {}

transaction[:merchantTransactionId] = 123456   # Change this to your unique id of the Failed Transaction
transaction[:customerId] = 'C6654332' # Change this to your unique id of the Customer's account
transaction[:subscriptionId] = 'S123' # Change this to your unique id of the Customer's subscription
transaction[:authCode] = '302'
transaction[:avsCode] = ''
transaction[:cvnCode] = ''
transaction[:creditCardAccount] = '4111111111111111'   # When using Tokens, change to the BIN (1st 6 digits of Card)
transaction[:amount] = '9.99'
transaction[:paymentMethodId] = 'PAYMETHOD1234'   # Change to your unique id of the Payment Method, or Token Id
transaction[:billingAddressLine1] = '123 Main (Address Line 1)'
transaction[:billingAddressLine2] = 'Suite 5 (Address Line 2)'
transaction[:billingAddressLine3] = 'Internet Widgets Co. Ltd. (Address Line 3)'
transaction[:billingAddressCity] = 'Any City'
transaction[:billingAddressDistrict] = 'Any State (i.e. District)'
transaction[:billingAddressCountry] = 'US'
transaction[:billingAddressPostalCode] = '94002'
transaction[:timestamp] = '2016-02-05T10:41:39.000Z'
transaction[:creditCardExpirationDate] = '202212'  # format is YYYYMM 
transaction[:divisionNumber] = '5599'
transaction[:status] = 'Failed'
transaction[:currency] = 'USD'
transaction[:billingFrequency] = 'Monthly'
transaction[:paymentMethodIsTokenized] = 'False' # Change to True when using Tokens

#add two copies of this transaction for testing
transactions = []
transactions.push transaction
transactions.push transaction

message = {
    :auth => authentication,
    :transactions => transactions
}

response = client.call(:bill_transactions, message: message)

if response.http_error? then
    exit 'error connecting to soap endpoint'
elsif response.soap_fault? then
    exit 'error in soap connection'
end


#to get results you can just cast the body to a hash
result = response.body.to_hash
puts result

#
#this will output something similar to this
#{
#   :bill_transactions_respons => >{
#       :return => {
#           :return_code => "200", 
#           :soap_id => "0123456789abcde0123456789abcde0123456789", 
#           :return_string => "OK", 
#           :@xmlns => "", 
#           :"@xsi:type" => "vin:Return"
#       }, 
#       :@xmlns => "http://soap.vindicia.com/v1_1/Select"
#   }
#}
#
