<?php

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$accountEmail = $argv[1];
print "Account to fetch: $accountEmail \n";

$account       = new Account();
$response      = $account->fetchByEmail($accountEmail);
$return_code   = $response['returnCode'];
$return_string = $response['returnString'];
// We could print the entire response for debugging... 
//print_r ($response);
$return_data   = $response['data'];
if ($return_code == '200') {
    $return_accounts = $return_data->accounts;
    
    foreach ($return_accounts as $my_account) {
        // only print merchant ID for this example...
        print "Merhant Account ID: " . $my_account->merchantAccountId . "\n";
    }
} else {
    print "No accounts match that email address. \n";
    print "returnCode: $return_code \n";
    print "returnString: $return_string \n";
}
?>
