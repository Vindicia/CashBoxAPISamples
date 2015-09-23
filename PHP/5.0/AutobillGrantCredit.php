<?php
require_once("../../../API/50/Vindicia/Soap/Vindicia.php");
require_once("../../../API/50/Vindicia/Soap/Const.php");

$merchantAutoBillId = 'ab-2015-06-26_05_48_36';

function AddCurrencyCredit($merchantAutobillId)
{
    $autoBill = new AutoBill();
    $autoBill->setMerchantAutoBillId($merchantAutobillId);

    $curAmount = new CurrencyAmount();
    $curAmount->setCurrency('USD');
    $curAmount->setAmount(25);
    $cr = new Credit();
    $cr->setCurrencyAmounts(array($curAmount));
    $response = $autoBill->grantCredit($cr, '25 Currency Credit');
    logCall($response, 'AutoBill::GrantCredit', '25 Currency Credit');
}

function AddTimeCredit($merchantAutobillId, $daysRemaining)
{
    $autoBill = new AutoBill();
    $autoBill->setMerchantAutoBillId($merchantAutobillId);

    $timeInterval = new TimeInterval();
    $timeInterval->setDays($daysRemaining);
    $cr = new Credit();
    $cr->setTimeIntervals(array($timeInterval));
    $response = $autoBill->grantCredit($cr, 'Early Pay on NonRecurring Billing');
    logCall($response, 'AutoBill::GrantCredit', $daysRemaining);
}

function logCall($response, $method, $comment)
{
    if (($response['returnCode'] != 200)) {
        print 'Fail: ' . $comment . ' > ' . $method . ' > ' . $response['returnString'] . PHP_EOL;
    }
    else
    {
        print 'Success: ' . $comment . ' > '  . $method . PHP_EOL;
    }
    print PHP_EOL;
}


?>