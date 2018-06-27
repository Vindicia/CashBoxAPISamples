from __future__ import print_function

import json
import boto3
from botocore.exceptions import ClientError
import logging
import os
import datetime
import hmacValidation as auth

logging.basicConfig(format='%(levelname)s:%(message)s', level=logging.INFO)
log = logging.getLogger()
log.setLevel(logging.INFO)
hmac_key = os.environ['hmac_key']

def lambda_handler(event, context):
    log.info('START_LAMBDA')
    log.info(event)
    print("Request ID:",context.aws_request_id)
    
    # Perform HMAC Validation
    webHook = event['headers']['X-Webhook-Signature']
    if not auth.Authoriser(webHook, hmac_key).validate(event['body']):
        log.info('HMAC Validation Error')
        log.warning('HMAC CHECK FAILED')
        raise Exception("403 - Not Authorised")
        return 403
    
    log.info('HMAC_VALIDATED')
        
    # Convert entire body to unicode to avoid string indice error 
    if isinstance(event['body'], (unicode, str)):
        event['body'] = json.loads(event['body'].decode('string-escape').strip('"'))
        log.info('UNICODE PAYLOAD: %s', event)
        log.info('HTTP HEADERS: %s', event['headers'])
    else: 
        log.warning('ERROR PROCESSING EVENT BODY')
    
    # Copy the HTTP headers into variables 
    httpHeaderContentType = event['headers']['Content-Type']
    httpHeaderVia = event['headers']['Via']
    httpHeaderProtocol = event['headers']['CloudFront-Forwarded-Proto']
    httpHeaderXForwardedFor = event['headers']['X-Forwarded-For']
    httpHeaderCloudFrontViewerCountry = event['headers']['CloudFront-Viewer-Country']
    httpHeaderUserAgent = event['headers']['User-Agent']
    httpHeaderXAmazonTraceId = event['headers']['X-Amzn-Trace-Id']
    httpHeaderHost = event['headers']['Host']
    httpHeaderXForwardedProtocol = event['headers']['X-Forwarded-Proto']
    httpHeaderXCloudFrontId = event['headers']['X-Amz-Cf-Id']
    httpHeaderXForwardedPort = event['headers']['X-Forwarded-Port']
    httpHeaderCloudFrontIsDesktopViewer = event['headers']['CloudFront-Is-Desktop-Viewer']
    httpHeaderCloudFrontIsSmartTVViewer = event['headers']['CloudFront-Is-SmartTV-Viewer']
    httpHeaderCloudFrontIsTabletViewer = event['headers']['CloudFront-Is-Tablet-Viewer']
    httpHeaderCloudFrontIsMobileViewer = event['headers']['CloudFront-Is-Mobile-Viewer']
    HTTPS_HTML = """
    <table align="left" border="2">
            <tr>
               <th align="left">HTTPS HEADER</th>
               <th align="left">VALUE</th>
            </tr>
            <tr>
               <td>Content Type</td>
               <td>{}</td>
            </tr>
            <tr>
               <td>Via</td>
               <td>{}</td>
            </tr>  
            <tr>
               <td>Protocol</td>
               <td>{}</td>
            </tr>  
            <tr>
               <td>X-Forwarded-For</td>
               <td>{}</td>
            </tr>    
            <tr>
               <td>Cloud Front Viewer Country</td>
               <td>{}</td>
            </tr>     
            <tr>
               <td>User Agent</td>
               <td>{}</td>
            </tr>    
            <tr>
               <td>X Amazon Trace Id</td>
               <td>{}</td>
            </tr>         
            <tr>
               <td>Host</td>
               <td>{}</td>
            </tr>      
            <tr>
               <td>X-Forwarded-Protocol</td>
               <td>{}</td>
            </tr>          
            <tr>
               <td>X-Cloud-Front-Id</td>
               <td>{}</td>
            </tr>    
            <tr>
               <td>X-Forwarded-Port</td>
               <td>{}</td>
            </tr>   
            <tr>
               <td>CloudFront-Is-Desktop-Viewer</td>
               <td>{}</td>
            </tr>              
            <tr>
               <td>CloudFront-Is-SmartTV-Viewer</td>
               <td>{}</td>
            </tr>  
            <tr>
               <td>CloudFront-Is-Tablet-Viewer</td>
               <td>{}</td>
            </tr>              
            <tr>
               <td>CloudFront-Is-Mobile-Viewer</td>
               <td>{}</td>
            </tr>              
    </table>
            """.format(httpHeaderContentType,
                       httpHeaderVia,
                       httpHeaderProtocol,
                       httpHeaderXForwardedFor,
                       httpHeaderCloudFrontViewerCountry,
                       httpHeaderUserAgent,
                       httpHeaderXAmazonTraceId,
                       httpHeaderHost,
                       httpHeaderXForwardedProtocol,
                       httpHeaderXCloudFrontId,
                       httpHeaderXForwardedPort,
                       httpHeaderCloudFrontIsDesktopViewer,
                       httpHeaderCloudFrontIsSmartTVViewer,
                       httpHeaderCloudFrontIsTabletViewer,
                       httpHeaderCloudFrontIsMobileViewer)
                       
            
    
    # Copy the account object attributes into variables     
    merchantAccountId = event['body']['content']['merchantAccountId']
    merchantAccountVID = event['body']['content']['VID']
    emailAddress = event['body']['content']['emailAddress']
    emailTypePreference = event['body']['content']['emailTypePreference']
    name = event['body']['content']['name']
    defaultCurrency = event['body']['content']['defaultCurrency']
    preferredLanguage = event['body']['content']['preferredLanguage']
    shippingAddressVID = event['body']['content']['shippingAddress']['VID']
    shippingAddressName = event['body']['content']['shippingAddress']['name']
    shippingAddressAddr1 = event['body']['content']['shippingAddress']['addr1']
    shippingAddressCity = event['body']['content']['shippingAddress']['city']
    shippingAddressDistrict = event['body']['content']['shippingAddress']['district']
    shippingAddressCountry =  event['body']['content']['shippingAddress']['country']
    shippingAddressPostalCode = event['body']['content']['shippingAddress']['postalCode']
    
    # Need to support an array of payment methods on the account as well as the account 
    # having no payment method defined 
    PM_HTML = ""  # start with an empty HTML buffer 
    
    # Check to see if the payment method object exists in the dictionary 
    # if it does process 1-N entries, if it does not move on... 
    if 'paymentMethods' in event['body']['content']:
       numberOfPaymentMethods = len(event['body']['content']['paymentMethods']) 
       x = 0
       while x < numberOfPaymentMethods: 
          paymentMethodId = event['body']['content']['paymentMethods'][x]['merchantPaymentMethodId']
          sortOrder = event['body']['content']['paymentMethods'][x]['sortOrder']
          active = event['body']['content']['paymentMethods'][x]['active']
          customerDescription = event['body']['content']['paymentMethods'][x]['customerDescription']
          paymentMethodType = event['body']['content']['paymentMethods'][x]['type']
          accountHolderName = event['body']['content']['paymentMethods'][x]['accountHolderName']
          paymentMethodVID = event['body']['content']['paymentMethods'][x]['VID']
          billingAddressCity = event['body']['content']['paymentMethods'][x]['billingAddress']['city']
          billingAddressName = event['body']['content']['paymentMethods'][x]['billingAddress']['name']
          billingAddressVID = event['body']['content']['paymentMethods'][x]['billingAddress']['VID']
          billingAddressCountry = event['body']['content']['paymentMethods'][x]['billingAddress']['country']
          billingAddressDistrict = event['body']['content']['paymentMethods'][x]['billingAddress']['district']
          billingAddressAddr1 = event['body']['content']['paymentMethods'][x]['billingAddress']['addr1']
          billingAddressPostalCode = event['body']['content']['paymentMethods'][x]['billingAddress']['postalCode']
          creditCardBin = event['body']['content']['paymentMethods'][x]['creditCard']['bin']
          creditCardAccount = event['body']['content']['paymentMethods'][x]['creditCard']['account']
          creditCardVID = event['body']['content']['paymentMethods'][x]['creditCard']['VID']
          creditCardLastDigits = event['body']['content']['paymentMethods'][x]['creditCard']['lastDigits']
          creditCardExpirationDate = event['body']['content']['paymentMethods'][x]['creditCard']['expirationDate']
          creditCardAccountLength = event['body']['content']['paymentMethods'][x]['creditCard']['accountLength']
          print ("Payment Method Id {}".format(paymentMethodId))
          PM_HTML += """
            <tr>
               <th align="left">Payment Method Object [{}]</th>
               <th align="left">VID: {}</th>
            </tr>         
            <tr>
               <td>Payment Method Id</td>  
               <td>{}</td>          
            </tr>  
            <tr>
               <td>Sort Order</td>  
               <td>{}</td>          
            </tr>  
            <tr>
               <td>Active</td>  
               <td>{}</td>          
            </tr>   
            <tr>
               <td>Customer Desciption</td>  
               <td>{}</td>          
            </tr>       
            <tr>
               <td>Payment Method Type</td>  
               <td>{}</td>          
            </tr>
            <tr>
               <td>Account Holder Name</td>  
               <td>{}</td>          
            </tr>
            <tr>
               <th align="left">Billing Address Object</th>
               <th align="left">VID: {}</th>
            </tr>               
            <tr>
               <td>Billing Address</td>  
               <td>
                   {}<br>
                   {}<br>
                   {}, {} {} {}<br>
               </td>          
            </tr>     
            <tr>
               <th align="left">Credit Card Object</th>
               <th align="left">VID: {}</th>
            </tr>                
            <tr>
               <td>Account</td>  
               <td>{}</td>          
            </tr>     
            <tr>
               <td>BIN</td>  
               <td>{}</td>          
            </tr>          
            <tr>
               <td>Last Digits</td>  
               <td>{}</td>          
            </tr>   
            <tr>
               <td>Account Length</td>  
               <td>{}</td>          
            </tr>           
            <tr>
               <td>Expiration Date</td>  
               <td>{}</td>          
            </tr>""".format(x, paymentMethodVID,
                       paymentMethodId, 
                       sortOrder, 
                       active, 
                       customerDescription, 
                       paymentMethodType, 
                       accountHolderName,
                       billingAddressVID,
                       billingAddressName,
                       billingAddressAddr1,
                       billingAddressCity,
                       billingAddressDistrict,
                       billingAddressPostalCode,
                       billingAddressCountry,
                       creditCardVID, creditCardAccount, creditCardBin, creditCardLastDigits, creditCardAccountLength, creditCardExpirationDate)
          x += 1       
    else:
       print("No Payment Method Exists")

    
    # Replace sender@example.com with your "From" address.
    # This address must be verified with Amazon SES.
    SENDER = "Liam Maxwell <liam.hall.maxwell@gmail.com>"
    
    # Replace recipient@example.com with a "To" address. If your account 
    # is still in the sandbox, this address must be verified.
    RECIPIENT = emailAddress
    
    # If necessary, replace us-west-2 with the AWS Region you're using for Amazon SES.
    AWS_REGION = "us-east-1"
    
    # The subject line for the email.
    SUBJECT = "Welcome {} to Speed of Sound Software!".format(name)
    
    # The email body for recipients with non-HTML email clients.
    BODY_TEXT = ("Amazon SES Test (Python)\r\n"
                 "This email was sent with Amazon SES using the "
                 "AWS SDK for Python (Boto)."
                )
            
    # The HTML body of the email.
    BODY_HTML = """<html>
   <head>
   <title>Account Create Template</title>
</head>
    <body>
      <h1>Welcome {} to Speed of Sound Software</h1>
      <p>This template shows all the attributes of the Account.Create Push Notification</p>
      <p>Currently this template only supports Credit Card payment methods</p>
      {}
      <br>
      <br>
      <br>
         <table align="left" border="2">
            <tr>
               <th align="left">Account Object</th>
               <th align="left">VID: {}</th>
            </tr>
            <tr>
               <td>Customer Name</td>
               <td>{}</td>
            </tr>
            <tr>
               <td>Merchant Account Id</td>
               <td>{}</td>
            </tr>   
            <tr>
               <td>Email Address</td>
               <td>{}</td>
            </tr>   
            <tr>
               <td>Email Type Preference</td>
               <td>{}</td>
            </tr>   
            <tr>
               <td>Preferred Language</td>
               <td>{}</td>
            </tr>   
            <tr>
               <td>Default Currency</td>
               <td>{}</td>
            </tr>   
            <tr>
               <th align="left">Shipping Address Object</th>
               <th align="left">VID: {}</th>
            </tr>               
            <tr>
               <td>Shipping Address</td>  
               <td>
                   {}<br>
                   {}<br>
                   {}, {} {} {}<br>
               </td>          
            </tr> 
            {}
       </table>
   </body>
</html>
                """.format(name,HTTPS_HTML, 
                           merchantAccountVID,
                           name, 
                           merchantAccountId, 
                           emailAddress, 
                           emailTypePreference, 
                           preferredLanguage, 
                           defaultCurrency,
                           shippingAddressVID,
                           shippingAddressName, 
                           shippingAddressAddr1, 
                           shippingAddressCity, 
                           shippingAddressDistrict, 
                           shippingAddressPostalCode, 
                           shippingAddressCountry,
                           PM_HTML)

    # The character encoding for the email.
    CHARSET = "UTF-8"
    
    # Create a new SES resource and specify a region.
    client = boto3.client('ses',region_name=AWS_REGION)
    
    # Try to send the email.
    try:
        #Provide the contents of the email.
        response = client.send_email(
            Destination={
                'ToAddresses': [
                    RECIPIENT,
                ],
            },
            Message={
                'Body': {
                    'Html': {
                        'Charset': CHARSET,
                        'Data': BODY_HTML,
                     },
                    'Text': {
                        'Charset': CHARSET,
                        'Data': BODY_TEXT,
                    },
                },
                'Subject': {
                    'Charset': CHARSET,
                    'Data': SUBJECT,
                },
            },
            Source=SENDER,

        )
    # Display an error if something goes wrong.	
    except ClientError as e:
        print(e.response['Error']['Message'])
    else:
        print("Email sent! Message ID:{}".format(response['MessageId']))
        log.info('STOP_LAMBDA')
        return {'disposition': 'stop_rule_set'}
