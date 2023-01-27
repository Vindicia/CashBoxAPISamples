# This sample was run using ruby 2.3.0 with Savon 2 :
#   http://savonrb.com

require 'savon'

def process_transaction(transaction)
    #todo: do something here
end

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
page = 0
total_count = 0

#loop through all pages of results
begin

    message = {
        :auth => authentication,
        :timestamp => ts_start,
        :endTimestamp => ts_end,
        :page => page,
        :pageSize => pageSize
    }

    response = client.call(:fetch_billing_results, message: message)

    if response.http_error? then
        exit 'error connecting to soap endpoint'
    elsif response.soap_fault? then
        exit 'error in soap connection'
    end

    result = response.body.to_hash

    return_obj = result[:fetch_billing_results_response][:return]
    transactions_obj = result[:fetch_billing_results_response][:transactions]

    if return_obj[:return_code] != '200' then
        puts "Error: return code #{return_obj[:return_code]}"
        exit 
    end

    puts " soap_id = '#{return_obj[:soap_id]}'"

    result_count = 0
    unless transactions_obj.nil? then

        if transactions_obj.class == Array then 
            transactions_obj.each do |transaction|
                process_transaction( transaction)
            end
            result_count = transactions_obj.length

        elsif transactions_obj.class == Hash then  
            process_transaction(transactions_obj)
            result_count = 1
        end
    end

    page = page + 1
    total_count += result_count

end while result_count > 0

puts "#{total_count} transactions processed."
