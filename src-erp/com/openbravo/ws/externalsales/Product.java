/**
 * Product.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package com.openbravo.ws.externalsales;

public class Product implements java.io.Serializable {

    private static final long serialVersionUID = 9239663177762L;
    private com.openbravo.ws.externalsales.Category category;
    private java.lang.String description;
    private java.lang.String ean;
    private java.lang.String id;
    private java.lang.String imageUrl;
    private double listPrice;
    private java.lang.String name;
    private java.lang.String number;
    private double purchasePrice;
    private com.openbravo.ws.externalsales.Tax tax;

    public Product() {
    }

    public Product(
            com.openbravo.ws.externalsales.Category category,
            java.lang.String description,
            java.lang.String ean,
            java.lang.String id,
            java.lang.String imageUrl,
            double listPrice,
            java.lang.String name,
            java.lang.String number,
            double purchasePrice,
            com.openbravo.ws.externalsales.Tax tax) {
        this.category = category;
        this.description = description;
        this.ean = ean;
        this.id = id;
        this.imageUrl = imageUrl;
        this.listPrice = listPrice;
        this.name = name;
        this.number = number;
        this.purchasePrice = purchasePrice;
        this.tax = tax;
    }

    /**
     * Gets the category value for this Product.
     * 
     * @return category
     */
    public com.openbravo.ws.externalsales.Category getCategory() {
        return category;
    }

    /**
     * Sets the category value for this Product.
     * 
     * @param category
     */
    public void setCategory(com.openbravo.ws.externalsales.Category category) {
        this.category = category;
    }

    /**
     * Gets the description value for this Product.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }

    /**
     * Sets the description value for this Product.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    /**
     * Gets the ean value for this Product.
     * 
     * @return ean
     */
    public java.lang.String getEan() {
        return ean;
    }

    /**
     * Sets the ean value for this Product.
     * 
     * @param ean
     */
    public void setEan(java.lang.String ean) {
        this.ean = ean;
    }

    /**
     * Gets the id value for this Product.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }

    /**
     * Sets the id value for this Product.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * Gets the imageUrl value for this Product.
     * 
     * @return imageUrl
     */
    public java.lang.String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the imageUrl value for this Product.
     * 
     * @param imageUrl
     */
    public void setImageUrl(java.lang.String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the listPrice value for this Product.
     * 
     * @return listPrice
     */
    public double getListPrice() {
        return listPrice;
    }

    /**
     * Sets the listPrice value for this Product.
     * 
     * @param listPrice
     */
    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    /**
     * Gets the name value for this Product.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * Sets the name value for this Product.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
     * Gets the number value for this Product.
     * 
     * @return number
     */
    public java.lang.String getNumber() {
        return number;
    }

    /**
     * Sets the number value for this Product.
     * 
     * @param number
     */
    public void setNumber(java.lang.String number) {
        this.number = number;
    }

    /**
     * Gets the purchasePrice value for this Product.
     * 
     * @return purchasePrice
     */
    public double getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * Sets the purchasePrice value for this Product.
     * 
     * @param purchasePrice
     */
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * Gets the tax value for this Product.
     * 
     * @return tax
     */
    public com.openbravo.ws.externalsales.Tax getTax() {
        return tax;
    }

    /**
     * Sets the tax value for this Product.
     * 
     * @param tax
     */
    public void setTax(com.openbravo.ws.externalsales.Tax tax) {
        this.tax = tax;
    }
    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Product)) {
            return false;
        }
        Product other = (Product) obj;
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
                ((this.category == null && other.getCategory() == null) ||
                (this.category != null &&
                this.category.equals(other.getCategory()))) &&
                ((this.description == null && other.getDescription() == null) ||
                (this.description != null &&
                this.description.equals(other.getDescription()))) &&
                ((this.ean == null && other.getEan() == null) ||
                (this.ean != null &&
                this.ean.equals(other.getEan()))) &&
                ((this.id == null && other.getId() == null) ||
                (this.id != null &&
                this.id.equals(other.getId()))) &&
                ((this.imageUrl == null && other.getImageUrl() == null) ||
                (this.imageUrl != null &&
                this.imageUrl.equals(other.getImageUrl()))) &&
                this.listPrice == other.getListPrice() &&
                ((this.name == null && other.getName() == null) ||
                (this.name != null &&
                this.name.equals(other.getName()))) &&
                ((this.number == null && other.getNumber() == null) ||
                (this.number != null &&
                this.number.equals(other.getNumber()))) &&
                this.purchasePrice == other.getPurchasePrice() &&
                ((this.tax == null && other.getTax() == null) ||
                (this.tax != null &&
                this.tax.equals(other.getTax())));
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
        if (getCategory() != null) {
            _hashCode += getCategory().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getEan() != null) {
            _hashCode += getEan().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getImageUrl() != null) {
            _hashCode += getImageUrl().hashCode();
        }
        _hashCode += new Double(getListPrice()).hashCode();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getNumber() != null) {
            _hashCode += getNumber().hashCode();
        }
        _hashCode += new Double(getPurchasePrice()).hashCode();
        if (getTax() != null) {
            _hashCode += getTax().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(Product.class, true);
    

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("/services/ExternalSales", "Product"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("category");
        elemField.setXmlName(new javax.xml.namespace.QName("", "category"));
        elemField.setXmlType(new javax.xml.namespace.QName("/services/ExternalSales", "Category"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ean");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ean"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imageUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("", "imageUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("number");
        elemField.setXmlName(new javax.xml.namespace.QName("", "number"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purchasePrice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "purchasePrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tax"));
        elemField.setXmlType(new javax.xml.namespace.QName("/services/ExternalSales", "Tax"));
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
