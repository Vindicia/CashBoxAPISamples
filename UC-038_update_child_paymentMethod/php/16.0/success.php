<?php

// Include the Vindicia library

ini_set('include_path','/Applications/MAMP/htdocs/16.0');
require_once("Vindicia/Soap/Vindicia.php");
require_once("Vindicia/Soap/Const.php");

// first, parse the POST parameters and create the Account object

$session_id = $_GET['session_id'];

$websession = new WebSession();
$response = $websession->fetchByVid('',$session_id);
print_r($response);
$response_object = $response['data'];

$return_code = $response['returnCode'];
$websession = $response_object->session;

if (($return_code=="200") && ($websession->apiReturn->returnCode == "200"))
{ // then all is good
	

	$response = $websession->finalize();
	//print the entire response for debugging if needed
        //print "Printing finalize response <br />";
	//print_r ($response);
	print "<br />";
	if( ($response['returnCode'] == '200') && ($response['data']->session->apiReturn->returnCode == "200"))
	{
		
	
		print  "Finalize successful." . "<br />";
		print "SOAP ID: " . $response['data']->return->soapId . "<br />";
		print "vinAVS: " . $response['data']->session->apiReturnValues->paymentMethodUpdate->authStatus->vinAVS . "<br />";
		print "authCode: " . $response['data']->session->apiReturnValues->paymentMethodUpdate->authStatus->creditCardStatus->authCode . "<br />";
		print "cvnCode: " . $response['data']->session->apiReturnValues->paymentMethodUpdate->authStatus->creditCardStatus->cvnCode . "<br />";

	}else
	{
		print  "Unable to finalize the websession" . "<br />";
		print "returnCode: " . $response['returnCode'] . "<br />";
		print "returnString: " . $response['returnString'] . "<br />";
		//var_dump($response);
		print  "<br />";
	}
	
}

?>
