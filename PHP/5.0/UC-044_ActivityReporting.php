<pre>
<?php

#-----------------------------------------------------------------------------------
#
#	UC-044 	Activity Reporting
#
#		User Story
#
#		As a merchant, I need to report Activity to CashBox so that the Vindicia
#		MAT Team will have the supporting information on usage to be able to defend
#		my Chargebacks.
#
#
#		Pre-Condition(s):
#
#			Account with an existing PaymentMethod identified in $merchantAccountId.
#
#		Files Used:
#
#			Standalone.  Creates a Transaction & Reports Activity on the Account.
#
#-----------------------------------------------------------------------------------

ini_set('include_path','/usr/local/Vindicia_php5_lib_5.0');
ini_set('display_errors',1);
error_reporting(E_ALL|E_STRICT);

require_once('Vindicia/Soap/Vindicia.php');
require_once('Vindicia/Soap/Const.php');

$merchantAccountId = 'acct66485';

# use a sparse local Account object, only specify an identifier for CashBox
# to locate existing Account:
$account = new Account();
$account->setMerchantAccountId($merchantAccountId);

# This example is assuming we already know it's the default PM:
$response   = $account->fetchByMerchantAccountId($merchantAccountId);
$fetch_SoapId = $response['data']->return->soapId;
print "Account.fetchByMerchantAccountId soapId: " . $fetch_SoapId . PHP_EOL;
$customer = $response['data']->account;
$merchantPaymentMethodId = $customer->paymentMethods[0]->merchantPaymentMethodId;
$merchantTransactionId = 'testTrx-' . rand(10000, 99999);

# 1) Line Item Detail (at time of transaction):
createTransaction($account, $merchantPaymentMethodId, $merchantTransactionId);

# 2) Activity Reporting:
activity_record($account, $merchantTransactionId);



