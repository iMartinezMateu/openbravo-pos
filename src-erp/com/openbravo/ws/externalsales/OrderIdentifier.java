/**
 * OrderIdentifier.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package com.openbravo.ws.externalsales;

public class OrderIdentifier implements java.io.Serializable {

    private static final long serialVersionUID = 9341677250499L;
    private java.util.Calendar dateNew;
    private java.lang.String documentNo;

    public OrderIdentifier() {
    }

    public OrderIdentifier(
            java.util.Calendar dateNew,
            java.lang.String documentNo) {
        this.dateNew = dateNew;
        this.documentNo = documentNo;
    }

    /**
     * Gets the dateNew value for this OrderIdentifier.
     * 
     * @return dateNew
     */
    public java.util.Calendar getDateNew() {
        return dateNew;
    }

    /**
     * Sets the dateNew value for this OrderIdentifier.
     * 
     * @param dateNew
     */
    public void setDateNew(java.util.Calendar dateNew) {
        this.dateNew = dateNew;
    }

    /**
     * Gets the documentNo value for this OrderIdentifier.
     * 
     * @return documentNo
     */
    public java.lang.String getDocumentNo() {
        return documentNo;
    }

    /**
     * Sets the documentNo value for this OrderIdentifier.
     * 
     * @param documentNo
     */
    public void setDocumentNo(java.lang.String documentNo) {
        this.documentNo = documentNo;
    }
    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderIdentifier)) {
            return false;
        }
        OrderIdentifier other = (OrderIdentifier) obj;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.dateNew == null && other.getDateNew() == null) ||
                (this.dateNew != null &&
                this.dateNew.equals(other.getDateNew()))) &&
                ((this.documentNo == null && other.getDocumentNo() == null) ||
                (this.documentNo != null &&
                this.documentNo.equals(other.getDocumentNo())));
        __equalsCalc = null;
        return _equals;
    }
    private boolean __hashCodeCalc = false;

    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDateNew() != null) {
            _hashCode += getDateNew().hashCode();
        }
        if (getDocumentNo() != null) {
            _hashCode += getDocumentNo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(OrderIdentifier.class, true);
    

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("/services/ExternalSales", "OrderIdentifier"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateNew");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dateNew"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "documentNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanSerializer(
                _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanDeserializer(
                _javaType, _xmlType, typeDesc);
    }
}
