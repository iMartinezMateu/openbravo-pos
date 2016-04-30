/**
 * OrderLine.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package com.openbravo.ws.externalsales;

public class OrderLine implements java.io.Serializable {

    private static final long serialVersionUID = 9316058460611L;
    private java.lang.String orderLineId;
    private double price;
    private java.lang.String productId;
    private java.lang.String taxId;
    private double units;

    public OrderLine() {
    }

    public OrderLine(
            java.lang.String orderLineId,
            double price,
            java.lang.String productId,
            java.lang.String taxId,
            double units) {
        this.orderLineId = orderLineId;
        this.price = price;
        this.productId = productId;
        this.taxId = taxId;
        this.units = units;
    }

    /**
     * Gets the orderLineId value for this OrderLine.
     * 
     * @return orderLineId
     */
    public java.lang.String getOrderLineId() {
        return orderLineId;
    }

    /**
     * Sets the orderLineId value for this OrderLine.
     * 
     * @param orderLineId
     */
    public void setOrderLineId(java.lang.String orderLineId) {
        this.orderLineId = orderLineId;
    }

    /**
     * Gets the price value for this OrderLine.
     * 
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price value for this OrderLine.
     * 
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the productId value for this OrderLine.
     * 
     * @return productId
     */
    public java.lang.String getProductId() {
        return productId;
    }

    /**
     * Sets the productId value for this OrderLine.
     * 
     * @param productId
     */
    public void setProductId(java.lang.String productId) {
        this.productId = productId;
    }

    /**
     * Gets the taxId value for this OrderLine.
     * 
     * @return taxId
     */
    public java.lang.String getTaxId() {
        return taxId;
    }

    /**
     * Sets the taxId value for this OrderLine.
     * 
     * @param taxId
     */
    public void setTaxId(java.lang.String taxId) {
        this.taxId = taxId;
    }

    /**
     * Gets the units value for this OrderLine.
     * 
     * @return units
     */
    public double getUnits() {
        return units;
    }

    /**
     * Sets the units value for this OrderLine.
     * 
     * @param units
     */
    public void setUnits(double units) {
        this.units = units;
    }
    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderLine)) {
            return false;
        }
        OrderLine other = (OrderLine) obj;
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
                ((this.orderLineId == null && other.getOrderLineId() == null) ||
                (this.orderLineId != null &&
                this.orderLineId.equals(other.getOrderLineId()))) &&
                this.price == other.getPrice() &&
                ((this.productId == null && other.getProductId() == null) ||
                (this.productId != null &&
                this.productId.equals(other.getProductId()))) &&
                ((this.taxId == null && other.getTaxId() == null) ||
                (this.taxId != null &&
                this.taxId.equals(other.getTaxId()))) &&
                this.units == other.getUnits();
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
        if (getOrderLineId() != null) {
            _hashCode += getOrderLineId().hashCode();
        }
        _hashCode += new Double(getPrice()).hashCode();
        if (getProductId() != null) {
            _hashCode += getProductId().hashCode();
        }
        if (getTaxId() != null) {
            _hashCode += getTaxId().hashCode();
        }
        _hashCode += new Double(getUnits()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(OrderLine.class, true);
    

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("/services/ExternalSales", "OrderLine"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderLineId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orderLineId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("price");
        elemField.setXmlName(new javax.xml.namespace.QName("", "price"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "taxId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("units");
        elemField.setXmlName(new javax.xml.namespace.QName("", "units"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
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
