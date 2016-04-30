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

package com.openbravo.pos.pda.dao;

import com.openbravo.pos.ticket.TicketInfo;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jaroslawwozniak
 */
public class TicketDAO extends BaseJdbcDAO implements Serializable {

    public TicketInfo getTicket(String id) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sqlStr = "Select CONTENT from SHAREDTICKETS where ID = ?";
        TicketInfo ticket = new TicketInfo();
        try {
            //get connection
            con = getConnection();
            //prepare statement
            ps = con.prepareStatement(sqlStr);

            ps.setString(1, id);
            //execute
            rs = ps.executeQuery();
            //transform to VO
            rs.next();
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(rs.getBinaryStream(1)));
            ticket = (TicketInfo) in.readObject();

        } catch (Exception ex) {
            //Logger.getLogger(TicketDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return ticket;
    }

    public void initTicket(String id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlStr = "INSERT INTO SHAREDTICKETS (ID, NAME,CONTENT) VALUES (?, ?, ?)";
        TicketInfo ticket = new TicketInfo();
        try {
            //get connection
            con = getConnection();
            //prepare statement
            ps = con.prepareStatement(sqlStr);
            ps.setString(1, id);
            ps.setString(2, ticket.getName());
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bytes);
            out.writeObject(ticket);
            ps.setBytes(3, bytes.toByteArray());
            //execute
            ps.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(TicketDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateTicket(String ticketId, TicketInfo ticket) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlStr = "UPDATE SHAREDTICKETS SET CONTENT = ? WHERE ID = ?";
        try {
            //get connection
            con = getConnection();
            //prepare statement
            ps = con.prepareStatement(sqlStr);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bytes);
            out.writeObject(ticket);
            ps.setBytes(1, bytes.toByteArray());
            ps.setString(2, ticketId);
            //execute
            ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(TicketDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertTicket(TicketInfo ticket) {

        Connection con = null;
        PreparedStatement ps = null;
        String sqlStr = "INSERT INTO SHAREDTICKETS (ID, NAME, CONTENT) VALUES (?, ?, ?)";

        try {
            //get connection
            con = getConnection();
            //prepare statement
            ps = con.prepareStatement(sqlStr);
            ps.setString(1, ticket.getM_sId());
            ps.setString(2, ticket.getName());
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bytes);
            out.writeObject(ticket);
            ps.setBytes(3, bytes.toByteArray());

            //execute
            ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(TicketDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteTicket(String id) {
        Connection con = null;
        PreparedStatement ps = null;
        String sqlStr = "DELETE FROM SHAREDTICKETS WHERE ID = ?";
        try {
            //get connection
            con = getConnection();
            //prepare statement
            ps = con.prepareStatement(sqlStr);

            ps.setString(1, id);
            //execute
            ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(TicketDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected TicketInfo map2VO(ResultSet rs) throws SQLException {
        ObjectInputStream in = null;
        TicketInfo ticket = new TicketInfo();

        return ticket;

    }
}
