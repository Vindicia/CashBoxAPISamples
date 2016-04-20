<?php
class NameValuePair {
  public $name; // string
  public $value; // string
}

class TransactionValidationResponse {
  public $merchantTransactionId; // string
  public $code; // int
  public $description; // string
}

class Transaction {
  public $VID; // string
  public $timestamp; // dateTime
  public $amount; // decimal
  public $currency; // string
  public $status; // TransactionStatusType
  public $divisionNumber; // string
  public $merchantTransactionId; // string
  public $selectTransactionId; // string
  public $selectRefundId; // string
  public $subscriptionId; // string
  public $subscriptionStartDate; // dateTime
  public $billingFrequency; // BillingIntervalType
  public $previousBillingDate; // dateTime
  public $previousBillingCount; // int
  public $customerId; // string
  public $paymentMethodId; // string
  public $paymentMethodIsTokenized; // boolean
  public $creditCardAccount; // string
  public $creditCardAccountHash; // string
  public $creditCardExpirationDate; // string
  public $accountHolderName; // string
  public $billingAddressLine1; // string
  public $billingAddressLine2; // string
  public $billingAddressLine3; // string
  public $billingAddressCity; // string
  public $billingAddressCounty; // string
  public $billingAddressDistrict; // string
  public $billingAddressPostalCode; // string
  public $billingAddressCountry; // string
  public $nameValues; // NameValuePair
  public $affiliateId; // string
  public $affiliateSubId; // string
  public $billingStatementIdentifier; // string
  public $authCode; // string
  public $avsCode; // string
  public $cvnCode; // string
  public $creditCardAccountUpdated; // boolean
}

class Chargeback {
  public $VID; // string
  public $amount; // decimal
  public $currency; // string
  public $presentmentAmount; // decimal
  public $presentmentCurrency; // string
  public $divisionNumber; // string
  public $reasonCode; // string
  public $caseNumber; // string
  public $referenceNumber; // string
  public $merchantNumber; // string
  public $merchantTransactionId; // string
  public $merchantTransactionTimestamp; // dateTime
  public $processorReceivedTimestamp; // dateTime
  public $postedTimestamp; // dateTime
  public $statusChangedTimestamp; // dateTime
  public $status; // ChargebackStatus
  public $merchantUserId; // string
  public $note; // string
  public $nameValues; // NameValuePair
}

class Authentication {
  public $version; // string
  public $login; // string
  public $password; // string
  public $evid; // string
  public $userAgent; // string
}

class VindiciaReturn {
  public $returnCode; // ReturnCode
  public $returnString; // string
  public $soapId; // string
}

class TransactionStatusType {
}

class BillingIntervalType {
}

class ChargebackStatus {
}

class ReturnCode {
}

class reportTransactions {
  public $auth; // Authentication
  public $transactions; // Transaction
}

class reportTransactionsResponse {
  public $return; // Return
  public $response; // TransactionValidationResponse
}

class billTransactions {
  public $auth; // Authentication
  public $transactions; // Transaction
}

class billTransactionsResponse {
  public $return; // Return
  public $response; // TransactionValidationResponse
}

class refundTransactions {
  public $auth; // Authentication
  public $refunds; // string
}

class refundTransactionsResponse {
  public $return; // Return
  public $response; // TransactionValidationResponse
}

class fetchBillingResults {
  public $auth; // Authentication
  public $timestamp; // dateTime
  public $endTimestamp; // dateTime
  public $page; // int
  public $pageSize; // int
}

class fetchBillingResultsResponse {
  public $return; // Return
  public $transactions; // Transaction
}

class fetchChargebacks {
  public $auth; // Authentication
  public $timestamp; // dateTime
  public $endTimestamp; // dateTime
  public $page; // int
  public $pageSize; // int
}

class fetchChargebacksResponse {
  public $return; // Return
  public $chargebacks; // Chargeback
}

class fetchByMerchantTransactionId {
  public $auth; // Authentication
  public $merchantTransactionId; // string
}

