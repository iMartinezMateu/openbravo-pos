/**
 * BusinessPartner.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package com.openbravo.ws.customers;

public class BusinessPartner implements java.io.Serializable {

    private static final long serialVersionUID = 7460326872588L;
    private java.lang.String clientId;
    private java.lang.Boolean complete;
    private com.openbravo.ws.customers.Contact[] contacts;
    private java.lang.Boolean customer;
    private java.lang.String description;
    private java.lang.String id;
    private com.openbravo.ws.customers.Location[] locations;
    private java.lang.String name;
    private java.lang.String searchKey;
    private java.lang.Boolean vendor;

    public BusinessPartner() {
    }

    public BusinessPartner(
            java.lang.String clientId,
            java.lang.Boolean complete,
            com.openbravo.ws.customers.Contact[] contacts,
            java.lang.Boolean customer,
            java.lang.String description,
            java.lang.String id,
            com.openbravo.ws.customers.Location[] locations,
            java.lang.String name,
            java.lang.String searchKey,
            java.lang.Boolean vendor) {
        this.clientId = clientId;
        this.complete = complete;
        this.contacts = contacts;
        this.customer = customer;
        this.description = description;
        this.id = id;
        this.locations = locations;
        this.name = name;
        this.searchKey = searchKey;
        this.vendor = vendor;
    }

    /**
     * Gets the clientId value for this BusinessPartner.
     * 
     * @return clientId
     */
    public java.lang.String getClientId() {
        return clientId;
    }

    /**
     * Sets the clientId value for this BusinessPartner.
     * 
     * @param clientId
     */
    public void setClientId(java.lang.String clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets the complete value for this BusinessPartner.
     * 
     * @return complete
     */
    public java.lang.Boolean getComplete() {
        return complete;
    }

    /**
     * Sets the complete value for this BusinessPartner.
     * 
     * @param complete
     */
    public void setComplete(java.lang.Boolean complete) {
        this.complete = complete;
    }

    /**
     * Gets the contacts value for this BusinessPartner.
     * 
     * @return contacts
     */
    public com.openbravo.ws.customers.Contact[] getContacts() {
        return contacts;
    }

    /**
     * Sets the contacts value for this BusinessPartner.
     * 
     * @param contacts
     */
    public void setContacts(com.openbravo.ws.customers.Contact[] contacts) {
        this.contacts = contacts;
    }

    /**
     * Gets the customer value for this BusinessPartner.
     * 
     * @return customer
     */
    public java.lang.Boolean getCustomer() {
        return customer;
    }

    /**
     * Sets the customer value for this BusinessPartner.
     * 
     * @param customer
     */
    public void setCustomer(java.lang.Boolean customer) {
        this.customer = customer;
    }

    /**
     * Gets the description value for this BusinessPartner.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }

    /**
     * Sets the description value for this BusinessPartner.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    /**
     * Gets the id value for this BusinessPartner.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }

    /**
     * Sets the id value for this BusinessPartner.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * Gets the locations value for this BusinessPartner.
     * 
     * @return locations
     */
    public com.openbravo.ws.customers.Location[] getLocations() {
        return locations;
    }

    /**
     * Sets the locations value for this BusinessPartner.
     * 
     * @param locations
     */
    public void setLocations(com.openbravo.ws.customers.Location[] locations) {
        this.locations = locations;
    }

    /**
     * Gets the name value for this BusinessPartner.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * Sets the name value for this BusinessPartner.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
     * Gets the searchKey value for this BusinessPartner.
     * 
     * @return searchKey
     */
    public java.lang.String getSearchKey() {
        return searchKey;
    }

    /**
     * Sets the searchKey value for this BusinessPartner.
     * 
     * @param searchKey
     */
    public void setSearchKey(java.lang.String searchKey) {
        this.searchKey = searchKey;
    }

    /**
     * Gets the vendor value for this BusinessPartner.
     * 
     * @return vendor
     */
    public java.lang.Boolean getVendor() {
        return vendor;
    }

    /**
     * Sets the vendor value for this BusinessPartner.
     * 
     * @param vendor
     */
    public void setVendor(java.lang.Boolean vendor) {
        this.vendor = vendor;
    }
    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BusinessPartner)) {
            return false;
        }
        BusinessPartner other = (BusinessPartner) obj;
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
                ((this.clientId == null && other.getClientId() == null) ||
                (this.clientId != null &&
                this.clientId.equals(other.getClientId()))) &&
                ((this.complete == null && other.getComplete() == null) ||
                (this.complete != null &&
                this.complete.equals(other.getComplete()))) &&
                ((this.contacts == null && other.getContacts() == null) ||
                (this.contacts != null &&
                java.util.Arrays.equals(this.contacts, other.getContacts()))) &&
                ((this.customer == null && other.getCustomer() == null) ||
                (this.customer != null &&
                this.customer.equals(other.getCustomer()))) &&
                ((this.description == null && other.getDescription() == null) ||
                (this.description != null &&
                this.description.equals(other.getDescription()))) &&
                ((this.id == null && other.getId() == null) ||
                (this.id != null &&
                this.id.equals(other.getId()))) &&
                ((this.locations == null && other.getLocations() == null) ||
                (this.locations != null &&
                java.util.Arrays.equals(this.locations, other.getLocations()))) &&
                ((this.name == null && other.getName() == null) ||
                (this.name != null &&
                this.name.equals(other.getName()))) &&
                ((this.searchKey == null && other.getSearchKey() == null) ||
                (this.searchKey != null &&
                this.searchKey.equals(other.getSearchKey()))) &&
                ((this.vendor == null && other.getVendor() == null) ||
                (this.vendor != null &&
                this.vendor.equals(other.getVendor())));
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
        if (getClientId() != null) {
            _hashCode += getClientId().hashCode();
        }
        if (getComplete() != null) {
            _hashCode += getComplete().hashCode();
        }
        if (getContacts() != null) {
            for (int i = 0;
                    i < java.lang.reflect.Array.getLength(getContacts());
                    i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getContacts(), i);
                if (obj != null &&
                        !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCustomer() != null) {
            _hashCode += getCustomer().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getLocations() != null) {
            for (int i = 0;
                    i < java.lang.reflect.Array.getLength(getLocations());
                    i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLocations(), i);
                if (obj != null &&
                        !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getSearchKey() != null) {
            _hashCode += getSearchKey().hashCode();
        }
        if (getVendor() != null) {
            _hashCode += getVendor().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(BusinessPartner.class, true);
    

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("/services/WebService", "BusinessPartner"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("complete");
        elemField.setXmlName(new javax.xml.namespace.QName("", "complete"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contacts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contacts"));
        elemField.setXmlType(new javax.xml.namespace.QName("/services/WebService", "Contact"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "customer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
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
        elemField.setFieldName("locations");
        elemField.setXmlName(new javax.xml.namespace.QName("", "locations"));
        elemField.setXmlType(new javax.xml.namespace.QName("/services/WebService", "Location"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "searchKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vendor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vendor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
