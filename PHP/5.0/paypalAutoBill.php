<pre>
<?php
/**
 * Date: 6/18/15
 * Time: 5:37 PM
 */
ini_set('include_path','/usr/local/Vindicia_php5_lib_9.0');
//ini_set('include_path','../../../API/90');
ini_set('display_errors',1);
 
// Include the Vindicia library
require_once("Vindicia/Soap/Vindicia.php");
require_once("Vindicia/Soap/Const.php");
/**
 * Step 1. Create Account with Payment Method object
 *
 */
$results = create_paypal_PaymentMethod();

$account = $results['account'];
$paymentmethod = $results['paymentmethod'];

print "results: account=" . $account->getMerchantAccountId() . PHP_EOL;
print "results: paymentmethod=" . $paymentmethod->getMerchantPaymentMethodId() . PHP_EOL;

//$account = create_paypal_PaymentMethod();

/**
 * Step 2. Create the Autobill and redirect the user to PayPal to authorize the order.
 * PayPal will return the vid in the query string when redirecting back to the store front.
 */
$redirection_url = create_paypal_AutoBill($account, $paymentmethod);

	print PHP_EOL;
	print "<b><i>SOAP URL</i></b>: " . VIN_SOAP_HOST . PHP_EOL;
	print "<b>PayPal Redirect URL</b>: " . $redirection_url . PHP_EOL;
	print PHP_EOL;

/**
 * Step 3. Finalize the Autobill after the user has gone through PayPal to authorize the order.
 * Obtain vid from the query string.
 */

// The code below needs to be hosted at the $successUrl defined in the
// method create_paypal_PaymentMethod() below (i.e. something other than
// 'http://good.com/' for purposes of actual testing).
//
// When PayPal redirects to the $successUrl, here is an
// 		example query string on redirect from PayPal:
//
// http://localhost/PayPalAutobill/success.php?vindicia_vid=4fef668588c9631cab82e9dbe136b6d7d0859f62
//							&token=EC-5L949920TC0737454&PayerID=7QT2TU58TPE7U
//
// $vid = '4fef668588c9631cab82e9dbe136b6d7d0859f62';
//
print_r($_GET);		// The $_GET array will contain the vid to use in 'vindicia_vid'
$vid = $_GET['vindicia_vid'];	// obtain VID from redirect URL
finalize_paypal_AutoBill($vid);

function get_unique_value()
{
    $nowNewYork = new \DateTime( 'now',  new \DateTimeZone( 'America/New_York' ) );
    return $nowNewYork->format('Y-m-d_h_i_s');
}

function create_paypal_PaymentMethod()
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
    $paymentmethod->setType('PayPal');
    $paymentmethod->setAccountHolderName($name);
    $paymentmethod->setBillingAddress($address);
    $paymentmethod->setMerchantPaymentMethodId($merchantPaymentMethodId);
    $paymentmethod->setCurrency('USD');
    $paypal = new PayPal();
    $paypal->setReturnUrl($successUrl);
    $paypal->setCancelUrl($errorUrl);
    $paymentmethod->setPaypal($paypal);
    $account = new Account();
    $account->setMerchantAccountId($merchantAccountId);
    $account->setEmailAddress($email);
    $account->setShippingAddress($address);
    $account->setEmailTypePreference('html');
    $account->setName($name);
    //$account->setPaymentMethods(array($paymentmethod));
    //return $account;
	return array('account' => $account, 'paymentmethod' => $paymentmethod);
}

function create_paypal_AutoBill($account, $paymentmethod)
{
    $ipAddress = '127.0.0.1';
    $uniqueValue = get_unique_value();
    $merchantAutoBillId = 'ab-' . $uniqueValue;
    $merchantProductId = 'mm_1_day_recurring';	//'Video';
    $merchantBillingPlanId = 'Daily';	//'OneMonthSubOneMonthRecurring';
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
	$autobill->setPaymentMethod($paymentmethod);	// Set PaymentMethod used by AutoBill
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
        if ($auth_status->status == 'AuthorizationPending') {
            $redirection_url = $auth_status->payPalStatus->redirectUrl;
            echo "To authorize, please visit: <a href=\"" . $redirection_url . "\">Continue to Paypal</a>"  . PHP_EOL;
        } else if ($auth_status->status == 'Cancelled') {
            echo "Autobill not accepted by PayPal";
        } else {
            echo "Status = " . $auth_status->status;
        }
    }
    return $redirection_url;
}

function finalize_paypal_AutoBill($vid)
{
    if ($vid == null) {
        $vid = $_GET['vindicia_vid'];
    }
    $autobill = new Autobill();
    $response = $autobill->finalizePayPalAuth($vid, true);
    if ($response['returnCode'] != '200') {
        print('Error finalizing autobill' . PHP_EOL);
        print 'Soap Id = ' . $response['data']->return->soapId . PHP_EOL;
        print 'Return Code = ' . $response['returnCode'] . PHP_EOL;
        print 'Return String = ' . $response['returnString'] . PHP_EOL;
    } else {
        // You can obtain the paypal payer email address from the return object if you desire to persist this.
        $response_object = $response['data'];
        $auth_status = $response_object->authStatus;
        $payPalEmail = $auth_status->payPalStatus->paypalEmail;
        print('Successfully paid for by ' . $payPalEmail);
    }
}
?>
</pre>
