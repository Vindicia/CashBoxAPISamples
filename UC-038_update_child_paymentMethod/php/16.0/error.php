<?php

// Include the Vindicia library
ini_set('include_path','/Applications/MAMP/htdocs/16.0');

require_once("Vindicia/Soap/Vindicia.php");
require_once("Vindicia/Soap/Const.php");

// first, parse the POST parameters and create the Account object

$session_id = $_GET['session_id'];

print "error page session_id is: " . $session_id . "<br>";

$websession = new WebSession();
$response = $websession->fetchByVid($session_id);

print "Response code is: " .  $response['returnCode'] . "<br>";
$websession = $response['data']->session;

print "APIReturn ReturnCode is " . $websession->apiReturn->returnCode . "<br>";
print "APIReturn ReturnString is " . $websession->apiReturn->returnString . "<br>";
?>


<!--
<?
print_r($websession);
?>
-->
