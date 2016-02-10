
/**
 * Chargeback.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.vindicia.soap.v1_1.selecttypes;
            

            /**
            *  Chargeback bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class Chargeback
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = Chargeback
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
                        * field for PresentmentAmount
                        */

                        
                                    protected java.math.BigDecimal localPresentmentAmount ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPresentmentAmountTracker = false ;

                           public boolean isPresentmentAmountSpecified(){
                               return localPresentmentAmountTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.math.BigDecimal
                           */
                           public  java.math.BigDecimal getPresentmentAmount(){
                               return localPresentmentAmount;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PresentmentAmount
                               */
                               public void setPresentmentAmount(java.math.BigDecimal param){
                            localPresentmentAmountTracker = param != null;
                                   
                                            this.localPresentmentAmount=param;
                                    

                               }
                            

                        /**
                        * field for PresentmentCurrency
                        */

                        
                                    protected java.lang.String localPresentmentCurrency ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPresentmentCurrencyTracker = false ;

                           public boolean isPresentmentCurrencySpecified(){
                               return localPresentmentCurrencyTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPresentmentCurrency(){
                               return localPresentmentCurrency;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PresentmentCurrency
                               */
                               public void setPresentmentCurrency(java.lang.String param){
                            localPresentmentCurrencyTracker = param != null;
                                   
                                            this.localPresentmentCurrency=param;
                                    

                               }
                            

                        /**
                        * field for DivisionNumber
                        */

                        
                                    protected java.lang.String localDivisionNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDivisionNumberTracker = false ;

                           public boolean isDivisionNumberSpecified(){
                               return localDivisionNumberTracker;
                           }

                           

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
                            localDivisionNumberTracker = param != null;
                                   
                                            this.localDivisionNumber=param;
                                    

                               }
                            

                        /**
                        * field for ReasonCode
                        */

                        
                                    protected java.lang.String localReasonCode ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getReasonCode(){
                               return localReasonCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReasonCode
                               */
                               public void setReasonCode(java.lang.String param){
                            
                                            this.localReasonCode=param;
                                    

                               }
                            

                        /**
                        * field for CaseNumber
                        */

                        
                                    protected java.lang.String localCaseNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCaseNumberTracker = false ;

                           public boolean isCaseNumberSpecified(){
                               return localCaseNumberTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCaseNumber(){
                               return localCaseNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CaseNumber
                               */
                               public void setCaseNumber(java.lang.String param){
                            localCaseNumberTracker = param != null;
                                   
                                            this.localCaseNumber=param;
                                    

                               }
                            

                        /**
                        * field for ReferenceNumber
                        */

                        
                                    protected java.lang.String localReferenceNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReferenceNumberTracker = false ;

                           public boolean isReferenceNumberSpecified(){
                               return localReferenceNumberTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getReferenceNumber(){
                               return localReferenceNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReferenceNumber
                               */
                               public void setReferenceNumber(java.lang.String param){
                            localReferenceNumberTracker = param != null;
                                   
                                            this.localReferenceNumber=param;
                                    

                               }
                            

                        /**
                        * field for MerchantNumber
                        */

                        
                                    protected java.lang.String localMerchantNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMerchantNumberTracker = false ;

                           public boolean isMerchantNumberSpecified(){
                               return localMerchantNumberTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMerchantNumber(){
                               return localMerchantNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MerchantNumber
                               */
                               public void setMerchantNumber(java.lang.String param){
                            localMerchantNumberTracker = param != null;
                                   
                                            this.localMerchantNumber=param;
                                    

                               }
                            

                        /**
                        * field for MerchantTransactionId
                        */

                        
                                    protected java.lang.String localMerchantTransactionId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMerchantTransactionIdTracker = false ;

                           public boolean isMerchantTransactionIdSpecified(){
                               return localMerchantTransactionIdTracker;
                           }

                           

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
                            localMerchantTransactionIdTracker = param != null;
                                   
                                            this.localMerchantTransactionId=param;
                                    

                               }
                            

                        /**
                        * field for MerchantTransactionTimestamp
                        */

                        
                                    protected java.util.Calendar localMerchantTransactionTimestamp ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMerchantTransactionTimestampTracker = false ;

                           public boolean isMerchantTransactionTimestampSpecified(){
                               return localMerchantTransactionTimestampTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getMerchantTransactionTimestamp(){
                               return localMerchantTransactionTimestamp;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MerchantTransactionTimestamp
                               */
                               public void setMerchantTransactionTimestamp(java.util.Calendar param){
                            localMerchantTransactionTimestampTracker = param != null;
                                   
                                            this.localMerchantTransactionTimestamp=param;
                                    

                               }
                            

                        /**
                        * field for ProcessorReceivedTimestamp
                        */

                        
                                    protected java.util.Calendar localProcessorReceivedTimestamp ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getProcessorReceivedTimestamp(){
                               return localProcessorReceivedTimestamp;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ProcessorReceivedTimestamp
                               */
                               public void setProcessorReceivedTimestamp(java.util.Calendar param){
                            
                                            this.localProcessorReceivedTimestamp=param;
                                    

                               }
                            

                        /**
                        * field for PostedTimestamp
                        */

                        
                                    protected java.util.Calendar localPostedTimestamp ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getPostedTimestamp(){
                               return localPostedTimestamp;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PostedTimestamp
                               */
                               public void setPostedTimestamp(java.util.Calendar param){
                            
                                            this.localPostedTimestamp=param;
                                    

                               }
                            

                        /**
                        * field for StatusChangedTimestamp
                        */

                        
                                    protected java.util.Calendar localStatusChangedTimestamp ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localStatusChangedTimestampTracker = false ;

                           public boolean isStatusChangedTimestampSpecified(){
                               return localStatusChangedTimestampTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getStatusChangedTimestamp(){
                               return localStatusChangedTimestamp;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param StatusChangedTimestamp
                               */
                               public void setStatusChangedTimestamp(java.util.Calendar param){
                            localStatusChangedTimestampTracker = param != null;
                                   
                                            this.localStatusChangedTimestamp=param;
                                    

                               }
                            

                        /**
                        * field for Status
                        */

                        
                                    protected com.vindicia.soap.v1_1.selecttypes.ChargebackStatus localStatus ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vindicia.soap.v1_1.selecttypes.ChargebackStatus
                           */
                           public  com.vindicia.soap.v1_1.selecttypes.ChargebackStatus getStatus(){
                               return localStatus;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Status
                               */
                               public void setStatus(com.vindicia.soap.v1_1.selecttypes.ChargebackStatus param){
                            
                                            this.localStatus=param;
                                    

                               }
                            

                        /**
                        * field for MerchantUserId
                        */

                        
                                    protected java.lang.String localMerchantUserId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMerchantUserIdTracker = false ;

                           public boolean isMerchantUserIdSpecified(){
                               return localMerchantUserIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMerchantUserId(){
                               return localMerchantUserId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MerchantUserId
                               */
                               public void setMerchantUserId(java.lang.String param){
                            localMerchantUserIdTracker = param != null;
                                   
                                            this.localMerchantUserId=param;
                                    

                               }
                            

                        /**
                        * field for Note
                        */

                        
                                    protected java.lang.String localNote ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNoteTracker = false ;

                           public boolean isNoteSpecified(){
                               return localNoteTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getNote(){
                               return localNote;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Note
                               */
                               public void setNote(java.lang.String param){
                            localNoteTracker = param != null;
                                   
                                            this.localNote=param;
                                    

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
                           namespacePrefix+":Chargeback",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "Chargeback",
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
                             } if (localPresentmentAmountTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "presentmentAmount", xmlWriter);
                             

                                          if (localPresentmentAmount==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("presentmentAmount cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPresentmentAmount));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPresentmentCurrencyTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "presentmentCurrency", xmlWriter);
                             

                                          if (localPresentmentCurrency==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("presentmentCurrency cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPresentmentCurrency);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDivisionNumberTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "divisionNumber", xmlWriter);
                             

                                          if (localDivisionNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("divisionNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDivisionNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "";
                                    writeStartElement(null, namespace, "reasonCode", xmlWriter);
                             

                                          if (localReasonCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("reasonCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localReasonCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localCaseNumberTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "caseNumber", xmlWriter);
                             

                                          if (localCaseNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("caseNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCaseNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localReferenceNumberTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "referenceNumber", xmlWriter);
                             

                                          if (localReferenceNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("referenceNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localReferenceNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMerchantNumberTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "merchantNumber", xmlWriter);
                             

                                          if (localMerchantNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("merchantNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMerchantNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMerchantTransactionIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "merchantTransactionId", xmlWriter);
                             

                                          if (localMerchantTransactionId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("merchantTransactionId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMerchantTransactionId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMerchantTransactionTimestampTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "merchantTransactionTimestamp", xmlWriter);
                             

                                          if (localMerchantTransactionTimestamp==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("merchantTransactionTimestamp cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMerchantTransactionTimestamp));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "";
                                    writeStartElement(null, namespace, "processorReceivedTimestamp", xmlWriter);
                             

                                          if (localProcessorReceivedTimestamp==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("processorReceivedTimestamp cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProcessorReceivedTimestamp));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "";
                                    writeStartElement(null, namespace, "postedTimestamp", xmlWriter);
                             

                                          if (localPostedTimestamp==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("postedTimestamp cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPostedTimestamp));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localStatusChangedTimestampTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "statusChangedTimestamp", xmlWriter);
                             

                                          if (localStatusChangedTimestamp==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("statusChangedTimestamp cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatusChangedTimestamp));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                            if (localStatus==null){
                                                 throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
                                            }
                                           localStatus.serialize(new javax.xml.namespace.QName("","status"),
                                               xmlWriter);
                                         if (localMerchantUserIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "merchantUserId", xmlWriter);
                             

                                          if (localMerchantUserId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("merchantUserId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMerchantUserId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNoteTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "note", xmlWriter);
                             

                                          if (localNote==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("note cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNote);
                                            
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
                                    } if (localPresentmentAmountTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "presentmentAmount"));
                                 
                                        if (localPresentmentAmount != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPresentmentAmount));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("presentmentAmount cannot be null!!");
                                        }
                                    } if (localPresentmentCurrencyTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "presentmentCurrency"));
                                 
                                        if (localPresentmentCurrency != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPresentmentCurrency));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("presentmentCurrency cannot be null!!");
                                        }
                                    } if (localDivisionNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "divisionNumber"));
                                 
                                        if (localDivisionNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDivisionNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("divisionNumber cannot be null!!");
                                        }
                                    }
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "reasonCode"));
                                 
                                        if (localReasonCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReasonCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("reasonCode cannot be null!!");
                                        }
                                     if (localCaseNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "caseNumber"));
                                 
                                        if (localCaseNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCaseNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("caseNumber cannot be null!!");
                                        }
                                    } if (localReferenceNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "referenceNumber"));
                                 
                                        if (localReferenceNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReferenceNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("referenceNumber cannot be null!!");
                                        }
                                    } if (localMerchantNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "merchantNumber"));
                                 
                                        if (localMerchantNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMerchantNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("merchantNumber cannot be null!!");
                                        }
                                    } if (localMerchantTransactionIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "merchantTransactionId"));
                                 
                                        if (localMerchantTransactionId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMerchantTransactionId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("merchantTransactionId cannot be null!!");
                                        }
                                    } if (localMerchantTransactionTimestampTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "merchantTransactionTimestamp"));
                                 
                                        if (localMerchantTransactionTimestamp != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMerchantTransactionTimestamp));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("merchantTransactionTimestamp cannot be null!!");
                                        }
                                    }
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "processorReceivedTimestamp"));
                                 
                                        if (localProcessorReceivedTimestamp != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProcessorReceivedTimestamp));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("processorReceivedTimestamp cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "postedTimestamp"));
                                 
                                        if (localPostedTimestamp != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPostedTimestamp));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("postedTimestamp cannot be null!!");
                                        }
                                     if (localStatusChangedTimestampTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "statusChangedTimestamp"));
                                 
                                        if (localStatusChangedTimestamp != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatusChangedTimestamp));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("statusChangedTimestamp cannot be null!!");
                                        }
                                    }
                            elementList.add(new javax.xml.namespace.QName("",
                                                                      "status"));
                            
                            
                                    if (localStatus==null){
                                         throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
                                    }
                                    elementList.add(localStatus);
                                 if (localMerchantUserIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "merchantUserId"));
                                 
                                        if (localMerchantUserId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMerchantUserId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("merchantUserId cannot be null!!");
                                        }
                                    } if (localNoteTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "note"));
                                 
                                        if (localNote != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNote));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("note cannot be null!!");
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
        public static Chargeback parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            Chargeback object =
                new Chargeback();

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
                    
                            if (!"Chargeback".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (Chargeback)com.vindicia.soap.v1_1.select.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list19 = new java.util.ArrayList();
                    
                                    
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","presentmentAmount").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"presentmentAmount" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPresentmentAmount(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","presentmentCurrency").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"presentmentCurrency" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPresentmentCurrency(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
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
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","reasonCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"reasonCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReasonCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","caseNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"caseNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCaseNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","referenceNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"referenceNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReferenceNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","merchantNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"merchantNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMerchantNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
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
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","merchantTransactionTimestamp").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"merchantTransactionTimestamp" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMerchantTransactionTimestamp(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","processorReceivedTimestamp").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"processorReceivedTimestamp" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProcessorReceivedTimestamp(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","postedTimestamp").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"postedTimestamp" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPostedTimestamp(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","statusChangedTimestamp").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"statusChangedTimestamp" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setStatusChangedTimestamp(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","status").equals(reader.getName())){
                                
                                                object.setStatus(com.vindicia.soap.v1_1.selecttypes.ChargebackStatus.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","merchantUserId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"merchantUserId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMerchantUserId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","note").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"note" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNote(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","nameValues").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list19.add(com.vindicia.soap.v1_1.selecttypes.NameValuePair.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone19 = false;
                                                        while(!loopDone19){
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
                                                                loopDone19 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("","nameValues").equals(reader.getName())){
                                                                    list19.add(com.vindicia.soap.v1_1.selecttypes.NameValuePair.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone19 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setNameValues((com.vindicia.soap.v1_1.selecttypes.NameValuePair[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                com.vindicia.soap.v1_1.selecttypes.NameValuePair.class,
                                                                list19));
                                                            
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
           
    