class fetchByMerchantTransactionIdResponse {
  public $return; // Return
  public $transaction; // Transaction
}


/**
 * Select class
 * 
 *  
 * 
 * @author    {author}
 * @copyright {copyright}
 * @package   {package}
 */
class Select extends SoapClient {

  private static $classmap = array(
                                    'NameValuePair' => 'NameValuePair',
                                    'TransactionValidationResponse' => 'TransactionValidationResponse',
                                    'Transaction' => 'Transaction',
                                    'Chargeback' => 'Chargeback',
                                    'Authentication' => 'Authentication',
                                    'Return' => 'Return',
                                    'TransactionStatusType' => 'TransactionStatusType',
                                    'BillingIntervalType' => 'BillingIntervalType',
                                    'ChargebackStatus' => 'ChargebackStatus',
                                    'ReturnCode' => 'ReturnCode',
                                    'reportTransactions' => 'reportTransactions',
                                    'reportTransactionsResponse' => 'reportTransactionsResponse',
                                    'billTransactions' => 'billTransactions',
                                    'billTransactionsResponse' => 'billTransactionsResponse',
                                    'refundTransactions' => 'refundTransactions',
                                    'refundTransactionsResponse' => 'refundTransactionsResponse',
                                    'fetchBillingResults' => 'fetchBillingResults',
                                    'fetchBillingResultsResponse' => 'fetchBillingResultsResponse',
                                    'fetchChargebacks' => 'fetchChargebacks',
                                    'fetchChargebacksResponse' => 'fetchChargebacksResponse',
                                    'fetchByMerchantTransactionId' => 'fetchByMerchantTransactionId',
                                    'fetchByMerchantTransactionIdResponse' => 'fetchByMerchantTransactionIdResponse',
                                   );

  public function Select($wsdl = "https://soap.vindicia.com/1.1/Select.wsdl", $options = array()) {
    foreach(self::$classmap as $key => $value) {
      if(!isset($options['classmap'][$key])) {
        $options['classmap'][$key] = $value;
      }
    }
    parent::__construct($wsdl, $options);
  }

  /**
   *  
   *
   * @param reportTransactions $parameters
   * @return reportTransactionsResponse
   */
  public function reportTransactions(reportTransactions $parameters) {
    return $this->__soapCall('reportTransactions', array($parameters),       array(
            'uri' => 'http://soap.vindicia.com/v1_1/Select',
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param billTransactions $parameters
   * @return billTransactionsResponse
   */
  public function billTransactions(billTransactions $parameters) {
    return $this->__soapCall('billTransactions', array($parameters),       array(
            'uri' => 'http://soap.vindicia.com/v1_1/Select',
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param refundTransactions $parameters
   * @return refundTransactionsResponse
   */
  public function refundTransactions(refundTransactions $parameters) {
    return $this->__soapCall('refundTransactions', array($parameters),       array(
            'uri' => 'http://soap.vindicia.com/v1_1/Select',
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param fetchBillingResults $parameters
   * @return fetchBillingResultsResponse
   */
  public function fetchBillingResults(fetchBillingResults $parameters) {
    return $this->__soapCall('fetchBillingResults', array($parameters),       array(
            'uri' => 'http://soap.vindicia.com/v1_1/Select',
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param fetchChargebacks $parameters
   * @return fetchChargebacksResponse
   */
  public function fetchChargebacks(fetchChargebacks $parameters) {
    return $this->__soapCall('fetchChargebacks', array($parameters),       array(
            'uri' => 'http://soap.vindicia.com/v1_1/Select',
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param fetchByMerchantTransactionId $parameters
   * @return fetchByMerchantTransactionIdResponse
   */
  public function fetchByMerchantTransactionId(fetchByMerchantTransactionId $parameters) {
    return $this->__soapCall('fetchByMerchantTransactionId', array($parameters),       array(
            'uri' => 'http://soap.vindicia.com/v1_1/Select',
            'soapaction' => ''
           )
      );
  }

}

?>
