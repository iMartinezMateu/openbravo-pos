/**
 * ExternalSalesImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openbravo.ws.externalsales;

public class ExternalSalesImplServiceLocator extends org.apache.axis.client.Service implements com.openbravo.ws.externalsales.ExternalSalesImplService {

    public ExternalSalesImplServiceLocator() {
    }


    public ExternalSalesImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ExternalSalesImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ExternalSales
    private java.lang.String ExternalSales_address = "http://localhost:8180/openbravo/services/ExternalSales";

    public java.lang.String getExternalSalesAddress() {
        return ExternalSales_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ExternalSalesWSDDServiceName = "ExternalSales";

    public java.lang.String getExternalSalesWSDDServiceName() {
        return ExternalSalesWSDDServiceName;
    }

    public void setExternalSalesWSDDServiceName(java.lang.String name) {
        ExternalSalesWSDDServiceName = name;
    }

    public com.openbravo.ws.externalsales.ExternalSalesImpl getExternalSales() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ExternalSales_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getExternalSales(endpoint);
    }

    public com.openbravo.ws.externalsales.ExternalSalesImpl getExternalSales(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.openbravo.ws.externalsales.ExternalSalesSoapBindingStub _stub = new com.openbravo.ws.externalsales.ExternalSalesSoapBindingStub(portAddress, this);
            _stub.setPortName(getExternalSalesWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setExternalSalesEndpointAddress(java.lang.String address) {
        ExternalSales_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.openbravo.ws.externalsales.ExternalSalesImpl.class.isAssignableFrom(serviceEndpointInterface)) {
                com.openbravo.ws.externalsales.ExternalSalesSoapBindingStub _stub = new com.openbravo.ws.externalsales.ExternalSalesSoapBindingStub(new java.net.URL(ExternalSales_address), this);
                _stub.setPortName(getExternalSalesWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ExternalSales".equals(inputPortName)) {
            return getExternalSales();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost:8180/openbravo/services/ExternalSales", "ExternalSalesImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://localhost:8180/openbravo/services/ExternalSales", "ExternalSales"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ExternalSales".equals(portName)) {
            setExternalSalesEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
