<?php
/**
 *
 * This sample illustrates how you can cancel a subscription without immediately disentitling the customer.
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

function fetchMerchantAutobillId($merchantAccountId)
{
    $merchantAutoBillId = null;
    $autobillAPI = new AutoBill();
    $account = new Account();
    $account->merchantAccountId = $merchantAccountId;
    logCall('Autobill->fetchByAccount ' . $merchantAccountId);
    $response = $autobillAPI->fetchByAccount($account, true);

    if (isCallSuccessful($response)) {
        print "Found account with id: "
            . $merchantAccountId . PHP_EOL;
        $fetchedAutoBills = $response['data']->autobills;
        foreach ($fetchedAutoBills as $autobill) {
            // process each autobill found here
            print "Found autobill with id: "
                . $autobill->getMerchantAutoBillId() . PHP_EOL;
            // If this is the autobill you want, set it here.  Check if $autobill is still active.
            if ($autobill->status == 'Active') {
                $merchantAutoBillId = $autobill->getMerchantAutoBillId();
            }
        }
    }

    return $merchantAutoBillId;
}

$merchantAccountId = 'account-2015-01-19_08_33_26';
$merchantAutoBillId = fetchMerchantAutobillId($merchantAccountId);

$merchantAutoBillId = 'ab-2015-01-19_08_33_26';

$autobill = new AutoBill();
$autobill->setMerchantAutoBillId($merchantAutoBillId);

// Cancel the AutoBill - leave customer entitled for the current period he/she already paid for
$disentitle = false; // disentitle customer immediately
$force = true; // force - this is a No-op flag unless you are using minimum commitment period feature
$settle = false;
$sendCancellationNotice = false;

logCall('Autobill->cancel ' . $merchantAutoBillId);
$response = $autobill->cancel(
    $disentitle,
    $force,
    $settle,
    $sendCancellationNotice
);

if(isCallSuccessful($response))
{
    print('Subscription ID ' . $autobill->getMerchantAutoBillId() . ' has been cancelled!' . PHP_EOL);
    print('Your entitlements will last through ' . $response['data']->autobill->endTimestamp . '. There will be no further auto-renewals.' . PHP_EOL);
    // Cancellation will attempt to cancel any pending renewal transactions . But it may not be successful
    // if the renewal transaction is in flight (in process of being submitted to the processor and waiting for
    // response. In that case, we want to use CashBox's feature to schedule a refund against this transaction
    // after it captures
    $latestTx = null;
    $transaction = new Transaction();
    logCall('transaction->fetchByAutobill ' . $merchantAutoBillId);
    $response = $transaction->fetchByAutobill($autobill);
    if (isCallSuccessful($response)) {
        $fetchedTxns = $response['data']->transactions;

        if ($fetchedTxns != null) {
            foreach ($fetchedTxns as $fetchedTx) {
                if ($fetchedTx->transactionItems[0]->sku == 'VIN_PM_VALIDATION') {
                    // This is a validation transaction, so we can ignore it
                    continue;
                } else if ($latestTx == null) {
                    $latestTx = $fetchedTx;
                } else {
                    $latestTxDateTime = new DateTime($latestTx->timestamp);
                    $fetchedTxDateTime = new DateTime($fetchedTx->timestamp);
                    if ($latestTxDateTime < $fetchedTxDateTime) {
                        $latestTx = $fetchedTx;
                    }
                }
            }

            // if we are here, we got the latest transaction
            // We want to worry about only transactions in the New status - because these transactions have not
            // given next cycle's entitlements to the customer - so we want to make
            if ($latestTx != null) {
                print('Latest Transaction ' . $latestTx->merchantTransactionId . 'has status = '. $latestTx->statusLog[0]->status . PHP_EOL);
                if ($latestTx->statusLog[0]->status == 'New') {
                    print('Attempting to issue refund against pending transaction ID ' . $latestTx->merchantTransactionId . '.' . PHP_EOL);
                    $refund = new Refund();
                    $refund->setAmount($latestTx->getAmount());
                    $refund->setTransaction($latestTx);
                    $refund->setCurrency($latestTx->getCurrency());
                    $nowNewYork = new \DateTime('now', new \DateTimeZone('America/New_York'));
                    $refund->setNote('Refunding due to subscription cancellation at ' . $nowNewYork->format('Y-m-d_h_i_s') . PHP_EOL);
                    $refundAPI = new Refund();
                    logCall('refundAPI->perform ' . $latestTx->merchantTransactionId);
                    $response = $refundAPI->perform(array($refund));
                    if (isCallSuccessful($response)) {
                        $refunds = $response['data']->refunds;
                        print('Issued refund of ' . $refunds[0]->getAmount() . ' against pending transaction ID '
                            . $latestTx->getMerchantTransactionId() . PHP_EOL);
                    }
                }
            }
            else {
                // We do not need to do anything since we could not
                // find a transaction in 'New' status for this AutoBill
            }
        } else {
            print 'No transactions found' . PHP_EOL;
        }
    }
}
