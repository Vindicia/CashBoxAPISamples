<?php
/**
 *
 * This sample illustrates how you can retrieve purchase history for the customer.
 *
 */

//ini_set('include_path','/usr/local/Vindicia_php5_lib_9.0');
ini_set('include_path','../../../API/90');
ini_set('display_errors',1);

require_once("Vindicia/Soap/Vindicia.php");
require_once("Vindicia/Soap/Const.php");

function logCall($call_name)
{
    $now = new \DateTime('now', new \DateTimeZone('America/New_York'));
    print $now->format('Y-m-d_h_i_s') . ': ' . $call_name . ': ' . VIN_SOAP_CLIENT_USER . PHP_EOL;
}

function isCallSuccessful($response)
{
    if($response['returnCode'] == 200) {
        return true;
    }
    else {
        print('Error:' . PHP_EOL);
        print 'Soap Id = ' . $response['data']->return->soapId . PHP_EOL;
        print 'Return Code = ' . $response['returnCode'] . PHP_EOL;
        print 'Return String = ' . $response['returnString'] . PHP_EOL;
        return false;
    }
}

$merchantAccountId = '313a6101cd7208f047af98256dceae582dc8537cfaa258845323741f7f3975668c35';
//$merchantAccountId = 'account-2015-01-19_08_33_26';

$account = new Account();
$account->setMerchantAccountId($merchantAccountId);

$autobillAPI = new AutoBill();

logCall('Autobill->fetchByAccount ' . $merchantAccountId);
$response = $autobillAPI->fetchByAccount($account, true);

if(isCallSuccessful($response))
{
    print "Found account with id: "
        . $merchantAccountId . PHP_EOL;
    $fetchedAutoBills= $response['data']->autobills;
    foreach ($fetchedAutoBills as $autobill) {
        // process each autobill found here
        print "Found autobill with id: "
            . $autobill->getMerchantAutoBillId() . PHP_EOL;
        print "Start Date: " . $autobill->startTimestamp . PHP_EOL;
        print "NextBilling: " . PHP_EOL;
        print "Date: " . $autobill->nextBilling->getTimestamp() . " ";
        print "Amount: " . $autobill->nextBilling->getAmount() . PHP_EOL;

        $autobillVid = $autobill->VID;
        $autobill = new AutoBill();
        $autobill->setVID($autobillVid);
        $numberOfFutureRebillsToFetch=24;

        logCall('Autobill->fetchFutureRebills ' . $merchantAccountId);
        $response = $autobill->fetchFutureRebills($numberOfFutureRebillsToFetch);

        if(isCallSuccessful($response)) {
            $futureTxns = $response['data']->transactions;
            print "Fetched " . $numberOfFutureRebillsToFetch . " future rebills." . PHP_EOL;
            print "This subscription will be billed at the following dates and amounts:" . PHP_EOL;
            foreach ($futureTxns as $futureTxn) {
                print "Date: " . $futureTxn->getTimestamp() . " ";
                print "Amount: " . $futureTxn->getAmount() . PHP_EOL;
            }
        }
    }
}

$transactionAPI = new Transaction();
logCall('transaction->fetchByAccount ' . $merchantAccountId);
$response = $transactionAPI->fetchByAccount($account, false);

if(isCallSuccessful($response)) {
    $fetchedTxns = $response['data']->transactions;

    if ($fetchedTxns != null) {
        foreach ($fetchedTxns as $fetchedTx) {
            print "Transaction VID " . $fetchedTx->getVID(). PHP_EOL;
            print "Transaction amount ". $fetchedTx->getAmount() . PHP_EOL;
            print "Transaction status " . PHP_EOL;
            print $fetchedTx->statusLog[0]->status . PHP_EOL;
        }
    }
    else {
        print "No transactions found \n";
    }
}

$refundAPI = new Refund();
logCall('refundAPI->fetchByAccount ' . $merchantAccountId);
$response = $refundAPI->fetchByAccount($account, false);

if(isCallSuccessful($response)) {
    $refunds = $response['data']->refunds;

    if ($refunds != null) {
        foreach ($refunds as $refund) {
            print('Refund of ' . $refund->amount . ' with Refund Id '
                . $refund->merchantRefundId . ' had been issued against payment (transaction Id '
                . $refund->transaction->merchantTransactionId . ') of ' . $refund->transaction->amount . ' made on '
                . $refund->transaction->timestamp. PHP_EOL);
        }
    }
    else {
        print "No refunds found \n";
    }
}
