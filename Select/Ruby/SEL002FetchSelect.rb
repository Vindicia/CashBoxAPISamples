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



ts_start = '2016-04-11T00:00:00.000Z'  # Change to timestamp of last successful execution 
ts_end = '2016-04-12T00:00:00.000Z'    # Change to timestamp of now (or prior midnight)
pageSize = 100




message = {
    :auth => authentication,
    :timestamp => ts_start,
    :endTimestamp => ts_end,
    :pageSize => 100,
    :page => 0
}

response = client.call(:fetch_billing_results, message: message)

if response.http_error? then
    exit 'error connecting to soap endpoint'
elsif response.soap_fault? then
    exit 'error in soap connection'
end


result = response.body.to_hash

puts result

#
#this will output something similar to this
#{
#   :fetch_billing_results_response => {
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
