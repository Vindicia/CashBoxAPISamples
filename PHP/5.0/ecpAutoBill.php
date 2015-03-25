<?php

// Include the Vindicia library
require_once("Vindicia/Soap/Vindicia.php");
require_once("Vindicia/Soap/Const.php");

/**
 * Step 1. Create Account with Payment Method object
 *
 */
$account = create_ecp_PaymentMethod();
/**
 * Step 2. Create the Autobill with ECP as the payment method
 */
$response = create_ecp_AutoBill($account);

function get_unique_value()
{
    $nowNewYork = new \DateTime( 'now',  new \DateTimeZone( 'America/New_York' ) );
    return $nowNewYork->format('Y-m-d_h_i_s');
}
function create_ecp_PaymentMethod()
{
    $uniqueValue = get_unique_value();
    $merchantAccountId = 'account-' . $uniqueValue;
    $merchantPaymentMethodId = 'pm-' . $uniqueValue;
    $email = get_unique_value() . '@nomail.com';
    $successUrl = 'http://good.com/'; //need a trailing slash
    $errorUrl = 'http://bad.com/'; //need a trailing slash
    $name = 'John Vindicia';
    $addr1 = '303 Twin Dolphin Drive';
    $city = 'Redwood City';
    $district = 'CA';
    $postalCode = '94065';
    $country = 'US';
    $address = new Address();
    $address->setName($name);
    $address->setAddr1($addr1);
    $address->setCity($city);
    $address->setDistrict($district);
    $address->setPostalCode($postalCode);
    $address->setCountry($country);
    
    $paymentmethod = new PaymentMethod();
    $paymentmethod->setType('ECP');
    $paymentmethod->setAccountHolderName($name);
    $paymentmethod->setBillingAddress($address);
    $paymentmethod->setMerchantPaymentMethodId($merchantPaymentMethodId);
    $paymentmethod->setCurrency('USD');
   
    $ecp = new ECP();
    $ecp->setAccount('495958930');
    $ecp->setRoutingNumber('611111111');
    $ecp->setAllowedTransactionType('Inbound');
    $ecp->setAccountType('ConsumerChecking');
       
    $paymentmethod->setECP($ecp);
    
    $account = new Account();
    $account->setMerchantAccountId($merchantAccountId);
    $account->setEmailAddress($email);
    $account->setShippingAddress($address);
    $account->setEmailTypePreference('html');
    $account->setName($name);
    $account->setPaymentMethods(array($paymentmethod));
    return $account;
}
function create_ecp_AutoBill($account)
{
    $ipAddress = '127.0.0.1';
    $uniqueValue = get_unique_value();
    $merchantAutoBillId = 'ab-' . $uniqueValue;
    $merchantProductId = 'git_hub_product';
    $merchantBillingPlanId = 'git_hub_example';
    $autobill = new AutoBill();
    $autobill->setMerchantAutoBillId($merchantAutoBillId);
    $autobill->setAccount($account);
    $product = new Product();
    $product->setMerchantProductId($merchantProductId);
    $billingplan = new BillingPlan();
    $billingplan->setMerchantBillingPlanId($merchantBillingPlanId);
    $item = new AutoBillItem();
    $item->setIndex(0);
    $item->setProduct($product);
    $autobill->setItems(array($item));
    $autobill->setSourceIp($ipAddress);
    $autobill->setBillingPlan($billingplan);
    $autobill->setCurrency('USD');
    //$duplicateBehavior = 'Fail'; //removed in 9.0
    //$validatePaymentMethod = true; //removed in 9.0
    $immediateAuthFailurePolicy = 'doNotSaveAutoBill'; //added in 9.0
    $validateForFuturePayment = true; //added in 9.0
    $minChargebackProbability = 100;
    $ignoreAvsPolicy = true;
    $ignoreCvnPolicy = true;
    $campaignCode = null;
    $dryrun = false;
    $response = $autobill->update(
        //$duplicateBehavior, //removed in 9.0
        //$validatePaymentMethod, //removed in 9.0
        $immediateAuthFailurePolicy, //added in 9.0
        $validateForFuturePayment, //added in 9.0
        $minChargebackProbability,
        $ignoreAvsPolicy,
        $ignoreCvnPolicy,
        $campaignCode,
        $dryrun);
    if ($response['returnCode'] != '200') {
        print('Error creating autobill' . PHP_EOL);
        print 'Soap Id = ' . $response['data']->return->soapId . PHP_EOL;
        print 'Return Code = ' . $response['returnCode'] . PHP_EOL;
        print 'Return String = ' . $response['returnString'] . PHP_EOL;
    } else {
        $response_object = $response['data'];
        $auth_status = $response_object->authStatus;
        if ($auth_status->status == 'AuthorizedForValidation') {
            echo "ECP payment in AuthorizedForValidation status, this is a successful test of ECP."  . PHP_EOL;
        } else if ($auth_status->status == 'Cancelled') {
            echo "Validation of ECP failed.";
        } else {
            echo "Status = " . $auth_status->status;
        }
    }
    return $auth_status->status;
}
