
		Vindicia Retain PHP samples
		---------------------------

Files:

	SEL001BillSelect.php					- Bill/Submit PHP sample
	SEL002FetchSelect.php					- Fetch PHP sample
	SEL003CancelSubmittedSelectTransactions.php		- Cancel PHP sample
	SEL005RefundSelect.php					- Refund PHP sample
	
	Select.php		- Generated PHP library (Built for Retain v1.1)
	SelectUtil.php		- Generated PHP Utility library (Built for Retain v1.1)
	generate_PHP.sh		- Generates PHP library files Select.php, SelectUtil.php
	Readme_PHP.txt		- This file
	Environment.properties	- The configuration file from which soap credentials are read


Location on GitHub
-------------------
   
    https://github.com/Vindicia/CashBoxAPISamples/blob/master/Select/PHP


Installation Instructions:
--------------------------
If using Retain v1.1, which is the standard default version for Retain,
simply follow the steps below:


1) Copy the Select.php & SelectUtil.ph files into your PHP Environment
where the library references in the PHP samples will locate them from.

2) Run one or more of the samples as is.  You will get returnCode=403,
"Access to domain 'soap' denied" since your soap credentials have not
been provided to the samples.

3) Edit Enviroment.properties to set your soap credentials so the samples
will use them.

4) Run the samples as is. You should now get returnCode=200, "Ok".



Running on Retain v1.5 instead of the default v1.1:
--------------------------------------------------- 

If needed to generate the Select.php & SelectUtil.php PHP Library files
see the file generate_PHP.sh file, which is a shell script which contains
instructions to run it at the top:
 
	https://github.com/Vindicia/CashBoxAPISamples/blob/master/Select/PHP/generate_PHP.sh
 
In the file generate_PHP.sh: If generating for Retain v1.5 instead of v1.1,
for this step, it should reference the v1.5 WSDL, change to reference the v1.5
WSDL URL for 3. Instead of 1.1:
 
# 3. Generate PHP library using wsdl2php:
#
#		wsdl2php https://soap.vindicia.com/1.5/Select.wsdl



