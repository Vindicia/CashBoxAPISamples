
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

        
            package com.vindicia.soap.v1_1.select;
        
            /**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://soap.vindicia.com/v1_1/SelectTypes".equals(namespaceURI) &&
                  "ReturnCode".equals(typeName)){
                   
                            return  com.vindicia.soap.v1_1.selecttypes.ReturnCode.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://soap.vindicia.com/v1_1/SelectTypes".equals(namespaceURI) &&
                  "TransactionStatusType".equals(typeName)){
                   
                            return  com.vindicia.soap.v1_1.selecttypes.TransactionStatusType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://soap.vindicia.com/v1_1/SelectTypes".equals(namespaceURI) &&
                  "Transaction".equals(typeName)){
                   
                            return  com.vindicia.soap.v1_1.selecttypes.Transaction.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://soap.vindicia.com/v1_1/SelectTypes".equals(namespaceURI) &&
                  "ChargebackStatus".equals(typeName)){
                   
                            return  com.vindicia.soap.v1_1.selecttypes.ChargebackStatus.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://soap.vindicia.com/v1_1/SelectTypes".equals(namespaceURI) &&
                  "Authentication".equals(typeName)){
                   
                            return  com.vindicia.soap.v1_1.selecttypes.Authentication.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://soap.vindicia.com/v1_1/SelectTypes".equals(namespaceURI) &&
                  "NameValuePair".equals(typeName)){
                   
                            return  com.vindicia.soap.v1_1.selecttypes.NameValuePair.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://soap.vindicia.com/v1_1/SelectTypes".equals(namespaceURI) &&
                  "TransactionValidationResponse".equals(typeName)){
                   
                            return  com.vindicia.soap.v1_1.selecttypes.TransactionValidationResponse.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://soap.vindicia.com/v1_1/SelectTypes".equals(namespaceURI) &&
                  "BillingIntervalType".equals(typeName)){
                   
                            return  com.vindicia.soap.v1_1.selecttypes.BillingIntervalType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://soap.vindicia.com/v1_1/SelectTypes".equals(namespaceURI) &&
                  "Return".equals(typeName)){
                   
                            return  com.vindicia.soap.v1_1.selecttypes.Return.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://soap.vindicia.com/v1_1/SelectTypes".equals(namespaceURI) &&
                  "Chargeback".equals(typeName)){
                   
                            return  com.vindicia.soap.v1_1.selecttypes.Chargeback.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    
