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
        
    merchantAccountId = event['body']['content']['merchantAccountId']
    emailAddress = event['body']['content']['emailAddress']
    emailTypePreference = event['body']['content']['emailTypePreference']
    name = event['body']['content']['name']
    log.info('Email Address: %s', emailAddress)
    log.info('Email Preference: %s', emailTypePreference)
    log.info('Account Id: %s', merchantAccountId)
    log.info('Name is: %s', name)
    
    senderEmail = "yourAddress@yourDomain.com"
    
    # Replace sender@example.com with your "From" address.
    # This address must be verified with Amazon SES.
    SENDER = senderEmail
    
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
    <head></head>
    <body>
      <h1>Welcome to Speed of Sound Software</h1>
      <p> This email was sent via CashBox and AWS Simple Email Service 
        <a href='https://aws.amazon.com/ses/'>Amazon SES</a> using the
        <a href='https://aws.amazon.com/sdk-for-python/'>
          AWS SDK for Python (Boto)</a>.</p>
    </body>
    </html>
                """            

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
        print("Email sent! Message ID:"),
        print(response['MessageId'])
        log.info('STOP_LAMBDA')
        return {'disposition': 'stop_rule_set'}
