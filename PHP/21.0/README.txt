README.txt

The cancelledAutoBills.php script is used for reporting on cancelled AutoBills for a 
given number of days.  The only assumption is that you have a computer that is able 
to run PHP scripts.  

To run this report, you need to have the Vindicia PHP library installed on your computer.  
And you need to modify the library to add the credentials for your CashBox sandbox. 

GET THE PHP LIBRARY 
The first three steps are all about getting the PHP library and then modifying it to 
use your CashBox credentials. 

Step 1 - Get the Vindicia passphrase from your Vindicia deployment consultant
+ the passphrase is request to download the PHP library

Step 2 - Download and Install the Vindicia PHP library.
+ The library can be found at this URL http://developer.vindicia.com/docs/client_library/
+ Download the latest PHP library file
+ Unzip the file into a directory on your computer - take note of where this is

Step 3 - Use a text editor open and modify the /vindicia/Soap/Const.php file 
+ where ever you see VIN_SOAP_TEST_USER enter your CashBox soap login 
+ where ever you see VIN_SOAP_TEST_PASSWORD enter your CashBox soap password 
+ also change  VIN_SOAP_CLIENT_USER and VIN_SOAP_CLIENT_PASSWORD  

EDIT THE SCRIPT FOR YOUR ENVIRONMENT 
The next steps are about modification of the cancelledAutoBill.php report to use your PHP 
library and CashBox credentials.  

Step 4 - Download and save the cancelledAutoBills.php file on your computer 
Step 5 - Use a text editor and open the cancelledAutoBills.php file
Step 6 - modify lines 10 and 11 with the directory path to your PHP libraries 

EXAMPLE: You make the change to the directory where your PHP libraries are located 
require_once("/MyComputer/Documents/Vindicia/Soap/Vindicia.php");
require_once("/MyComputer/Documents/Vindicia/Soap/Const.php");

RUN THE SCRIPT
Step 7 - From the command line, go to the directory where the cancelledAutoBills.php is 
Step 8 - type:  "php cancelledAutoBills.php" and hit enter
+ This will tell you how to run the script.  You should see: 

################
my-computer-cmd-line$ php cancelledAutoBills.php 

Wrong Number of Arguments Passed!

Usage: cancelledAutoBills.php -d YYYYMMDD -i I

Where 'YYYYMMDD' is the date you wish to start the search.
Where 'I' is an Integer representing the number of days after the start date to search.

Try Again
#################
Step 8: run the script with real parameters such as: 
my-computer-cmd-line$ php cancelledAutoBills.php -d 20180601 -i 65 

SAMPLE OUTPUT: 
Executing For: 2018-06-01 + 65 Days
Start: 2018-06-01T00:00:00.000Z
Stop: 2018-08-05T23:59:59.000Z
----------------------------------------------------------------------------------------------------------
merchantAccountId, merchantAutoBillId, startTimestamp, endTimestamp, emailAddress, cancelReasonCode, cancelReasonDesc
CS_1523456954, AB_1523457072, 2018-02-12T06:31:12-08:00, 2018-06-11T23:59:59-07:00, frank.nash@no-mail.com, <BLANK>, "<BLANK>"
CS_1523456618, AB_1523456638, 2018-02-12T06:23:58-08:00, 2018-06-11T23:59:59-07:00, charles.jackson@no-mail.com, <BLANK>, "<BLANK>"
CS_1523455706, AB_1523455816, 2018-02-12T06:10:16-08:00, 2018-06-11T23:59:59-07:00, vincent.livingston@no-mail.com, <BLANK>, "<BLANK>"
CS_1523454760, AB_1523454783, 2018-02-12T05:53:03-08:00, 2018-06-11T23:59:59-07:00, uve.kilmono@no-mail.com, <BLANK>, "<BLANK>"
CS_1523454573, AB_1523454630, 2018-02-12T05:50:30-08:00, 2018-06-11T23:59:59-07:00, erin.randolph@no-mail.com, <BLANK>, "<BLANK>"
CS_1523393261, AB_1523393283, 2018-03-25T13:48:03-07:00, 2018-06-24T23:59:59-07:00, angie.varnish@no-mail.com, <BLANK>, "<BLANK>"
CS_1523393162, AB_1523393196, 2018-03-25T13:46:36-07:00, 2018-06-24T23:59:59-07:00, bob.feinstein@no-mail.com, <BLANK>, "<BLANK>"
CS_1523390355, AB_1523390521, 2018-03-25T13:02:01-07:00, 2018-06-24T23:59:59-07:00, william.quasi@no-mail.com, <BLANK>, "<BLANK>"
CS_1523390795, AB_1523390825, 2018-03-25T13:07:05-07:00, 2018-06-24T23:59:59-07:00, xavier.quasi@no-mail.com, <BLANK>, "<BLANK>"
CS_1523386127, AB_1523386187, 2018-03-25T11:49:47-07:00, 2018-06-24T23:59:59-07:00, tanya.yankee@no-mail.com, <BLANK>, "<BLANK>"
CS_1523388451, AB_1523389886, 2018-03-25T12:51:26-07:00, 2018-06-24T23:59:59-07:00, erin.maxwell@no-mail.com, <BLANK>, "<BLANK>"
CS_1523392965, AB_1523393000, 2018-03-25T13:43:20-07:00, 2018-06-24T23:59:59-07:00, jonas.livingston@no-mail.com, <BLANK>, "<BLANK>"
AWS_1530570206, AB_1530570206_0, 2018-07-02T15:23:27-07:00, 2018-08-03T10:15:43-07:00, lmaxwell@vindicia.com, 105, "Merchant:  Unable/Unwilling to Pay (Cost too high)"
-----------------------------------------------------------------------------------------------------------
Finished Cancelled Subscription Search from 2018-06-01T00:00:00.000Z to 2018-08-05T23:59:59.000Z.
