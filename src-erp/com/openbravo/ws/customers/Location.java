/**
 * Location.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package com.openbravo.ws.customers;

public class Location implements java.io.Serializable {

    private static final long serialVersionUID = 9751926027090L;
    private java.lang.String address1;
    private java.lang.String address2;
    private java.lang.String businessPartnerId;
    private java.lang.String city;
    private java.lang.String clientId;
    private java.lang.String country;
    private java.lang.String id;
    private java.lang.String postal;
    private java.lang.String region;

    public Location() {
    }

    public Location(
            java.lang.String address1,
            java.lang.String address2,
            java.lang.String businessPartnerId,
            java.lang.String city,
            java.lang.String clientId,
            java.lang.String country,
            java.lang.String id,
            java.lang.String postal,
            java.lang.String region) {
        this.address1 = address1;
        this.address2 = address2;
        this.businessPartnerId = businessPartnerId;
        this.city = city;
        this.clientId = clientId;
        this.country = country;
        this.id = id;
        this.postal = postal;
        this.region = region;
    }

    /**
     * Gets the address1 value for this Location.
     * 
     * @return address1
     */
    public java.lang.String getAddress1() {
        return address1;
    }

    /**
     * Sets the address1 value for this Location.
     * 
     * @param address1
     */
    public void setAddress1(java.lang.String address1) {
        this.address1 = address1;
    }

    /**
     * Gets the address2 value for this Location.
     * 
     * @return address2
     */
    public java.lang.String getAddress2() {
        return address2;
    }

    /**
     * Sets the address2 value for this Location.
     * 
     * @param address2
     */
    public void setAddress2(java.lang.String address2) {
        this.address2 = address2;
    }

    /**
     * Gets the businessPartnerId value for this Location.
     * 
     * @return businessPartnerId
     */
    public java.lang.String getBusinessPartnerId() {
        return businessPartnerId;
    }

    /**
     * Sets the businessPartnerId value for this Location.
     * 
     * @param businessPartnerId
     */
    public void setBusinessPartnerId(java.lang.String businessPartnerId) {
        this.businessPartnerId = businessPartnerId;
    }

    /**
     * Gets the city value for this Location.
     * 
     * @return city
     */
    public java.lang.String getCity() {
        return city;
    }

    /**
     * Sets the city value for this Location.
     * 
     * @param city
     */
    public void setCity(java.lang.String city) {
        this.city = city;
    }

    /**
     * Gets the clientId value for this Location.
     * 
     * @return clientId
     */
    public java.lang.String getClientId() {
        return clientId;
    }

    /**
     * Sets the clientId value for this Location.
     * 
     * @param clientId
     */
    public void setClientId(java.lang.String clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets the country value for this Location.
     * 
     * @return country
     */
    public java.lang.String getCountry() {
        return country;
    }

    /**
     * Sets the country value for this Location.
     * 
     * @param country
     */
    public void setCountry(java.lang.String country) {
        this.country = country;
    }

    /**
     * Gets the id value for this Location.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }

    /**
     * Sets the id value for this Location.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * Gets the postal value for this Location.
     * 
     * @return postal
     */
    public java.lang.String getPostal() {
        return postal;
    }

    /**
     * Sets the postal value for this Location.
     * 
     * @param postal
     */
    public void setPostal(java.lang.String postal) {
        this.postal = postal;
    }

    /**
     * Gets the region value for this Location.
     * 
     * @return region
     */
    public java.lang.String getRegion() {
        return region;
    }

    /**
     * Sets the region value for this Location.
     * 
     * @param region
     */
    public void setRegion(java.lang.String region) {
        this.region = region;
    }
    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Location)) {
            return false;
        }
        Location other = (Location) obj;
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
                ((this.address1 == null && other.getAddress1() == null) ||
                (this.address1 != null &&
                this.address1.equals(other.getAddress1()))) &&
                ((this.address2 == null && other.getAddress2() == null) ||
                (this.address2 != null &&
                this.address2.equals(other.getAddress2()))) &&
                ((this.businessPartnerId == null && other.getBusinessPartnerId() == null) ||
                (this.businessPartnerId != null &&
                this.businessPartnerId.equals(other.getBusinessPartnerId()))) &&
                ((this.city == null && other.getCity() == null) ||
                (this.city != null &&
                this.city.equals(other.getCity()))) &&
                ((this.clientId == null && other.getClientId() == null) ||
                (this.clientId != null &&
                this.clientId.equals(other.getClientId()))) &&
                ((this.country == null && other.getCountry() == null) ||
                (this.country != null &&
                this.country.equals(other.getCountry()))) &&
                ((this.id == null && other.getId() == null) ||
                (this.id != null &&
                this.id.equals(other.getId()))) &&
                ((this.postal == null && other.getPostal() == null) ||
                (this.postal != null &&
                this.postal.equals(other.getPostal()))) &&
                ((this.region == null && other.getRegion() == null) ||
                (this.region != null &&
                this.region.equals(other.getRegion())));
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
        if (getAddress1() != null) {
            _hashCode += getAddress1().hashCode();
        }
        if (getAddress2() != null) {
            _hashCode += getAddress2().hashCode();
        }
        if (getBusinessPartnerId() != null) {
            _hashCode += getBusinessPartnerId().hashCode();
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getClientId() != null) {
            _hashCode += getClientId().hashCode();
        }
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getPostal() != null) {
            _hashCode += getPostal().hashCode();
        }
        if (getRegion() != null) {
            _hashCode += getRegion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(Location.class, true);
    

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("/services/WebService", "Location"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "address1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "address2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("businessPartnerId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "businessPartnerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("", "city"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("", "country"));
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
        elemField.setFieldName("postal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "postal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("region");
        elemField.setXmlName(new javax.xml.namespace.QName("", "region"));
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
