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

import com.openbravo.pos.ticket.Place;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jaroslawozniak
 */
public class PlaceDAO extends BaseJdbcDAO {

    public List<Place> findAllPlaceByFloor(String floorId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Place> vos = null;
        String sqlStr = "Select * from PLACES where Floor = ? order by NAME";

        try {
            //get connection
            con = getConnection();
            //prepare statement
            ps = con.prepareStatement(sqlStr);
            ps.setString(1, floorId);
            //execute
            rs = ps.executeQuery();
            //transform to VO
            vos = transformSet(rs);
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

        return vos;
    }

    public List<Place> findAllBusyPlacesByFloor(String floorId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Place> vos = null;
        String sqlStr = "SELECT * FROM PLACES P, SHAREDTICKETS S WHERE FLOOR = ? AND P.ID = S.ID";

        try {
            //get connection
            con = getConnection();
            //prepare statement
            ps = con.prepareStatement(sqlStr);
            ps.setString(1, floorId);
            //execute
            rs = ps.executeQuery();
            //transform to VO
            vos = transformSet(rs);
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

        return vos;
    }

    public Place findPlaceById(String placeId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Place vo = null;
        String sqlStr = "Select * from PLACES where ID = ?";

        try {
            //get connection
            con = getConnection();
            //prepare statement
            ps = con.prepareStatement(sqlStr);
            ps.setString(1, placeId);
            //execute
            rs = ps.executeQuery();
            //transform to VO
            rs.next();
            vo = map2VO(rs);
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

        return vo;
    }

    public void setTableBusy(String placeId, String placeName) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlStr = "INSERT INTO SHAREDTICKETS (ID, NAME) VALUES (?, ?)";

        try {
            //get connection
            con = getConnection();
            //prepare statement
            ps = con.prepareStatement(sqlStr);
            ps.setString(1, placeId);
            ps.setString(2, placeName);
            //execute
            rs = ps.executeQuery();

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

    }

    @Override
    protected Place map2VO(ResultSet rs) throws SQLException {

        Place place = new Place();
        place.setId(rs.getString("id"));
        place.setName(rs.getString("name"));
        place.setX(rs.getInt("x"));
        place.setY(rs.getInt("y"));
        place.setFloor(rs.getString("floor"));

        return place;
    }
}