function activity_record($account, $trxId = "")
{
	#-----------------------------------------------------------------------------------
	#
	# Report Activity on the Account specified, indicating Usage of the merchant product
	# or service by the Cardholder subsequent to the Transaction, to support Chargeback
	# defense.
	#
	# 2) Activity Reporting (after transaction, when product/service used by Cardholder):
	#
	# This function demonstrates Reporting the following Activity Types:
	#
	#		1) Usage
	#		2) Phone
	#		3) Note
	#		4) NamedValue
	#
	# Considerations for information included in Activity records:
	#
	#	What should be included in the Activity information is what is necessary
	#	so the resulting information displays in the Portal on a single page view.
	#
	#	This allows the Vindicia MAT Team to take a single screenshot with the Transaction
	#	& Chargeback to submit as part of the response to the Chargeback.
	#
	#	This information includes:
	#
	# 	- The merchantTransactionId associated with the transaction that enabled the
	#	  Cardholder to have access for which Usage Activity is being reported.
	#
	#	- Username & email address of the user.
	#
	#	- The ip address from which the Activity originated (may need to put this in
	#	  a text field that displays in the Portal to see it, if the ip address data
	#	  member on the Activity Type does not get displayed in the Portal).
	#
	#	If Credits are used:
	#	- How many credits are being used/downloaded/referenced. (if credits used)
	#	- Balance of credits or tokens before & after use or purchase. (if credits used)
	#	- What type of credits are being used: For multiple items, which one?
	#
	# 	The above items can all be included in a single Description or Text field in one
	#	of the Activity Types above.  What should be verified in testing is that the
	#	combined Text field displays in the CashBox Portal on a single screen for the
	#	Transaction, without having to click in to any links for the  Activity.
	#
	#-----------------------------------------------------------------------------------
	
	#
	#	Timestamp format accepted by CashBox is from the WSDL xsd:dateTime datatype:
	#
	#	http://books.xmlschemata.org/relaxng/ch19-77049.html
	#
	# Name
	#
	# xsd:dateTime — Instant of time (Gregorian calendar)
	#
	# Description
	#
	# This datatype describes instances identified by the combination of a date and a time.
	# Its value space is described as a combination of date and time of day in Chapter 5.4
	# of ISO 8601. Its lexical space is the extended format:
	#
	# [-]CCYY-MM-DDThh:mm:ss[Z|(+|-)hh:mm]
	# The time zone may be specified as Z (UTC) or (+|-)hh:mm. Time zones that aren't
	# specified are considered undetermined.
	#

	if ( !empty($trxId) ) {
		$trxId = "[" . $trxId . "]: ";
	}
	
	$response = $account->update(); 
	$return_code = $response['returnCode'];
	print "Account.update Return Code is: $return_code \n";
	print_r ($response);

	$timestamp = '2015-03-25T10:41:39.000Z';
	
	# 1) Usage Activity:
	$lastusage = $timestamp;
	
	$activityUsage = new ActivityUsage();
	$activityUsage->setDescription($trxId . 'downloaded 2 songs');
	$activityUsage->setTotal('2');
	$activityUsage->setLastUsageDate($lastusage);
	$activityTypeArg = new ActivityTypeArg();
	$activityTypeArg->setUsageArgs( $activityUsage );
	$activity = new Activity();
	$activity->setActivityType('Usage');
	$activity->setActivityArgs( $activityTypeArg );

	$activity->setTimestamp($timestamp);
	$activity->setAccount($account);

	# 2) Phone Activity:
	$activityPhone = new ActivityPhoneContact();
	$activityPhone->setCidPhoneNumber('1234567890');
	$activityPhone->setDurationSeconds(367);
	$activityPhone->setType('FromCustomerToMerchant');
	$activityPhone->setNote($trxId . 'Customer agreed to be rebilled for services');
	$typeArg = new ActivityTypeArg();
	$typeArg->setPhoneArgs( $activityPhone );
	$activity2 = new Activity();
	$activity2->setActivityType('Phone');
	$activity2->setActivityArgs( $typeArg );

	$activity2->setTimestamp($timestamp);
	$activity2->setAccount($account);

	# 3) Note Activity:
	$activityNote = new ActivityNote();
	$activityNote->setNote($trxId . 'Note Activity: AVS=?, CVN=?');
	$typeNoteArg = new ActivityTypeArg();
	$typeNoteArg->setNoteArgs( $activityNote );
	$activity3 = new Activity();
	$activity3->setActivityType('Note');
	$activity3->setActivityArgs( $typeNoteArg );

	$activity3->setTimestamp($timestamp);
	$activity3->setAccount($account);

	# 4) NamedValue Activity:
	$activityNamedValue = new ActivityNamedValue();
	$activityNamedValue->setName('AVS, CVN');
	$activityNamedValue->setType('Response values');
	$activityNamedValue->setValue($trxId . 'IU, N');
	$typeNamedValueArg = new ActivityTypeArg();
	$typeNamedValueArg->setNamedvalueArgs( $activityNamedValue );
	$activity4 = new Activity();
	$activity4->setActivityType('NamedValue');
	$activity4->setActivityArgs( $typeNamedValueArg );

	$activity4->setTimestamp($timestamp);
	$activity4->setAccount($account);


	$response = $activity->record( array($activity ,$activity2, $activity3, $activity4) ); 
	print_r ($response);
	$return_code = $response['returnCode'];
	print "Activity.record Return Code is: $return_code \n";
	$return_string = $response['returnString'];
	print "Activity.record Return String is: $return_string \n";
	$activityRecord_SoapId = $response['data']->return->soapId;
	print "Activity.record soapId: " . $activityRecord_SoapId . PHP_EOL;
}


