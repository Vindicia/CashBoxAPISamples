<?php

require_once("../../../API/50/Vindicia/Soap/Vindicia.php");
require_once("../../../API/50/Vindicia/Soap/Const.php");

$refund = new Refund();

$nowPT = new \DateTime( 'now',  new \DateTimeZone( 'America/Los_Angeles' ) );
$aDayAgoPT = new \DateTime( '-30 days',  new \DateTimeZone( 'America/Los_Angeles' ) );
$today = $nowPT->format(DATE_ATOM);
$since = $aDayAgoPT->format(DATE_ATOM);

//$merchantPaymentMethodId = 'UniquePaymentMethodId';
//$paymentMethod = new PaymentMethod();
//$paymentMethod->setMerchantPaymentMethodId($merchantPaymentMethodId);
$paymentMethod = null;

$totalCount = 0;
$ret = $refund->fetchDeltaSince($since, $today, $paymentMethod);
$count = 0;
if ($ret['returnCode'] == 200) {
    $fetchedRefunds = $ret['data']->refunds;
    if ($fetchedRefunds != null) {
        $count = sizeof($fetchedRefunds);
        print($count.' returned.'.PHP_EOL);
        foreach ($fetchedRefunds as $ref) {
            print('Refund Id='.$ref->merchantRefundId.PHP_EOL);
            $totalCount++;
        }
    }
    else {
        print('No transactions returned.'.PHP_EOL);
    }
}
else if ($ret['returnCode'] == 404) {
    print('No refunds returned.'.PHP_EOL);
}
else {
    print('Refund fetch failed'.PHP_EOL);
}
print('Fetched total='.$totalCount.PHP_EOL);

