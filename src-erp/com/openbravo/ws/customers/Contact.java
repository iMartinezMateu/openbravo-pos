/**
 * Contact.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package com.openbravo.ws.customers;

public class Contact implements java.io.Serializable {

    private static final long serialVersionUID = 9809905717175L;
    private java.lang.String businessPartnerId;
    private java.lang.String clientId;
    private java.lang.String email;
    private java.lang.String fax;
    private java.lang.String firstName;
    private java.lang.String id;
    private java.lang.String lastName;
    private java.lang.String phone;
    private java.lang.String phone2;

    public Contact() {
    }

    public Contact(
            java.lang.String businessPartnerId,
            java.lang.String clientId,
            java.lang.String email,
            java.lang.String fax,
            java.lang.String firstName,
            java.lang.String id,
            java.lang.String lastName,
            java.lang.String phone,
            java.lang.String phone2) {
        this.businessPartnerId = businessPartnerId;
        this.clientId = clientId;
        this.email = email;
        this.fax = fax;
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.phone = phone;
        this.phone2 = phone2;
    }

    /**
     * Gets the businessPartnerId value for this Contact.
     * 
     * @return businessPartnerId
     */
    public java.lang.String getBusinessPartnerId() {
        return businessPartnerId;
    }

    /**
     * Sets the businessPartnerId value for this Contact.
     * 
     * @param businessPartnerId
     */
    public void setBusinessPartnerId(java.lang.String businessPartnerId) {
        this.businessPartnerId = businessPartnerId;
    }

    /**
     * Gets the clientId value for this Contact.
     * 
     * @return clientId
     */
    public java.lang.String getClientId() {
        return clientId;
    }

    /**
     * Sets the clientId value for this Contact.
     * 
     * @param clientId
     */
    public void setClientId(java.lang.String clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets the email value for this Contact.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }

    /**
     * Sets the email value for this Contact.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    /**
     * Gets the fax value for this Contact.
     * 
     * @return fax
     */
    public java.lang.String getFax() {
        return fax;
    }

    /**
     * Sets the fax value for this Contact.
     * 
     * @param fax
     */
    public void setFax(java.lang.String fax) {
        this.fax = fax;
    }

    /**
     * Gets the firstName value for this Contact.
     * 
     * @return firstName
     */
    public java.lang.String getFirstName() {
        return firstName;
    }

    /**
     * Sets the firstName value for this Contact.
     * 
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the id value for this Contact.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }

    /**
     * Sets the id value for this Contact.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * Gets the lastName value for this Contact.
     * 
     * @return lastName
     */
    public java.lang.String getLastName() {
        return lastName;
    }

    /**
     * Sets the lastName value for this Contact.
     * 
     * @param lastName
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the phone value for this Contact.
     * 
     * @return phone
     */
    public java.lang.String getPhone() {
        return phone;
    }

    /**
     * Sets the phone value for this Contact.
     * 
     * @param phone
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }

    /**
     * Gets the phone2 value for this Contact.
     * 
     * @return phone2
     */
    public java.lang.String getPhone2() {
        return phone2;
    }

    /**
     * Sets the phone2 value for this Contact.
     * 
     * @param phone2
     */
    public void setPhone2(java.lang.String phone2) {
        this.phone2 = phone2;
    }
    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) obj;
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
                ((this.businessPartnerId == null && other.getBusinessPartnerId() == null) ||
                (this.businessPartnerId != null &&
                this.businessPartnerId.equals(other.getBusinessPartnerId()))) &&
                ((this.clientId == null && other.getClientId() == null) ||
                (this.clientId != null &&
                this.clientId.equals(other.getClientId()))) &&
                ((this.email == null && other.getEmail() == null) ||
                (this.email != null &&
                this.email.equals(other.getEmail()))) &&
                ((this.fax == null && other.getFax() == null) ||
                (this.fax != null &&
                this.fax.equals(other.getFax()))) &&
                ((this.firstName == null && other.getFirstName() == null) ||
                (this.firstName != null &&
                this.firstName.equals(other.getFirstName()))) &&
                ((this.id == null && other.getId() == null) ||
                (this.id != null &&
                this.id.equals(other.getId()))) &&
                ((this.lastName == null && other.getLastName() == null) ||
                (this.lastName != null &&
                this.lastName.equals(other.getLastName()))) &&
                ((this.phone == null && other.getPhone() == null) ||
                (this.phone != null &&
                this.phone.equals(other.getPhone()))) &&
                ((this.phone2 == null && other.getPhone2() == null) ||
                (this.phone2 != null &&
                this.phone2.equals(other.getPhone2())));
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
        if (getBusinessPartnerId() != null) {
            _hashCode += getBusinessPartnerId().hashCode();
        }
        if (getClientId() != null) {
            _hashCode += getClientId().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getFax() != null) {
            _hashCode += getFax().hashCode();
        }
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getLastName() != null) {
            _hashCode += getLastName().hashCode();
        }
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        if (getPhone2() != null) {
            _hashCode += getPhone2().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(Contact.class, true);
    

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("/services/WebService", "Contact"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("businessPartnerId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "businessPartnerId"));
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
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "firstName"));
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
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phone2"));
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
