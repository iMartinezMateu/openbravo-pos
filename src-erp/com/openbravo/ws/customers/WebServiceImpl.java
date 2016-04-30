/**
 * WebServiceImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openbravo.ws.customers;

public interface WebServiceImpl extends java.rmi.Remote {
    public com.openbravo.ws.customers.Customer[] getCustomers(java.lang.String clientId, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;
    public com.openbravo.ws.customers.Customer getCustomer(java.lang.String clientId, java.lang.String customerId, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;
    public com.openbravo.ws.customers.Customer getCustomer(java.lang.String clientId, java.lang.String name, java.lang.String searchKey, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;
    public boolean updateCustomer(com.openbravo.ws.customers.BusinessPartner customer, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;
    public int[] getCustomerAddresses(java.lang.String clientId, java.lang.String customerId, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;
    public com.openbravo.ws.customers.Location getCustomerLocation(java.lang.String clientId, java.lang.String customerId, java.lang.String locationId, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;
    public boolean updateAddress(com.openbravo.ws.customers.Location addr, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;
    public com.openbravo.ws.customers.Contact getCustomerContact(java.lang.String clientId, java.lang.String customerId, java.lang.String contactId, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;
    public boolean updateContact(com.openbravo.ws.customers.Contact contact, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;
}
