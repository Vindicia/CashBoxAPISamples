		Vindicia Select C# sample
		-------------------------

Files:

	SEL001BillSelect.cs		- BillSelect C# class file
	SEL002FetchSelect.cs		- FetchSelect C# class file
	Program.cs			- Main program that calls BillSelect & FetchSelect C# code
	PropFile.cs			- Utility class to support reading from the properties file
	VindiciaSelectIntegration.sln	- Compiles BillSelect/FetchSelect C# sample
	build.bat			- Generates VindiciaSelect dll
	Readme_SelectIntegration.txt	- This file
	Environment.properties		- The configuration file from which soap credentials are read


Installation
------------

The following steps are referenced in the included build.bat file:

0. Choose working directory & drive, (referenced as F: & DIR) below:

	F:
	set DIR=\dotNet\Select_v1.1


1. Generate C# source file from the Select v1.1 WSDL:

	wsdl /out:%DIR%\VindiciaSelect.cs /n:com.vindicia.soap https://soap.vindicia.com/1.1/Select.wsdl


2. Finally, Generate dll from the Select v1.1 C# source file

	csc /t:library /debug /out:%DIR%\VindiciaSelect.dll %DIR%\VindiciaSelect.cs


3. VindiciaSelect.dll is now found on the Selected drive at
   the working directory indicated in 0. above:

	F:
	cd %DIR%
	dir

	F:\dotNet\Select_v1.1>dir *.dll
 	Volume in drive F is VBOX_shared
 	Volume Serial Number is 0100-0004

 	Directory of F:\dotNet\Select_v1.1

	08/21/2015  08:32 PM            45,056 VindiciaSelect.dll
	               1 File(s)         45,056 bytes
	               0 Dir(s)  48,666,497,024 bytes free

	F:\dotNet\Select_v1.1>


4. Extract this zip file to a working directory, open VindiciaSelectIntegration.sln
   in The Visual C# IDE, then ensure that the Reference to VindiciaSelect properly
   locates the newly generated VindiciaSelect.dll just generated above:

	Under VindiciaSelectIntegration in the Solution Explorer pane, locate the
	References tree, and confirm VindicaSelect appears there as a Reference,
	or right-button->properties->Add Reference... on References to add it now.

	Perform right-button->properties on the VindiciaSelect entry, and confirm
	the Path Reference Property correctly references either the location above
 	where the VindiciaSelect.dll was generated, or the dll's current location.

	

Running the Select samples
--------------------------

5. Run the BillSelect & FetchSelect sample:

In Program.cs there are two variables that are currently enabled
for both BillSelect & FetchSelect, no change is needed unless you
want to turn one or both off:

            Boolean bBill = true;	// BillSelect sample enabled
            Boolean bFetch = true;	// FetchSelect sample enabled

If the sample is run as is without any modification, it will return
a returnCode=403, returnString=Permission denied to domain "soap" error.

This is because your soap credentials must be specified in the file
Environment.properties, invoked by Program.cs using class PropFile.cs,
as described below:



Changing the Vindicia Select C# samples
---------------------------------------

6. Note the following lines in Program.cs:

This is where your soap login & password is initialized, with the soap URL
which points to the correct CashBox environment (ProdTest or Staging):

	Select select = new Select();

	Authentication auth = new Authentication();
	auth.version = "1.1";	// Specify which Select API Version (1.0 or 1.1)

	PropertyFile rb = PropertyFile.getBundle("Environment");
	select.Url = rb.get("soap_url");
	auth.login = rb.get("soap_login");
	auth.password = rb.get("soap_password");

7. Edit the Environment.properties file to specify your soap credentials:

	# ProdTest
	soap_url = https://soap.prodtest.sj.vindicia.com/soap.pl
	#
	soap_login = xxx_soap
	soap_password = xxxxxx


8. Build the VindiciaSelectIntegration.sln Project.

