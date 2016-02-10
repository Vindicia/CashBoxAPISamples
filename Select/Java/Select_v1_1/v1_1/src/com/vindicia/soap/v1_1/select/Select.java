

/**
 * Select.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.vindicia.soap.v1_1.select;

    /*
     *  Select java interface
     */

    public interface Select {
          

        /**
          * Auto generated method signature
          * 
                    * @param reportTransactions
                
         */

         
                     public com.vindicia.soap.v1_1.select.ReportTransactionsResponse reportTransactions(

                        com.vindicia.soap.v1_1.select.ReportTransactions reportTransactions)
                        throws java.rmi.RemoteException
             ;

        

        /**
          * Auto generated method signature
          * 
                    * @param fetchChargebacks
                
         */

         
                     public com.vindicia.soap.v1_1.select.FetchChargebacksResponse fetchChargebacks(

                        com.vindicia.soap.v1_1.select.FetchChargebacks fetchChargebacks)
                        throws java.rmi.RemoteException
             ;

        

        /**
          * Auto generated method signature
          * 
                    * @param fetchBillingResults
                
         */

         
                     public com.vindicia.soap.v1_1.select.FetchBillingResultsResponse fetchBillingResults(

                        com.vindicia.soap.v1_1.select.FetchBillingResults fetchBillingResults)
                        throws java.rmi.RemoteException
             ;

        

        /**
          * Auto generated method signature
          * 
                    * @param fetchByMerchantTransactionId
                
         */

         
                     public com.vindicia.soap.v1_1.select.FetchByMerchantTransactionIdResponse fetchByMerchantTransactionId(

                        com.vindicia.soap.v1_1.select.FetchByMerchantTransactionId fetchByMerchantTransactionId)
                        throws java.rmi.RemoteException
             ;

        

        /**
          * Auto generated method signature
          * 
                    * @param billTransactions
                
         */

         
                     public com.vindicia.soap.v1_1.select.BillTransactionsResponse billTransactions(

                        com.vindicia.soap.v1_1.select.BillTransactions billTransactions)
                        throws java.rmi.RemoteException
             ;

        

        /**
          * Auto generated method signature
          * 
                    * @param refundTransactions
                
         */

         
                     public com.vindicia.soap.v1_1.select.RefundTransactionsResponse refundTransactions(

                        com.vindicia.soap.v1_1.select.RefundTransactions refundTransactions)
                        throws java.rmi.RemoteException
             ;

        

        
       //
       }
    
