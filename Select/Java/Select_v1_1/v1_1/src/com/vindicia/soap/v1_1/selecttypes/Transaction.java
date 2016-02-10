
/**
 * Transaction.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.vindicia.soap.v1_1.selecttypes;
            

            /**
            *  Transaction bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class Transaction
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = Transaction
                Namespace URI = http://soap.vindicia.com/v1_1/SelectTypes
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for VID
                        */

                        
                                    protected java.lang.String localVID ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localVIDTracker = false ;

                           public boolean isVIDSpecified(){
                               return localVIDTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getVID(){
                               return localVID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param VID
                               */
                               public void setVID(java.lang.String param){
                            localVIDTracker = param != null;
                                   
                                            this.localVID=param;
                                    

                               }
                            

                        /**
                        * field for Timestamp
                        */

                        
                                    protected java.util.Calendar localTimestamp ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getTimestamp(){
                               return localTimestamp;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Timestamp
                               */
                               public void setTimestamp(java.util.Calendar param){
                            
                                            this.localTimestamp=param;
                                    

                               }
                            

                        /**
                        * field for Amount
                        */

                        
                                    protected java.math.BigDecimal localAmount ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.math.BigDecimal
                           */
                           public  java.math.BigDecimal getAmount(){
                               return localAmount;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Amount
                               */
                               public void setAmount(java.math.BigDecimal param){
                            
                                            this.localAmount=param;
                                    

                               }
                            

                        /**
                        * field for Currency
                        */

                        
                                    protected java.lang.String localCurrency ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCurrencyTracker = false ;

                           public boolean isCurrencySpecified(){
                               return localCurrencyTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCurrency(){
                               return localCurrency;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Currency
                               */
                               public void setCurrency(java.lang.String param){
                            localCurrencyTracker = param != null;
                                   
                                            this.localCurrency=param;
                                    

                               }
                            

                        /**
                        * field for Status
                        */

                        
                                    protected com.vindicia.soap.v1_1.selecttypes.TransactionStatusType localStatus ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vindicia.soap.v1_1.selecttypes.TransactionStatusType
                           */
                           public  com.vindicia.soap.v1_1.selecttypes.TransactionStatusType getStatus(){
                               return localStatus;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Status
                               */
                               public void setStatus(com.vindicia.soap.v1_1.selecttypes.TransactionStatusType param){
                            
                                            this.localStatus=param;
                                    

                               }
                            

                        /**
                        * field for DivisionNumber
                        */

                        
                                    protected java.lang.String localDivisionNumber ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDivisionNumber(){
                               return localDivisionNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DivisionNumber
                               */
                               public void setDivisionNumber(java.lang.String param){
                            
                                            this.localDivisionNumber=param;
                                    

                               }
                            

                        /**
                        * field for MerchantTransactionId
                        */

                        
                                    protected java.lang.String localMerchantTransactionId ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMerchantTransactionId(){
                               return localMerchantTransactionId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MerchantTransactionId
                               */
                               public void setMerchantTransactionId(java.lang.String param){
                            
                                            this.localMerchantTransactionId=param;
                                    

                               }
                            

                        /**
                        * field for SelectTransactionId
                        */

                        
                                    protected java.lang.String localSelectTransactionId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSelectTransactionIdTracker = false ;

                           public boolean isSelectTransactionIdSpecified(){
                               return localSelectTransactionIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSelectTransactionId(){
                               return localSelectTransactionId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SelectTransactionId
                               */
                               public void setSelectTransactionId(java.lang.String param){
                            localSelectTransactionIdTracker = param != null;
                                   
                                            this.localSelectTransactionId=param;
                                    

                               }
                            

                        /**
                        * field for SelectRefundId
                        */

                        
                                    protected java.lang.String localSelectRefundId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSelectRefundIdTracker = false ;

                           public boolean isSelectRefundIdSpecified(){
                               return localSelectRefundIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSelectRefundId(){
                               return localSelectRefundId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SelectRefundId
                               */
                               public void setSelectRefundId(java.lang.String param){
                            localSelectRefundIdTracker = param != null;
                                   
                                            this.localSelectRefundId=param;
                                    

                               }
                            

                        /**
                        * field for SubscriptionId
                        */

                        
                                    protected java.lang.String localSubscriptionId ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSubscriptionId(){
                               return localSubscriptionId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SubscriptionId
                               */
                               public void setSubscriptionId(java.lang.String param){
                            
                                            this.localSubscriptionId=param;
                                    

                               }
                            

                        /**
                        * field for SubscriptionStartDate
                        */

                        
                                    protected java.util.Calendar localSubscriptionStartDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSubscriptionStartDateTracker = false ;

                           public boolean isSubscriptionStartDateSpecified(){
                               return localSubscriptionStartDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getSubscriptionStartDate(){
                               return localSubscriptionStartDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SubscriptionStartDate
                               */
                               public void setSubscriptionStartDate(java.util.Calendar param){
                            localSubscriptionStartDateTracker = param != null;
                                   
                                            this.localSubscriptionStartDate=param;
                                    

                               }
                            

                        /**
                        * field for BillingFrequency
                        */

                        
                                    protected com.vindicia.soap.v1_1.selecttypes.BillingIntervalType localBillingFrequency ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBillingFrequencyTracker = false ;

                           public boolean isBillingFrequencySpecified(){
                               return localBillingFrequencyTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vindicia.soap.v1_1.selecttypes.BillingIntervalType
                           */
                           public  com.vindicia.soap.v1_1.selecttypes.BillingIntervalType getBillingFrequency(){
                               return localBillingFrequency;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BillingFrequency
                               */
                               public void setBillingFrequency(com.vindicia.soap.v1_1.selecttypes.BillingIntervalType param){
                            localBillingFrequencyTracker = param != null;
                                   
                                            this.localBillingFrequency=param;
                                    

                               }
                            

                        /**
                        * field for PreviousBillingDate
                        */

                        
                                    protected java.util.Calendar localPreviousBillingDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPreviousBillingDateTracker = false ;

                           public boolean isPreviousBillingDateSpecified(){
                               return localPreviousBillingDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getPreviousBillingDate(){
                               return localPreviousBillingDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PreviousBillingDate
                               */
                               public void setPreviousBillingDate(java.util.Calendar param){
                            localPreviousBillingDateTracker = param != null;
                                   
                                            this.localPreviousBillingDate=param;
                                    

                               }
                            

                        /**
                        * field for PreviousBillingCount
                        */

                        
                                    protected int localPreviousBillingCount ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPreviousBillingCountTracker = false ;

                           public boolean isPreviousBillingCountSpecified(){
                               return localPreviousBillingCountTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getPreviousBillingCount(){
                               return localPreviousBillingCount;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PreviousBillingCount
                               */
                               public void setPreviousBillingCount(int param){
                            
                                       // setting primitive attribute tracker to true
                                       localPreviousBillingCountTracker =
                                       param != java.lang.Integer.MIN_VALUE;
                                   
                                            this.localPreviousBillingCount=param;
                                    

                               }
                            

                        /**
                        * field for CustomerId
                        */

                        
                                    protected java.lang.String localCustomerId ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerId(){
                               return localCustomerId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerId
                               */
                               public void setCustomerId(java.lang.String param){
                            
                                            this.localCustomerId=param;
                                    

                               }
                            

                        /**
                        * field for PaymentMethodId
                        */

                        
                                    protected java.lang.String localPaymentMethodId ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPaymentMethodId(){
                               return localPaymentMethodId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PaymentMethodId
                               */
                               public void setPaymentMethodId(java.lang.String param){
                            
                                            this.localPaymentMethodId=param;
                                    

                               }
                            

                        /**
                        * field for PaymentMethodIsTokenized
                        */

                        
                                    protected boolean localPaymentMethodIsTokenized ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getPaymentMethodIsTokenized(){
                               return localPaymentMethodIsTokenized;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PaymentMethodIsTokenized
                               */
                               public void setPaymentMethodIsTokenized(boolean param){
                            
                                            this.localPaymentMethodIsTokenized=param;
                                    

                               }
                            

                        /**
                        * field for CreditCardAccount
                        */

                        
                                    protected java.lang.String localCreditCardAccount ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreditCardAccountTracker = false ;

                           public boolean isCreditCardAccountSpecified(){
                               return localCreditCardAccountTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCreditCardAccount(){
                               return localCreditCardAccount;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CreditCardAccount
                               */
                               public void setCreditCardAccount(java.lang.String param){
                            localCreditCardAccountTracker = param != null;
                                   
                                            this.localCreditCardAccount=param;
                                    

                               }
                            

                        /**
                        * field for CreditCardAccountHash
                        */

                        
                                    protected java.lang.String localCreditCardAccountHash ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreditCardAccountHashTracker = false ;

                           public boolean isCreditCardAccountHashSpecified(){
                               return localCreditCardAccountHashTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCreditCardAccountHash(){
                               return localCreditCardAccountHash;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CreditCardAccountHash
                               */
                               public void setCreditCardAccountHash(java.lang.String param){
                            localCreditCardAccountHashTracker = param != null;
                                   
                                            this.localCreditCardAccountHash=param;
                                    

                               }
                            

                        /**
                        * field for CreditCardExpirationDate
                        */

                        
                                    protected java.lang.String localCreditCardExpirationDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreditCardExpirationDateTracker = false ;

                           public boolean isCreditCardExpirationDateSpecified(){
                               return localCreditCardExpirationDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCreditCardExpirationDate(){
                               return localCreditCardExpirationDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CreditCardExpirationDate
                               */
                               public void setCreditCardExpirationDate(java.lang.String param){
                            localCreditCardExpirationDateTracker = param != null;
                                   
                                            this.localCreditCardExpirationDate=param;
                                    

                               }
                            

                        /**
                        * field for AccountHolderName
                        */

                        
                                    protected java.lang.String localAccountHolderName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAccountHolderNameTracker = false ;

                           public boolean isAccountHolderNameSpecified(){
                               return localAccountHolderNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAccountHolderName(){
                               return localAccountHolderName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AccountHolderName
                               */
                               public void setAccountHolderName(java.lang.String param){
                            localAccountHolderNameTracker = param != null;
                                   
                                            this.localAccountHolderName=param;
                                    

                               }
                            

                        /**
                        * field for BillingAddressLine1
                        */

                        
                                    protected java.lang.String localBillingAddressLine1 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBillingAddressLine1Tracker = false ;

                           public boolean isBillingAddressLine1Specified(){
                               return localBillingAddressLine1Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBillingAddressLine1(){
                               return localBillingAddressLine1;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BillingAddressLine1
                               */
                               public void setBillingAddressLine1(java.lang.String param){
                            localBillingAddressLine1Tracker = param != null;
                                   
                                            this.localBillingAddressLine1=param;
                                    

                               }
                            

                        /**
                        * field for BillingAddressLine2
                        */

                        
                                    protected java.lang.String localBillingAddressLine2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBillingAddressLine2Tracker = false ;

                           public boolean isBillingAddressLine2Specified(){
                               return localBillingAddressLine2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBillingAddressLine2(){
                               return localBillingAddressLine2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BillingAddressLine2
                               */
                               public void setBillingAddressLine2(java.lang.String param){
                            localBillingAddressLine2Tracker = param != null;
                                   
                                            this.localBillingAddressLine2=param;
                                    

                               }
                            

                        /**
                        * field for BillingAddressLine3
                        */

                        
                                    protected java.lang.String localBillingAddressLine3 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBillingAddressLine3Tracker = false ;

                           public boolean isBillingAddressLine3Specified(){
                               return localBillingAddressLine3Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBillingAddressLine3(){
                               return localBillingAddressLine3;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BillingAddressLine3
                               */
                               public void setBillingAddressLine3(java.lang.String param){
                            localBillingAddressLine3Tracker = param != null;
                                   
                                            this.localBillingAddressLine3=param;
                                    

                               }
                            

                        /**
                        * field for BillingAddressCity
                        */

                        
                                    protected java.lang.String localBillingAddressCity ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBillingAddressCityTracker = false ;

                           public boolean isBillingAddressCitySpecified(){
                               return localBillingAddressCityTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBillingAddressCity(){
                               return localBillingAddressCity;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BillingAddressCity
                               */
                               public void setBillingAddressCity(java.lang.String param){
                            localBillingAddressCityTracker = param != null;
                                   
                                            this.localBillingAddressCity=param;
                                    

                               }
                            

                        /**
                        * field for BillingAddressCounty
                        */

                        
                                    protected java.lang.String localBillingAddressCounty ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBillingAddressCountyTracker = false ;

                           public boolean isBillingAddressCountySpecified(){
                               return localBillingAddressCountyTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBillingAddressCounty(){
                               return localBillingAddressCounty;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BillingAddressCounty
                               */
                               public void setBillingAddressCounty(java.lang.String param){
                            localBillingAddressCountyTracker = param != null;
                                   
                                            this.localBillingAddressCounty=param;
                                    

                               }
                            

                        /**
                        * field for BillingAddressDistrict
                        */

                        
                                    protected java.lang.String localBillingAddressDistrict ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBillingAddressDistrictTracker = false ;

                           public boolean isBillingAddressDistrictSpecified(){
                               return localBillingAddressDistrictTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBillingAddressDistrict(){
                               return localBillingAddressDistrict;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BillingAddressDistrict
                               */
                               public void setBillingAddressDistrict(java.lang.String param){
                            localBillingAddressDistrictTracker = param != null;
                                   
                                            this.localBillingAddressDistrict=param;
                                    

                               }
                            

                        /**
                        * field for BillingAddressPostalCode
                        */

                        
                                    protected java.lang.String localBillingAddressPostalCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBillingAddressPostalCodeTracker = false ;

                           public boolean isBillingAddressPostalCodeSpecified(){
                               return localBillingAddressPostalCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBillingAddressPostalCode(){
                               return localBillingAddressPostalCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BillingAddressPostalCode
                               */
                               public void setBillingAddressPostalCode(java.lang.String param){
                            localBillingAddressPostalCodeTracker = param != null;
                                   
                                            this.localBillingAddressPostalCode=param;
                                    

                               }
                            

                        /**
                        * field for BillingAddressCountry
                        */

                        
                                    protected java.lang.String localBillingAddressCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBillingAddressCountryTracker = false ;

                           public boolean isBillingAddressCountrySpecified(){
                               return localBillingAddressCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBillingAddressCountry(){
                               return localBillingAddressCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BillingAddressCountry
                               */
                               public void setBillingAddressCountry(java.lang.String param){
                            localBillingAddressCountryTracker = param != null;
                                   
                                            this.localBillingAddressCountry=param;
                                    

                               }
                            

                        /**
                        * field for NameValues
                        * This was an Array!
                        */

                        
                                    protected com.vindicia.soap.v1_1.selecttypes.NameValuePair[] localNameValues ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNameValuesTracker = false ;

                           public boolean isNameValuesSpecified(){
                               return localNameValuesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vindicia.soap.v1_1.selecttypes.NameValuePair[]
                           */
                           public  com.vindicia.soap.v1_1.selecttypes.NameValuePair[] getNameValues(){
                               return localNameValues;
                           }

                           
                        


                               
                              /**
                               * validate the array for NameValues
                               */
                              protected void validateNameValues(com.vindicia.soap.v1_1.selecttypes.NameValuePair[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param NameValues
                              */
                              public void setNameValues(com.vindicia.soap.v1_1.selecttypes.NameValuePair[] param){
                              
                                   validateNameValues(param);

                               localNameValuesTracker = param != null;
                                      
                                      this.localNameValues=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param com.vindicia.soap.v1_1.selecttypes.NameValuePair
                             */
                             public void addNameValues(com.vindicia.soap.v1_1.selecttypes.NameValuePair param){
                                   if (localNameValues == null){
                                   localNameValues = new com.vindicia.soap.v1_1.selecttypes.NameValuePair[]{};
                                   }

                            
                                 //update the setting tracker
                                localNameValuesTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localNameValues);
                               list.add(param);
                               this.localNameValues =
                             (com.vindicia.soap.v1_1.selecttypes.NameValuePair[])list.toArray(
                            new com.vindicia.soap.v1_1.selecttypes.NameValuePair[list.size()]);

                             }
                             

                        /**
                        * field for AffiliateId
                        */

                        
                                    protected java.lang.String localAffiliateId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAffiliateIdTracker = false ;

                           public boolean isAffiliateIdSpecified(){
                               return localAffiliateIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAffiliateId(){
                               return localAffiliateId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AffiliateId
                               */
                               public void setAffiliateId(java.lang.String param){
                            localAffiliateIdTracker = param != null;
                                   
                                            this.localAffiliateId=param;
                                    

                               }
                            

                        /**
                        * field for AffiliateSubId
                        */

                        
                                    protected java.lang.String localAffiliateSubId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAffiliateSubIdTracker = false ;

                           public boolean isAffiliateSubIdSpecified(){
                               return localAffiliateSubIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAffiliateSubId(){
                               return localAffiliateSubId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AffiliateSubId
                               */
                               public void setAffiliateSubId(java.lang.String param){
                            localAffiliateSubIdTracker = param != null;
                                   
                                            this.localAffiliateSubId=param;
                                    

                               }
                            

                        /**
                        * field for BillingStatementIdentifier
                        */

                        
                                    protected java.lang.String localBillingStatementIdentifier ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBillingStatementIdentifierTracker = false ;

                           public boolean isBillingStatementIdentifierSpecified(){
                               return localBillingStatementIdentifierTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBillingStatementIdentifier(){
                               return localBillingStatementIdentifier;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BillingStatementIdentifier
                               */
                               public void setBillingStatementIdentifier(java.lang.String param){
                            localBillingStatementIdentifierTracker = param != null;
                                   
                                            this.localBillingStatementIdentifier=param;
                                    

                               }
                            

                        /**
                        * field for AuthCode
                        */

                        
                                    protected java.lang.String localAuthCode ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAuthCode(){
                               return localAuthCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AuthCode
                               */
                               public void setAuthCode(java.lang.String param){
                            
                                            this.localAuthCode=param;
                                    

                               }
                            

                        /**
                        * field for AvsCode
                        */

                        
                                    protected java.lang.String localAvsCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAvsCodeTracker = false ;

                           public boolean isAvsCodeSpecified(){
                               return localAvsCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAvsCode(){
                               return localAvsCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AvsCode
                               */
                               public void setAvsCode(java.lang.String param){
                            localAvsCodeTracker = param != null;
                                   
                                            this.localAvsCode=param;
                                    

                               }
                            

                        /**
                        * field for CvnCode
                        */

                        
                                    protected java.lang.String localCvnCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCvnCodeTracker = false ;

                           public boolean isCvnCodeSpecified(){
                               return localCvnCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCvnCode(){
                               return localCvnCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CvnCode
                               */
                               public void setCvnCode(java.lang.String param){
                            localCvnCodeTracker = param != null;
                                   
                                            this.localCvnCode=param;
                                    

                               }
                            

                        /**
                        * field for CreditCardAccountUpdated
                        */

                        
                                    protected boolean localCreditCardAccountUpdated ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreditCardAccountUpdatedTracker = false ;

                           public boolean isCreditCardAccountUpdatedSpecified(){
                               return localCreditCardAccountUpdatedTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getCreditCardAccountUpdated(){
                               return localCreditCardAccountUpdated;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CreditCardAccountUpdated
                               */
                               public void setCreditCardAccountUpdated(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localCreditCardAccountUpdatedTracker =
                                       true;
                                   
                                            this.localCreditCardAccountUpdated=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://soap.vindicia.com/v1_1/SelectTypes");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":Transaction",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "Transaction",
                           xmlWriter);
                   }

               
                   }
                if (localVIDTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "VID", xmlWriter);
                             

                                          if (localVID==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("VID cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localVID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "";
                                    writeStartElement(null, namespace, "timestamp", xmlWriter);
                             

                                          if (localTimestamp==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("timestamp cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTimestamp));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "";
                                    writeStartElement(null, namespace, "amount", xmlWriter);
                             

                                          if (localAmount==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("amount cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAmount));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localCurrencyTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "currency", xmlWriter);
                             

                                          if (localCurrency==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("currency cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCurrency);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                            if (localStatus==null){
                                                 throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
                                            }
                                           localStatus.serialize(new javax.xml.namespace.QName("","status"),
                                               xmlWriter);
                                        
                                    namespace = "";
                                    writeStartElement(null, namespace, "divisionNumber", xmlWriter);
                             

                                          if (localDivisionNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("divisionNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDivisionNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "";
                                    writeStartElement(null, namespace, "merchantTransactionId", xmlWriter);
                             

                                          if (localMerchantTransactionId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("merchantTransactionId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMerchantTransactionId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localSelectTransactionIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "selectTransactionId", xmlWriter);
                             

                                          if (localSelectTransactionId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("selectTransactionId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSelectTransactionId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSelectRefundIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "selectRefundId", xmlWriter);
                             

                                          if (localSelectRefundId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("selectRefundId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSelectRefundId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "";
                                    writeStartElement(null, namespace, "subscriptionId", xmlWriter);
                             

                                          if (localSubscriptionId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("subscriptionId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSubscriptionId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localSubscriptionStartDateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "subscriptionStartDate", xmlWriter);
                             

                                          if (localSubscriptionStartDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("subscriptionStartDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSubscriptionStartDate));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBillingFrequencyTracker){
                                            if (localBillingFrequency==null){
                                                 throw new org.apache.axis2.databinding.ADBException("billingFrequency cannot be null!!");
                                            }
                                           localBillingFrequency.serialize(new javax.xml.namespace.QName("","billingFrequency"),
                                               xmlWriter);
                                        } if (localPreviousBillingDateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "previousBillingDate", xmlWriter);
                             

                                          if (localPreviousBillingDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("previousBillingDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPreviousBillingDate));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPreviousBillingCountTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "previousBillingCount", xmlWriter);
                             
                                               if (localPreviousBillingCount==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("previousBillingCount cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPreviousBillingCount));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "";
                                    writeStartElement(null, namespace, "customerId", xmlWriter);
                             

                                          if (localCustomerId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "";
                                    writeStartElement(null, namespace, "paymentMethodId", xmlWriter);
                             

                                          if (localPaymentMethodId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("paymentMethodId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPaymentMethodId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "";
                                    writeStartElement(null, namespace, "paymentMethodIsTokenized", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("paymentMethodIsTokenized cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPaymentMethodIsTokenized));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                              if (localCreditCardAccountTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "creditCardAccount", xmlWriter);
                             

                                          if (localCreditCardAccount==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("creditCardAccount cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCreditCardAccount);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCreditCardAccountHashTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "creditCardAccountHash", xmlWriter);
                             

                                          if (localCreditCardAccountHash==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("creditCardAccountHash cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCreditCardAccountHash);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCreditCardExpirationDateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "creditCardExpirationDate", xmlWriter);
                             

                                          if (localCreditCardExpirationDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("creditCardExpirationDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCreditCardExpirationDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAccountHolderNameTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "accountHolderName", xmlWriter);
                             

                                          if (localAccountHolderName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("accountHolderName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAccountHolderName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBillingAddressLine1Tracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "billingAddressLine1", xmlWriter);
                             

                                          if (localBillingAddressLine1==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("billingAddressLine1 cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBillingAddressLine1);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBillingAddressLine2Tracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "billingAddressLine2", xmlWriter);
                             

                                          if (localBillingAddressLine2==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("billingAddressLine2 cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBillingAddressLine2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBillingAddressLine3Tracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "billingAddressLine3", xmlWriter);
                             

                                          if (localBillingAddressLine3==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("billingAddressLine3 cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBillingAddressLine3);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBillingAddressCityTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "billingAddressCity", xmlWriter);
                             

                                          if (localBillingAddressCity==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("billingAddressCity cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBillingAddressCity);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBillingAddressCountyTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "billingAddressCounty", xmlWriter);
                             

                                          if (localBillingAddressCounty==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("billingAddressCounty cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBillingAddressCounty);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBillingAddressDistrictTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "billingAddressDistrict", xmlWriter);
                             

                                          if (localBillingAddressDistrict==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("billingAddressDistrict cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBillingAddressDistrict);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBillingAddressPostalCodeTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "billingAddressPostalCode", xmlWriter);
                             

                                          if (localBillingAddressPostalCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("billingAddressPostalCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBillingAddressPostalCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBillingAddressCountryTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "billingAddressCountry", xmlWriter);
                             

                                          if (localBillingAddressCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("billingAddressCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBillingAddressCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNameValuesTracker){
                                       if (localNameValues!=null){
                                            for (int i = 0;i < localNameValues.length;i++){
                                                if (localNameValues[i] != null){
                                                 localNameValues[i].serialize(new javax.xml.namespace.QName("","nameValues"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("nameValues cannot be null!!");
                                        
                                    }
                                 } if (localAffiliateIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "affiliateId", xmlWriter);
                             

                                          if (localAffiliateId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("affiliateId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAffiliateId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAffiliateSubIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "affiliateSubId", xmlWriter);
                             

                                          if (localAffiliateSubId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("affiliateSubId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAffiliateSubId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBillingStatementIdentifierTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "billingStatementIdentifier", xmlWriter);
                             

                                          if (localBillingStatementIdentifier==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("billingStatementIdentifier cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBillingStatementIdentifier);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "";
                                    writeStartElement(null, namespace, "authCode", xmlWriter);
                             

                                          if (localAuthCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("authCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAuthCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localAvsCodeTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "avsCode", xmlWriter);
                             

                                          if (localAvsCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("avsCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAvsCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCvnCodeTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "cvnCode", xmlWriter);
                             

                                          if (localCvnCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("cvnCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCvnCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCreditCardAccountUpdatedTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "creditCardAccountUpdated", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("creditCardAccountUpdated cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreditCardAccountUpdated));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://soap.vindicia.com/v1_1/SelectTypes")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localVIDTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "VID"));
                                 
                                        if (localVID != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVID));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("VID cannot be null!!");
                                        }
                                    }
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "timestamp"));
                                 
                                        if (localTimestamp != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTimestamp));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("timestamp cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "amount"));
                                 
                                        if (localAmount != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAmount));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("amount cannot be null!!");
                                        }
                                     if (localCurrencyTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "currency"));
                                 
                                        if (localCurrency != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurrency));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("currency cannot be null!!");
                                        }
                                    }
                            elementList.add(new javax.xml.namespace.QName("",
                                                                      "status"));
                            
                            
                                    if (localStatus==null){
                                         throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
                                    }
                                    elementList.add(localStatus);
                                
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "divisionNumber"));
                                 
                                        if (localDivisionNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDivisionNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("divisionNumber cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "merchantTransactionId"));
                                 
                                        if (localMerchantTransactionId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMerchantTransactionId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("merchantTransactionId cannot be null!!");
                                        }
                                     if (localSelectTransactionIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "selectTransactionId"));
                                 
                                        if (localSelectTransactionId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSelectTransactionId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("selectTransactionId cannot be null!!");
                                        }
                                    } if (localSelectRefundIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "selectRefundId"));
                                 
                                        if (localSelectRefundId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSelectRefundId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("selectRefundId cannot be null!!");
                                        }
                                    }
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "subscriptionId"));
                                 
                                        if (localSubscriptionId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSubscriptionId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("subscriptionId cannot be null!!");
                                        }
                                     if (localSubscriptionStartDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "subscriptionStartDate"));
                                 
                                        if (localSubscriptionStartDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSubscriptionStartDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("subscriptionStartDate cannot be null!!");
                                        }
                                    } if (localBillingFrequencyTracker){
                            elementList.add(new javax.xml.namespace.QName("",
                                                                      "billingFrequency"));
                            
                            
                                    if (localBillingFrequency==null){
                                         throw new org.apache.axis2.databinding.ADBException("billingFrequency cannot be null!!");
                                    }
                                    elementList.add(localBillingFrequency);
                                } if (localPreviousBillingDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "previousBillingDate"));
                                 
                                        if (localPreviousBillingDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPreviousBillingDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("previousBillingDate cannot be null!!");
                                        }
                                    } if (localPreviousBillingCountTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "previousBillingCount"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPreviousBillingCount));
                            }
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "customerId"));
                                 
                                        if (localCustomerId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "paymentMethodId"));
                                 
                                        if (localPaymentMethodId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPaymentMethodId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("paymentMethodId cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "paymentMethodIsTokenized"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPaymentMethodIsTokenized));
                             if (localCreditCardAccountTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "creditCardAccount"));
                                 
                                        if (localCreditCardAccount != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreditCardAccount));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("creditCardAccount cannot be null!!");
                                        }
                                    } if (localCreditCardAccountHashTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "creditCardAccountHash"));
                                 
                                        if (localCreditCardAccountHash != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreditCardAccountHash));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("creditCardAccountHash cannot be null!!");
                                        }
                                    } if (localCreditCardExpirationDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "creditCardExpirationDate"));
                                 
                                        if (localCreditCardExpirationDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreditCardExpirationDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("creditCardExpirationDate cannot be null!!");
                                        }
                                    } if (localAccountHolderNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "accountHolderName"));
                                 
                                        if (localAccountHolderName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccountHolderName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("accountHolderName cannot be null!!");
                                        }
                                    } if (localBillingAddressLine1Tracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "billingAddressLine1"));
                                 
                                        if (localBillingAddressLine1 != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBillingAddressLine1));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("billingAddressLine1 cannot be null!!");
                                        }
                                    } if (localBillingAddressLine2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "billingAddressLine2"));
                                 
                                        if (localBillingAddressLine2 != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBillingAddressLine2));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("billingAddressLine2 cannot be null!!");
                                        }
                                    } if (localBillingAddressLine3Tracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "billingAddressLine3"));
                                 
                                        if (localBillingAddressLine3 != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBillingAddressLine3));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("billingAddressLine3 cannot be null!!");
                                        }
                                    } if (localBillingAddressCityTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "billingAddressCity"));
                                 
                                        if (localBillingAddressCity != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBillingAddressCity));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("billingAddressCity cannot be null!!");
                                        }
                                    } if (localBillingAddressCountyTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "billingAddressCounty"));
                                 
                                        if (localBillingAddressCounty != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBillingAddressCounty));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("billingAddressCounty cannot be null!!");
                                        }
                                    } if (localBillingAddressDistrictTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "billingAddressDistrict"));
                                 
                                        if (localBillingAddressDistrict != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBillingAddressDistrict));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("billingAddressDistrict cannot be null!!");
                                        }
                                    } if (localBillingAddressPostalCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "billingAddressPostalCode"));
                                 
                                        if (localBillingAddressPostalCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBillingAddressPostalCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("billingAddressPostalCode cannot be null!!");
                                        }
                                    } if (localBillingAddressCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "billingAddressCountry"));
                                 
                                        if (localBillingAddressCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBillingAddressCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("billingAddressCountry cannot be null!!");
                                        }
                                    } if (localNameValuesTracker){
                             if (localNameValues!=null) {
                                 for (int i = 0;i < localNameValues.length;i++){

                                    if (localNameValues[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("",
                                                                          "nameValues"));
                                         elementList.add(localNameValues[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("nameValues cannot be null!!");
                                    
                             }

                        } if (localAffiliateIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "affiliateId"));
                                 
                                        if (localAffiliateId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAffiliateId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("affiliateId cannot be null!!");
                                        }
                                    } if (localAffiliateSubIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "affiliateSubId"));
                                 
                                        if (localAffiliateSubId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAffiliateSubId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("affiliateSubId cannot be null!!");
                                        }
                                    } if (localBillingStatementIdentifierTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "billingStatementIdentifier"));
                                 
                                        if (localBillingStatementIdentifier != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBillingStatementIdentifier));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("billingStatementIdentifier cannot be null!!");
                                        }
                                    }
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "authCode"));
                                 
                                        if (localAuthCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("authCode cannot be null!!");
                                        }
                                     if (localAvsCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "avsCode"));
                                 
                                        if (localAvsCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAvsCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("avsCode cannot be null!!");
                                        }
                                    } if (localCvnCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "cvnCode"));
                                 
                                        if (localCvnCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCvnCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("cvnCode cannot be null!!");
                                        }
                                    } if (localCreditCardAccountUpdatedTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "creditCardAccountUpdated"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreditCardAccountUpdated));
                            }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static Transaction parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            Transaction object =
                new Transaction();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"Transaction".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (Transaction)com.vindicia.soap.v1_1.select.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list30 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","VID").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"VID" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setVID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","timestamp").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"timestamp" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTimestamp(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","amount").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"amount" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAmount(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","currency").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"currency" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCurrency(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","status").equals(reader.getName())){
                                
                                                object.setStatus(com.vindicia.soap.v1_1.selecttypes.TransactionStatusType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","divisionNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"divisionNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDivisionNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","merchantTransactionId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"merchantTransactionId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMerchantTransactionId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","selectTransactionId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"selectTransactionId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSelectTransactionId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","selectRefundId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"selectRefundId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSelectRefundId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","subscriptionId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"subscriptionId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSubscriptionId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","subscriptionStartDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"subscriptionStartDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSubscriptionStartDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","billingFrequency").equals(reader.getName())){
                                
                                                object.setBillingFrequency(com.vindicia.soap.v1_1.selecttypes.BillingIntervalType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","previousBillingDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"previousBillingDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPreviousBillingDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","previousBillingCount").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"previousBillingCount" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPreviousBillingCount(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setPreviousBillingCount(java.lang.Integer.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","customerId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"customerId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","paymentMethodId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"paymentMethodId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPaymentMethodId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","paymentMethodIsTokenized").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"paymentMethodIsTokenized" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPaymentMethodIsTokenized(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","creditCardAccount").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"creditCardAccount" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCreditCardAccount(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","creditCardAccountHash").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"creditCardAccountHash" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCreditCardAccountHash(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","creditCardExpirationDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"creditCardExpirationDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCreditCardExpirationDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","accountHolderName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"accountHolderName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAccountHolderName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","billingAddressLine1").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"billingAddressLine1" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBillingAddressLine1(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","billingAddressLine2").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"billingAddressLine2" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBillingAddressLine2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","billingAddressLine3").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"billingAddressLine3" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBillingAddressLine3(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","billingAddressCity").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"billingAddressCity" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBillingAddressCity(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","billingAddressCounty").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"billingAddressCounty" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBillingAddressCounty(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","billingAddressDistrict").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"billingAddressDistrict" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBillingAddressDistrict(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","billingAddressPostalCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"billingAddressPostalCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBillingAddressPostalCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","billingAddressCountry").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"billingAddressCountry" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBillingAddressCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","nameValues").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list30.add(com.vindicia.soap.v1_1.selecttypes.NameValuePair.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone30 = false;
                                                        while(!loopDone30){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone30 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("","nameValues").equals(reader.getName())){
                                                                    list30.add(com.vindicia.soap.v1_1.selecttypes.NameValuePair.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone30 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setNameValues((com.vindicia.soap.v1_1.selecttypes.NameValuePair[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                com.vindicia.soap.v1_1.selecttypes.NameValuePair.class,
                                                                list30));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","affiliateId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"affiliateId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAffiliateId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","affiliateSubId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"affiliateSubId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAffiliateSubId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","billingStatementIdentifier").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"billingStatementIdentifier" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBillingStatementIdentifier(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","authCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"authCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuthCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","avsCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"avsCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAvsCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","cvnCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"cvnCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCvnCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","creditCardAccountUpdated").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"creditCardAccountUpdated" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCreditCardAccountUpdated(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
