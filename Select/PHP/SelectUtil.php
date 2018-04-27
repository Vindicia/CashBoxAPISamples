<?php
#
#	SelectUtil.php:
#
#		Utility module for SEL-0XX Use Cases
#
# ---------------------------------------------------------------
#
# This sample was run using wsdl2php from pear to generate PHP library:
#
# 1. Download & install pear if needed (already installed on the Mac):
#
#	https://pear.php.net/manual/en/installation.getting.php
#
# 2. Download & install wsdl2php-0.2.1-pear.tgz from Sourceforge:
#
#	https://sourceforge.net/projects/wsdl2php/
#
#		sudo pear install wsdl2php-0.2.1-pear.tgz
#
# 3. Generate PHP library using wsdl2php:
#
#		wsdl2php https://soap.vindicia.com/1.1/Select.wsdl
#
# 4. Cleanup generated code to work with Select (Return datatype is PHP keyword):
#
#	mv Select.php{,.orig}; sed 's/class Return /class VindiciaReturn /g' Select.php.orig > Select2.php
#	(or edit generated Select.php: change class Return to class VindiciaReturn).
#
#	(SelectUtil.php overrides 'Return' to 'VindiciaReturn' to match the change above)
#
#	Also fix up constructor to be compatible with PHP 7.0+:
#
#	sed 's/function Select(/function __construct(/g' Select2.php > Select.php
#
#
# 5. This sample may be found on GitHub at:
#
#	https://github.com/Vindicia/CashBoxAPISamples/tree/master/Select/PHP
#
#	Reference:
#
#		http://developer.vindicia.com/docs/soap/Select.html?ver=1.1
#
#

# ---------------------------------------------------------------
#
# This file should be included with the following lines
# in each PHP file implementing a SEL-0XX Use Case:
#
# // Change this to the location where you have installed Select.php:
# ini_set('include_path','/usr/local/Select_php5_lib_1.1');
# ini_set('display_errors',1);
# 
# require_once("Select.php");
# require_once("SelectUtil.php");
#

if (PHP_SAPI === 'cli')
{
   define( "EOL", PHP_EOL);
}
else
{
   define( "EOL", "<BR/>");
}

define("VIN_SOAP_CLIENT_VERSION", "1.1");
define("VIN_SOAP_TIMEOUT", "120");

function getOptions($endpoint) {

	$types = array(
                'Return' => 'VindiciaReturn'
            	);

	$options = array( "style"            => SOAP_DOCUMENT,
                    "use"                => SOAP_LITERAL,
                    "connection_timeout" => VIN_SOAP_TIMEOUT,
                    "location"           => $endpoint,
                    "trace"              => 1,
                    "classmap"           => $types,
                    "features"           => SOAP_SINGLE_ELEMENT_ARRAYS,
                    "ssltransport"       => "tlsv1.2"
                );

	return $options;
}

function getProperties($file) {

	$properties = file_get_contents('./'.$file.'.properties', false);
	
	return parse_properties($properties);
}

#
# https://mufumbo.wordpress.com/2009/08/05/reading-java-style-properties-file-in-php/
#

function parse_properties($txtProperties) {
	$result = array();

	$lines = explode("\n", $txtProperties);
	$key = "";

	$isWaitingOtherLine = false;
 	foreach($lines as $i=>$line) {

		if(empty($line) || (!$isWaitingOtherLine && strpos($line,"#") === 0)) continue;

 		if(!$isWaitingOtherLine) {
			$key = substr($line,0,strpos($line,'='));
 			$value = substr($line,strpos($line,'=') + 1, strlen($line));
 		}
 		else {
			$value .= $line;
		}

		/* Check if ends with single '\' */
 		if(strrpos($value,"\\") === strlen($value)-strlen("\\")) {
 			$value = substr($value, 0, strlen($value)-1)."\n";
 			$isWaitingOtherLine = true;
 		}
		else {
			$isWaitingOtherLine = false;
		}

		$result[trim($key)] = trim($value);	// added use of trim() SH
		unset($lines[$i]);
 	}

	return $result;
}

/** Prettifies an XML string into a human-readable and indented work of art
 *  @param string $xml The XML as a string
 *  @param boolean $html_output True if the output should be escaped (for use in HTML)
 */

function xmlpp($xml, $html_output=false) {
    $xml_obj = new SimpleXMLElement($xml);
    $level = 4;
    $indent = 0; // current indentation level
    $pretty = array();    

    // get an array containing each XML element
    $xml = explode("\n", preg_replace('/>\s*</', ">\n<", $xml_obj->asXML()));

    // shift off opening XML tag if present
    if (count($xml) && preg_match('/^<\?\s*xml/', $xml[0])) {
      $pretty[] = array_shift($xml);
    }

    foreach ($xml as $el) {

      if (preg_match('/^<([\w])+[^>\/]*>$/U', $el)) { 
          // opening tag, increase indent 
          $pretty[] = str_repeat(' ', $indent) . $el; 
          $indent += $level; 
      } else { 
        if (preg_match('/^<\/.+>$/', $el)) {            
          $indent -= $level;  // closing tag, decrease indent 
        } 
        if ($indent < 0) { 
          $indent += $level; 
        } 
        $pretty[] = str_repeat(' ', $indent) . $el; 
      } 
    }   

    $xml = implode("\n", $pretty);   

    return ($html_output) ? htmlentities($xml) : $xml; 
}


function getTransaction($merchantTransactionId, $customerId, $subscriptionId,
    $authCode, $creditCardAccount, $paymentMethodId, $creditCardExpirationDate,
    $amount, $currency, $timestamp, $divisionNumber, $billingFrequency,
    $paymentMethodIsTokenized) {

    # Reference:
    #
    #	http://developer.vindicia.com/docs/soap/AllDataTypes.html?pf=1&ver=1.1&type=Transaction
    #
    $transaction = new Transaction();

    $transaction->merchantTransactionId = $merchantTransactionId;	# Change this to your unique id of the Failed Transaction
    $transaction->customerId = $customerId;	# Change this to your unique id of the Customer's account
    $transaction->subscriptionId = $subscriptionId;	# Change this to your unique id of the Customer's subscription
    $transaction->authCode = $authCode;
    $transaction->avsCode = '';
    $transaction->cvnCode = '';
    $transaction->creditCardAccount = $creditCardAccount;	# When using Tokens, change to the BIN (1st 6 digits of Card)
    $transaction->amount = $amount;
    $transaction->paymentMethodId = $paymentMethodId;	# Change to your unique id of the Payment Method, or Token Id
    $transaction->billingAddressLine1 = '123 Main (Address Line 1)'	;	
    $transaction->billingAddressLine2 = 'Suite 5 (Address Line 2)';
    $transaction->billingAddressLine3 = 'Internet Widgets Co. Ltd. (Address Line 3)';
    $transaction->billingAddressCity = 'Any City';
    $transaction->billingAddressDistrict = 'Any State (i.e. District)';
    $transaction->billingAddressCountry = 'US';
    $transaction->billingAddressPostalCode = '94002';
    $transaction->timestamp = $timestamp;
    $transaction->creditCardExpirationDate = $creditCardExpirationDate;
    $transaction->divisionNumber = $divisionNumber;
    $transaction->status = 'Failed';
    $transaction->currency = $currency;
    $transaction->billingFrequency = $billingFrequency;
    $transaction->paymentMethodIsTokenized = $paymentMethodIsTokenized;	# Change to True when using Tokens
    
    return $transaction;
}

?>