function createTransaction($account, $merchantPaymentMethodId, $merchantTransactionId)
{
	#-----------------------------------------------------------------------------------
	# Process Transaction, setting Line Items so they will be helpful in providing
	# a clear indication of what was being purchased, the cost per item and the
	# price for each item, to be used for the first step in collecting information
	# needed for Chargeback defense.
	#	
	# 1) Line Item Detail (at time of transaction):
	#
	# a) Line Item Detail for One Time Transactions:
	#
	# First, note that at the time of the transaction, you are already logging Line Item
	# information in TransactionItem entries on the Transaction in realtime.
	#
	# This function demonstrates setting of these Line Items in preparation for use as
	# part of Chargeback defense, used in conjunction with the Activity Reporting which
	# is subsequent sent to CashBox once Usage by the Cardholder has occurred.
	#
	#-----------------------------------------------------------------------------------
	
	$transaction = new Transaction();
	$transaction->setCurrency('USD');

	# Specify Account to use:
	$transaction->setAccount($account);
	
	# Specify PaymentMethod to use:
	# use a sparse local PaymentMethod object, only specify an identifier for CashBox
	# to locate existing PaymentMethod:
    $pm = new PaymentMethod();
    $pm->setMerchantPaymentMethodId($merchantPaymentMethodId);
	$transaction->setSourcePaymentMethod($pm);

	# Specify merchantTransactionId to use:
	$transaction->setMerchantTransactionId($merchantTransactionId);
	
	# Considerations for Line Time Detail:
	#
	# 1) Descriptive name for the Product/SKU, ideally readable text clearly indicating:
	#		- Product name
	#		- Duration it is valid
	#		- What the Product/SKU applies to
	#
	# 2) Description field, set in the API as TransactionItem.name field, indicating:
	#		- How much it cost for 1 item
	#		- For what period the purchase is applicable
	#		- League or other identifying information.
	#
	# 3) Quantity should show how many were purchased.
	# 4) The Amount for a single unit should be indicated in the Line Item.
	# 
	# This will result in CashBox automatically setting the Transaction Amount to be
	# the total of the extended price for each Line Item from the product of Quantity
	# & Amount from each Line Item.
	
	# Use a single Line Item for this sample:
	
	$tx_lineItem0 = new TransactionItem();
	$tx_lineItem0->setSku('WIDGET200');						# 1) Descriptive name
	$tx_lineItem0->setName('600 Widgets ($3.30 for 200)');	# 2) Description field
	$tx_lineItem0->setMerchantAutoBillItemId('foobar');
	$tx_lineItem0->setQuantity('3');			# 3) Quantity: how many were purchased
	$tx_lineItem0->setPrice('3.30');			# 4) Amount for a single unit
	
	# CashBox automatically computes extended price (3 X $3.30) & sets the
	# Transaction amount to be the result, $9.90.
	
	# Campaign Code can be assigned to each individual line item:
	# $transaction_lineItem0->setCampaignCode('promo2');
	$lineitems = array(
    	$tx_lineItem0
	);
	$transaction->setTransactionItems($lineitems);

	# we can choose to send email for one-time transactions, or not
	$sendEmailNotification = true;
	# use the default CashBox AVS and CVN policy
	$ignoreAvsPolicy = false;
	$ignoreCvnPolicy = false;
	# Campaign Code can also be passed in to the call as a param to apply to all eligible items
	$campaign = '';		# 'promo2'; 
	$dryrun = false;
	$response = $transaction->authCapture($sendEmailNotification, $ignoreAvsPolicy, $ignoreCvnPolicy, $campaign, $dryrun);
	print_r($response);
	$return_code = $response['returnCode'];
	print "Transaction.authCapture Return Code is: $return_code \n";
	$return_string = $response['returnString'];
	print "Transaction.authCapture Return String is: $return_string \n";
	$txAuthCapture_SoapId = $response['data']->return->soapId;
	print "Transaction.authCapture soapId: " . $txAuthCapture_SoapId . PHP_EOL . PHP_EOL;
	
	$txStatus = $response['data']->transaction->statusLog[0];
	$status = $txStatus->status;
	print "Transaction.authCapture status: " . $status . PHP_EOL;
	$vinAVS = $txStatus->vinAVS;
	print "Transaction.authCapture vinAVS: " . $vinAVS . PHP_EOL;
	$ccStatus = $txStatus->creditCardStatus;
	print_r($ccStatus);
	$authCode = $ccStatus->authCode;
	print "Transaction.authCapture authCode: " . $authCode . PHP_EOL;
	$avsCode = $ccStatus->avsCode;
	print "Transaction.authCapture avsCode: " . $avsCode . PHP_EOL;
	$cvnCode = $ccStatus->cvnCode;
	print "Transaction.authCapture cvnCode: " . $cvnCode . PHP_EOL;
	$extendedCardAttributes = $ccStatus->extendedCardAttributes;
	print "Transaction.authCapture extendedCardAttributes: " . PHP_EOL;
	print_r($extendedCardAttributes);
}

?>
</pre>
