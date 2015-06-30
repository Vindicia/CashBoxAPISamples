<?php
$app->get('/wsfinalizeautobillupdatepaidtrialstepuppricing', function(Request $request) use($app) {
    $webSession = new WebSession();
    $websession_id = $request->query->get('vin_WebSession_VID');
    $webSession->setVID($websession_id);
    $response = $webSession->finalize();

    // Add error checking and logging of soap ids
    // Check all the response codes, log soap id.
    $autobillVid = $response['data']->session->apiReturnValues->autoBillUpdate->autobill->VID;

    $modAutoBill = new AutoBill();
    $modAutoBill->setVID($autobillVid);

    $remID = 'Video';
    $remProduct = new Product();
    $remProduct->setMerchantProductId($remID);
    $remItem = new AutoBillItem();
    $remItem->setProduct($remProduct);

    $addID = 'VideoPricingSteppedUp';
    $addProduct = new Product();
    $addProduct->setMerchantProductId($addID);
    $addItem = new AutoBillItem();
    $addItem->setProduct($addProduct);

    $replaceModification = new AutoBillItemModification();
    $replaceModification->setRemoveAutoBillItem($remItem);
    $replaceModification->setAddAutoBillItem($addItem);

    $effectiveDate = 'nextBill';
    $prorate = 0;
    $changeBillingPlanTo = null;
    $dryrun = false;

    $response = $modAutoBill->modify($prorate, $effectiveDate, $changeBillingPlanTo, $replaceModification, $dryrun);
});
?>
