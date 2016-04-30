/**
 * WebServiceImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.openbravo.ws.customers;

public class WebServiceImplServiceLocator extends org.apache.axis.client.Service implements com.openbravo.ws.customers.WebServiceImplService {

    public WebServiceImplServiceLocator() {
    }


    public WebServiceImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WebServiceImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WebService
    private java.lang.String WebService_address = "http://localhost:8180/openbravo/services/WebService";

    public java.lang.String getWebServiceAddress() {
        return WebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WebServiceWSDDServiceName = "WebService";

    public java.lang.String getWebServiceWSDDServiceName() {
        return WebServiceWSDDServiceName;
    }

    public void setWebServiceWSDDServiceName(java.lang.String name) {
        WebServiceWSDDServiceName = name;
    }

    public com.openbravo.ws.customers.WebServiceImpl getWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWebService(endpoint);
    }

    public com.openbravo.ws.customers.WebServiceImpl getWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.openbravo.ws.customers.WebServiceSoapBindingStub _stub = new com.openbravo.ws.customers.WebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWebServiceEndpointAddress(java.lang.String address) {
        WebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.openbravo.ws.customers.WebServiceImpl.class.isAssignableFrom(serviceEndpointInterface)) {
                com.openbravo.ws.customers.WebServiceSoapBindingStub _stub = new com.openbravo.ws.customers.WebServiceSoapBindingStub(new java.net.URL(WebService_address), this);
                _stub.setPortName(getWebServiceWSDDServiceName());
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
        if ("WebService".equals(inputPortName)) {
            return getWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost:8180/openbravo/services/WebService", "WebServiceImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://localhost:8180/openbravo/services/WebService", "WebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WebService".equals(portName)) {
            setWebServiceEndpointAddress(address);
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
