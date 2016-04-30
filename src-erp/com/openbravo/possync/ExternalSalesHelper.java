//    Openbravo POS is a point of sales application designed for touch screens.
//    http://www.openbravo.com/product/pos
//    Copyright (c) 2007 openTrends Solucions i Sistemes, S.L
//    Modified by Openbravo SL on March 22, 2007
//    These modifications are copyright Openbravo SL
//    Author/s: A. Romero
//    You may contact Openbravo SL at: http://www.openbravo.com
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.possync;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import com.openbravo.basic.BasicException;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.util.AltEncrypter;
import com.openbravo.pos.util.Base64Encoder;
import com.openbravo.ws.customers.Customer;
import com.openbravo.ws.customers.WebServiceImpl;
import com.openbravo.ws.customers.WebServiceImplServiceLocator;
import com.openbravo.ws.externalsales.ExternalSalesImpl;
import com.openbravo.ws.externalsales.ExternalSalesImplServiceLocator;
import com.openbravo.ws.externalsales.Order;
import com.openbravo.ws.externalsales.Product;
import com.openbravo.ws.externalsales.ProductPlus;

public class ExternalSalesHelper {
    
    private ExternalSalesImpl externalSales;
    private WebServiceImpl externalCustomers;
    
    private String m_sERPUser;
    private String m_sERPPassword;
    private String m_iERPId;
    private String m_iERPOrg;
    private String m_iERPPos;
    
    /** Creates a new instance of WebServiceHelper */
    public ExternalSalesHelper(DataLogicSystem dlsystem) throws BasicException, ServiceException, MalformedURLException {
        
        
        Properties prop = dlsystem.getResourceAsProperties("openbravo.properties");
        if (prop == null) {
            throw new BasicException(AppLocal.getIntString("message.propsnotdefined"));            
        } else {
            String url = prop.getProperty("url");
            if (url == null || url.equals("")) {
                throw new BasicException(AppLocal.getIntString("message.urlnotdefined"));
            } else {
                
                url = url.trim();
                 
                // transform the URL for backwards compatibility
                if (url.endsWith("/ExternalSales")) {
                    url = url.substring(0, url.length() - 14);
                }               
                
                externalSales = new ExternalSalesImplServiceLocator().getExternalSales(new URL(url + "/ExternalSales"));
                externalCustomers = new WebServiceImplServiceLocator().getWebService(new URL(url + "/WebService"));
                        
                m_sERPUser = prop.getProperty("user");
                m_sERPPassword = prop.getProperty("password");        
                if (m_sERPUser != null && m_sERPPassword != null && m_sERPPassword.startsWith("crypt:")) {
                    // La clave esta encriptada.
                    AltEncrypter cypher = new AltEncrypter("key" + m_sERPUser);
                    m_sERPPassword = cypher.decrypt(m_sERPPassword.substring(6));
                } 
                m_sERPPassword = getPasswordHash(m_sERPPassword);
                m_iERPId = prop.getProperty("id");
                m_iERPOrg = prop.getProperty("org");
                m_iERPPos = prop.getProperty("pos");
            }
        }
    }
    
    public Customer[] getCustomers() throws RemoteException {
        try {
            // 2.40 service
            return externalCustomers.getCustomers(m_iERPId, m_sERPUser, m_sERPPassword);
        } catch (RemoteException e) {
            // 2.30 service not exists
            return new Customer[0];
        }             
    }
    
    public Product[] getProductsCatalog() throws RemoteException {
        try {
            // 2.40 service
            return externalSales.getProductsPlusCatalog(m_iERPId, m_iERPOrg, m_iERPPos, m_sERPUser, m_sERPPassword);
        } catch (RemoteException e) {
            // 2.30 service
            return externalSales.getProductsCatalog(m_iERPId, m_iERPOrg, m_iERPPos, m_sERPUser, m_sERPPassword);
        }        
    }
    
    public boolean uploadOrders(Order[] orderstoupload) throws RemoteException {
        return externalSales.uploadOrders(m_iERPId, m_iERPOrg, m_iERPPos, orderstoupload, m_sERPUser, m_sERPPassword);
    }
       
    private static String getPasswordHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte raw[] = md.digest(password.getBytes("UTF-8"));
            return Base64Encoder.encode(raw);
        } catch (NoSuchAlgorithmException e) {
            return null; // never happens :-)
        } catch (UnsupportedEncodingException e) {
            return null; // never happens :-)
        }
    }     
}
