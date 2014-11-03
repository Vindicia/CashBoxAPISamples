<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$accountID = $argv[1];

$account  = new Account();
$response = $account->fetchByMerchantAccountId($accountID);
print_r($response);
$return_code   = $response['returnCode'];
$return_string = $response['returnString'];
if ($return_code != '200') {
    print "Failed to fetch $accountID\n";
    print "ReturnCode: $return_code\n";
    print "ReturnString : $return_string\n";
} else {
    //print_r ($response);
    $return_data  = $response['data'];
    $return_ID    = $return_data->account->merchantAccountId;
    $return_email = $return_data->account->emailAddress;
    
    print "email address for account $return_ID" . ": " . "$return_email \n";
}

?>
