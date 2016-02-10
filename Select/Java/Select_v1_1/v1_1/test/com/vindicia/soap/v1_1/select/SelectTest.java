

/**
 * SelectTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package com.vindicia.soap.v1_1.select;

    /*
     *  SelectTest Junit test case
    */

    public class SelectTest extends junit.framework.TestCase{

     
        /**
         * Auto generated test method
         */
        public  void testreportTransactions() throws java.lang.Exception{

        com.vindicia.soap.v1_1.select.SelectStub stub =
                    new com.vindicia.soap.v1_1.select.SelectStub();//the default implementation should point to the right endpoint

           com.vindicia.soap.v1_1.select.ReportTransactions reportTransactions24=
                                                        (com.vindicia.soap.v1_1.select.ReportTransactions)getTestObject(com.vindicia.soap.v1_1.select.ReportTransactions.class);
                    // TODO : Fill in the reportTransactions24 here
                
                        assertNotNull(stub.reportTransactions(
                        reportTransactions24));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testfetchChargebacks() throws java.lang.Exception{

        com.vindicia.soap.v1_1.select.SelectStub stub =
                    new com.vindicia.soap.v1_1.select.SelectStub();//the default implementation should point to the right endpoint

           com.vindicia.soap.v1_1.select.FetchChargebacks fetchChargebacks26=
                                                        (com.vindicia.soap.v1_1.select.FetchChargebacks)getTestObject(com.vindicia.soap.v1_1.select.FetchChargebacks.class);
                    // TODO : Fill in the fetchChargebacks26 here
                
                        assertNotNull(stub.fetchChargebacks(
                        fetchChargebacks26));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testfetchBillingResults() throws java.lang.Exception{

        com.vindicia.soap.v1_1.select.SelectStub stub =
                    new com.vindicia.soap.v1_1.select.SelectStub();//the default implementation should point to the right endpoint

           com.vindicia.soap.v1_1.select.FetchBillingResults fetchBillingResults28=
                                                        (com.vindicia.soap.v1_1.select.FetchBillingResults)getTestObject(com.vindicia.soap.v1_1.select.FetchBillingResults.class);
                    // TODO : Fill in the fetchBillingResults28 here
                
                        assertNotNull(stub.fetchBillingResults(
                        fetchBillingResults28));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testfetchByMerchantTransactionId() throws java.lang.Exception{

        com.vindicia.soap.v1_1.select.SelectStub stub =
                    new com.vindicia.soap.v1_1.select.SelectStub();//the default implementation should point to the right endpoint

           com.vindicia.soap.v1_1.select.FetchByMerchantTransactionId fetchByMerchantTransactionId30=
                                                        (com.vindicia.soap.v1_1.select.FetchByMerchantTransactionId)getTestObject(com.vindicia.soap.v1_1.select.FetchByMerchantTransactionId.class);
                    // TODO : Fill in the fetchByMerchantTransactionId30 here
                
                        assertNotNull(stub.fetchByMerchantTransactionId(
                        fetchByMerchantTransactionId30));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testbillTransactions() throws java.lang.Exception{

        com.vindicia.soap.v1_1.select.SelectStub stub =
                    new com.vindicia.soap.v1_1.select.SelectStub();//the default implementation should point to the right endpoint

           com.vindicia.soap.v1_1.select.BillTransactions billTransactions32=
                                                        (com.vindicia.soap.v1_1.select.BillTransactions)getTestObject(com.vindicia.soap.v1_1.select.BillTransactions.class);
                    // TODO : Fill in the billTransactions32 here
                
                        assertNotNull(stub.billTransactions(
                        billTransactions32));
                  



        }
        
        /**
         * Auto generated test method
         */
        public  void testrefundTransactions() throws java.lang.Exception{

        com.vindicia.soap.v1_1.select.SelectStub stub =
                    new com.vindicia.soap.v1_1.select.SelectStub();//the default implementation should point to the right endpoint

           com.vindicia.soap.v1_1.select.RefundTransactions refundTransactions34=
                                                        (com.vindicia.soap.v1_1.select.RefundTransactions)getTestObject(com.vindicia.soap.v1_1.select.RefundTransactions.class);
                    // TODO : Fill in the refundTransactions34 here
                
                        assertNotNull(stub.refundTransactions(
                        refundTransactions34));
                  



        }
        
        //Create an ADBBean and provide it as the test object
        public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception{
           return (org.apache.axis2.databinding.ADBBean) type.newInstance();
        }

        
        

    }
    
