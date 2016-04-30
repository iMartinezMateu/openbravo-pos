//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
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

package com.openbravo.pos.ticket;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 *
 * @author jaroslawwozniak
 */
public class TicketInfo implements Serializable, Externalizable {

    private static final long serialVersionUID = 2765650092387265178L;
    public static final int RECEIPT_NORMAL = 0;
    public static final int RECEIPT_REFUND = 1;
    public static final int RECEIPT_PAYMENT = 2;
    private static DateFormat m_dateformat = new SimpleDateFormat("hh:mm");
    private String m_sId;
    private int tickettype;
    private int m_iTicketId;
    private java.util.Date m_dDate;
    private Properties attributes;
    private UserInfo m_User;
    private CustomerInfoExt m_Customer;
    private String m_sActiveCash;
    private List<TicketLineInfo> m_aLines;
    private List<PaymentInfo> payments;
    private List<TicketTaxInfo> taxes;

    public TicketInfo() {

        m_sId = UUID.randomUUID().toString();
        tickettype = RECEIPT_NORMAL;
        m_iTicketId = 0; // incrementamos
        m_dDate = new Date();
        attributes = new Properties();
        m_User = null;
        m_Customer = null;
        m_sActiveCash = null;
        m_aLines = new ArrayList<TicketLineInfo>(); // vacio de lineas

        payments = new ArrayList<PaymentInfo>();
        taxes = new ArrayList<TicketTaxInfo>();
    }

 /*   public int  getLineIndex(){

    } */

    public void addLine(TicketLineInfo oLine) {

        oLine.setTicket(m_sId, m_aLines.size());
        m_aLines.add(oLine);
    }

    public void setAttributes(Properties attributes) {
        this.attributes = attributes;
    }

    public void setM_Customer(CustomerInfoExt m_Customer) {
        this.m_Customer = m_Customer;
    }

    public void setM_User(UserInfo m_User) {
        this.m_User = m_User;
    }

    public void setM_aLines(List<TicketLineInfo> m_aLines) {
        this.m_aLines = m_aLines;
    }

    public void setM_dDate(Date m_dDate) {
        this.m_dDate = m_dDate;
    }

    public static void setM_dateformat(DateFormat m_dateformat) {
        TicketInfo.m_dateformat = m_dateformat;
    }

    public void setM_iTicketId(int m_iTicketId) {
        this.m_iTicketId = m_iTicketId;
    }

    public void setM_sActiveCash(String m_sActiveCash) {
        this.m_sActiveCash = m_sActiveCash;
    }

    public void setM_sId(String m_sId) {
        this.m_sId = m_sId;
    }

    public void setPayments(List<PaymentInfo> payments) {
        this.payments = payments;
    }

    public void setTaxes(List<TicketTaxInfo> taxes) {
        this.taxes = taxes;
    }

    public void setTickettype(int tickettype) {
        this.tickettype = tickettype;
    }

    public Properties getAttributes() {
        return attributes;
    }

    public CustomerInfoExt getM_Customer() {
        return m_Customer;
    }

    public UserInfo getM_User() {
        return m_User;
    }

    public List<TicketLineInfo> getM_aLines() {
        return m_aLines;
    }

    public Date getM_dDate() {
        return m_dDate;
    }

    public static DateFormat getM_dateformat() {
        return m_dateformat;
    }

    public int getM_iTicketId() {
        return m_iTicketId;
    }

    public String getM_sActiveCash() {
        return m_sActiveCash;
    }

    public String getM_sId() {
        return m_sId;
    }

    public List<PaymentInfo> getPayments() {
        return payments;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<TicketTaxInfo> getTaxes() {
        return taxes;
    }

    public int getTickettype() {
        return tickettype;
    }

    public String getName() {

        StringBuffer name = new StringBuffer();

        if (m_iTicketId == 0) {
            name.append("(" + m_dateformat.format(m_dDate) + " " + Long.toString(m_dDate.getTime() % 1000) + ")");
        } else {
            name.append(Integer.toString(m_iTicketId));
        }

        return name.toString();
    }

    public List<TicketLineInfo> getLines() {
        return m_aLines;
    }

    public void writeExternal(ObjectOutput out) throws IOException {

        out.writeObject(m_sId);
        out.writeInt(tickettype);
        out.writeInt(m_iTicketId);
        out.writeObject(m_Customer);
        out.writeObject(m_dDate);
        out.writeObject(attributes);
        out.writeObject(m_aLines);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

        m_sId = (String) in.readObject();
        tickettype = in.readInt();
        m_iTicketId = in.readInt();
        m_Customer = (CustomerInfoExt) in.readObject();
        m_dDate = (Date) in.readObject();
        attributes = (Properties) in.readObject();
        m_aLines = (List<TicketLineInfo>) in.readObject();
        m_User = null;
        m_sActiveCash = null;

        payments = new ArrayList<PaymentInfo>();
        taxes = null;
    }
}
