<?php
require_once("../../../API/50/Vindicia/Soap/Vindicia.php");
require_once("../../../API/50/Vindicia/Soap/Const.php");

$merchantAutoBillId = 'ab-2015-06-26_05_48_36';

function AddCurrencyCredit($merchantAutobillId)
{
    $autoBill = new AutoBill();
    $autoBill->setMerchantAutoBillId($merchantAutobillId);
    
//note field can be empty - it doesn't get used.
//use description and/or reason fields in the Credit object instead

    $curAmount = new CurrencyAmount();
    $curAmount->setCurrency('USD');
    $curAmount->setAmount(25);
    $curAmount->setDescription("25 Dollar Currency Credit");
    $curAmount->setReason("Credit for July");
    
    $cr = new Credit();
    $cr->setCurrencyAmounts(array($curAmount));
    $response = $autoBill->grantCredit($cr, '');
    logCall($response, 'AutoBill::GrantCredit', '25 Currency Credit');
}

function AddTimeCredit($merchantAutobillId, $daysRemaining)
{
    $autoBill = new AutoBill();
    $autoBill->setMerchantAutoBillId($merchantAutobillId);

    $timeInterval = new TimeInterval();
    $timeInterval->setDays($daysRemaining);
    $timeInterval->setDescription("Early Pay on NonRecurring Billing");
    $timeInterval->setReason("Credit for July");
    
    $cr = new Credit();
    $cr->setTimeIntervals(array($timeInterval));
    $response = $autoBill->grantCredit($cr, '');
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
