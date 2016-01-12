		BillSelect Java sample
		-----------------------

Files:

	SEL001BillSelect.java		    - BillSelect Java class file
	build.sh			              - Compiles BillSelect Java sample
	build.bat			              - Compiles BillSelect Java sample (Windows)
	run.sh				              - Runs the BillSelect Java sample
	run.bat				              - Runs the BillSelect Java sample (Windows)
	Readme_BillSelect.txt		    - This file
	bin\Environment.properties  - The configuration file from which soap credentials are read


Installation
------------

0. Choose working directory, referenced as <Base Path> below.

1. Install Axis2 1.6.2 binary distribution to obtain Axis jar files from:

	http://axis.apache.org/axis2/java/core/download.cgi

   Extract axis2-1.6.2-bin.zip (or Axis2.zip) so it is populated in the directory:

	<Base Path>/Axis2/axis2-1.6.2

2. Extract this sample to the following directory:

	<Base Path>

Resulting directory structure after extracting Axis2 1.6.2 binary & Select.zip:

	<Base Path>
		Axis2
			axis2-1.6.2
				lib
		Select
			BillSelect
			bin
			FetchSelect
			Select_v1_0


3. Edit run.sh to reference the locations in 1. & 2. above:

	DIR=<Base Path> -- set to where Axis2 & Select subdirectories exist
	AXISDIR=<Base Path>/Axis2/axis2-1.6.2/lib
	BASEDIR=<Base Path>/Select



Running the BillSelect sample
------------------------------

4. Run the BillSelect sample:

(First associate Terminal.app to *.sh files if on the Mac,
 by right button->Get Info on the run.sh file.  Then select
 Opens with Terminal.app at the bottom)
 
 	Double click run.sh from Finder

Or execute run.sh from the command line:

	BillSelect> ./run.sh



Changing the BillSelect sample
-------------------------------

5. Edit build.sh to reference the locations in 1. & 2. above:

	DIR=<Base Path> -- set to where Axis2 & Select subdirectories exist
	AXISDIR=<Base Path>/Axis2/axis2-1.6.2/lib
	BASEDIR=<Base Path>/Select


6. Edit Environment.properties:

So it has your soap login & password, points to the correct CashBox environment (ProdTest or Staging):

	#
	soap_url = https://soap.prodtest.sj.vindicia.com/v1.0/soap.pl
	#
	soap_login = xxx_soap
	soap_password = xxxxxx

7. Compile the BillSelect sample:

 	Double click build.sh from Finder

Or execute build.sh from the command line:

	BillSelect> ./build.sh


8. Run the changed FetchSelect sample as in 4. above:

	Double click run.sh from Finder

Or from the command line:

	BillSelect> ./run.sh

