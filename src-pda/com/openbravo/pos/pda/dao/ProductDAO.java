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

import com.openbravo.pos.ticket.ProductInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jaroslawwozniak
 */
public class ProductDAO extends BaseJdbcDAO {

    public List<ProductInfo> findAllAuxiliars(String id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ProductInfo> vos = null;
        String sqlStr = "SELECT * FROM PRODUCTS P, PRODUCTS_COM COM WHERE COM.PRODUCT=? AND P.ID = COM.PRODUCT2 ORDER BY P.NAME";

        try {
            //get connection
            con = getConnection();
            //prepare statement
            ps = con.prepareStatement(sqlStr);
            ps.setString(1, id);
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

    public ProductInfo findProductById(String productId) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ProductInfo vos = null;
        String sqlStr = "SELECT * FROM PRODUCTS WHERE ID=?";

        try {
            //get connection
            con = getConnection();
            //prepare statement
            ps = con.prepareStatement(sqlStr);
            ps.setString(1, productId);
            //execute
            rs = ps.executeQuery();
            //transform to VO
            vos = (ProductInfo) transformSet(rs).get(0);

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

    public List<ProductInfo> findProductsByCategory(String categoryId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ProductInfo> vos = null;
        String sqlStr = isPostgre() ? "SELECT * FROM PRODUCTS P, PRODUCTS_CAT PCAT WHERE P.CATEGORY= ? AND P.ID = PCAT.PRODUCT" +
                " AND P.ISCOM = FALSE ORDER BY PCAT.CATORDER, P.NAME" : "SELECT * FROM PRODUCTS P, PRODUCTS_CAT PCAT WHERE P.CATEGORY= ? AND P.ID = PCAT.PRODUCT" +
                " AND P.ISCOM = 0 ORDER BY PCAT.CATORDER, P.NAME";

        try {
            //get connection
            con = getConnection();
            //prepare statement
            ps = con.prepareStatement(sqlStr);
            ps.setString(1, categoryId);
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

    @Override
    protected ProductInfo map2VO(ResultSet rs) throws SQLException {
        ProductInfo product = new ProductInfo();
        product.setId(rs.getString("id"));
        product.setRef(rs.getString("reference"));
        product.setCode(rs.getString("code"));
        product.setName(rs.getString("name"));
        product.setPriceBuy(rs.getDouble("pricebuy"));
        product.setPriceSell(rs.getDouble("pricesell"));
        product.setCategoryId(rs.getString("category"));
        product.setTaxcat(rs.getString("taxcat"));
        product.setCom(rs.getBoolean("iscom"));
        product.setScale(rs.getBoolean("isscale"));

        return product;
    }
}
