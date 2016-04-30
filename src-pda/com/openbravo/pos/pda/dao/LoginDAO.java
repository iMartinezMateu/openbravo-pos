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

import com.openbravo.pos.pda.util.StringUtils;
import com.openbravo.pos.ticket.UserInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jaroslawwozniak
 */
public class LoginDAO extends BaseJdbcDAO {

    public UserInfo findUser(String login, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserInfo user = null;
        String sqlStr = "SELECT NAME, APPPASSWORD FROM PEOPLE WHERE NAME = ? AND APPPASSWORD ";
        String end = "";
        if (password.equals("")) {
            end = "IS NULL";
        } else {
            end = " = ?";
        }

        try {
            //get connection
            con = getConnection();
            //prepare statement
            ps = con.prepareStatement(sqlStr + end);
            ps.setString(1, login);
            if (!password.equals("")) {
                ps.setString(2, StringUtils.hashString(password));
            }

            //execute
            rs = ps.executeQuery();
            //transform to VO
            user = map2VO(rs);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                // close the resources 
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqlee) {
                sqlee.printStackTrace();
            }
        }

        return user;
    }

    @Override
    protected UserInfo map2VO(ResultSet rs) throws SQLException {
        UserInfo user = new UserInfo();
        rs.next();
        user.setLogin(rs.getString("name"));
        if (rs.getString("apppassword") == null) {
            user.setPassword("");
        } else {
            user.setPassword(rs.getString("apppassword"));
        }
        return user;
    }
}
