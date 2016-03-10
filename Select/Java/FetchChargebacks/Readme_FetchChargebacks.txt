		FetchChargebacks Java sample
		----------------------------

Files:

	SEL004FetchChargebacks.java	- FetchChargebacks Java class file
	build.sh			- Compiles FetchChargebacks Java sample
	build.bat			- Compiles FetchChargebacks Java sample (Windows)
	run.sh				- Runs the FetchChargebacks Java sample
	run.bat				- Runs the FetchChargebacks Java sample (Windows)
	Readme_FetchChargebacks.txt	- This file
	bin\Environment.properties	- The configuration file from which soap credentials are read


Locations on GitHub
-------------------

   Binary version is on GitHub in Releases at:
   
	https://github.com/Vindicia/CashBoxAPISamples/releases/Select_Java_Samples_v1_1
   
   Source distribution is on GitHub at:
   
	https://github.com/Vindicia/CashBoxAPISamples/tree/master/Select/Java/FetchChargebacks


Installation
------------

0. Choose working directory, referenced as <Base Path> below.

1. Install Axis2 1.6.2 binary distribution to obtain Axis jar files from:

	http://axis.apache.org/axis2/java/core/download.cgi

   Extract axis2-1.6.2-bin.zip (or Axis2.zip) so it is populated in the directory:

	<Base Path>/Axis2/axis2-1.6.2

2. Obtain VindiciaSelect_v1_1.jar & place it in your bin directory:

   Binary version is on GitHub in Releases at:
   
		https://github.com/Vindicia/CashBoxAPISamples/releases/Select_v1_1
		https://github.com/Vindicia/CashBoxAPISamples/files/124097/Select_v1_1.zip
   
   Source distribution is on GitHub at:
   
		https://github.com/Vindicia/CashBoxAPISamples/tree/master/Select/Java/

3. Extract this sample to the following directory:

	<Base Path>

Resulting directory structure after extracting Axis2 1.6.2 binary & Select.zip:

	<Base Path>
		Axis2
			axis2-1.6.2
				lib
		Select
			BillSelect
			bin
			FetchChargebacks
			FetchSelect


4. Edit run.sh to reference the locations in 1. & 2. above:

	DIR=<Base Path> -- set to where Axis2 & Select subdirectories exist
	AXISDIR=<Base Path>/Axis2/axis2-1.6.2/lib
	BASEDIR=<Base Path>/Select



Running the FetchChargebacks sample
-----------------------------------

5. Run the FetchChargebacks sample:

(First associate Terminal.app to *.sh files if on the Mac,
 by right button->Get Info on the run.sh file.  Then select
 Opens with Terminal.app at the bottom)
 
 	Double click run.sh from Finder

Or execute run.sh from the command line:

	FetchChargebacks> ./run.sh



Changing the FetchChargebacks sample
------------------------------------

6. Edit build.sh to reference the locations in 1. & 2. above:

	DIR=<Base Path> -- set to where Axis2 & Select subdirectories exist
	AXISDIR=<Base Path>/Axis2/axis2-1.6.2/lib
	BASEDIR=<Base Path>/Select


7. Edit Environment.properties:

So it has your soap login & password, points to the correct CashBox environment (ProdTest or Staging):

	#
	soap_url = https://soap.prodtest.sj.vindicia.com/soap.pl
	#
	soap_login = xxx_soap
	soap_password = xxxxxx

8. Compile the FetchChargebacks sample:

 	Double click build.sh from Finder

Or execute build.sh from the command line:

	FetchChargebacks> ./build.sh


9. Run the changed FetchChargebacks sample as in 5. above:

	Double click run.sh from Finder

Or from the command line:

	FetchChargebacks> ./run.sh



