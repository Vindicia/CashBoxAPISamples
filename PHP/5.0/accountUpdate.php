<?php
// Include the Vindicia library
require_once("../../../API/50/Vindicia/Soap/Vindicia.php");
require_once("../../../API/50/Vindicia/Soap/Const.php");

update_account_after_fetching_entire_account_object();
update_account_using_sparse_object();

function update_account_after_fetching_entire_account_object()
{
    $merchantAccountId = 'some_user_id_having_account';
    $accountAPI = new Account();

    $response = $accountAPI->fetchByMerchantAccountId($merchantAccountId);
    if ($response['returnCode'] == 200) {
        $account = $response['data']->account;
        if ($account != null) {
            $account->setEmailAddress('some_different_email_address@nomail.com');
            $response = $account->update();

            if ($response['returnCode'] != '200') {
                print('Error updating account' . PHP_EOL);
            }
        }
    }
}

function update_account_using_sparse_object()
{
    // If the merchant account id exists in the system, the data on the account will me updated.
    // If not, a new account is created.
    $merchantAccountId = 'some_user_id';
    $account = new Account();
    $account->setMerchantAccountId($merchantAccountId);
    $account->setEmailAddress('some_new_or_different_email_address@nomail.com');
    $response = $account->update();

    if ($response['returnCode'] != '200') {
        print('Error updating account' . PHP_EOL);
    }
}
