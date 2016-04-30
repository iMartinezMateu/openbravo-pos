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

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Properties;

/**
 *
 * @author jaroslawwozniak
 */
public class ProductInfoExt implements Serializable {

    protected String m_ID;
    protected String m_sRef;
    protected String m_sCode;
    protected String m_sName;
    protected boolean m_bCom;
    protected boolean m_bScale;
    protected String categoryid;
    protected String taxcategoryid;
    protected String attributesetid;
    protected double m_dPriceBuy;
    protected double m_dPriceSell;
    protected BufferedImage m_Image;
    protected Properties attributes;

    public Properties getAttributes() {
        return attributes;
    }

    public void setAttributes(Properties attributes) {
        this.attributes = attributes;
    }

    public String getAttributesetid() {
        return attributesetid;
    }

    public void setAttributesetid(String attributesetid) {
        this.attributesetid = attributesetid;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getM_ID() {
        return m_ID;
    }

    public void setM_ID(String m_ID) {
        this.m_ID = m_ID;
    }

    public BufferedImage getM_Image() {
        return m_Image;
    }

    public void setM_Image(BufferedImage m_Image) {
        this.m_Image = m_Image;
    }

    public boolean isM_bCom() {
        return m_bCom;
    }

    public void setM_bCom(boolean m_bCom) {
        this.m_bCom = m_bCom;
    }

    public boolean isM_bScale() {
        return m_bScale;
    }

    public void setM_bScale(boolean m_bScale) {
        this.m_bScale = m_bScale;
    }

    public double getM_dPriceBuy() {
        return m_dPriceBuy;
    }

    public void setM_dPriceBuy(double m_dPriceBuy) {
        this.m_dPriceBuy = m_dPriceBuy;
    }

    public double getM_dPriceSell() {
        return m_dPriceSell;
    }

    public void setM_dPriceSell(double m_dPriceSell) {
        this.m_dPriceSell = m_dPriceSell;
    }

    public String getM_sCode() {
        return m_sCode;
    }

    public void setM_sCode(String m_sCode) {
        this.m_sCode = m_sCode;
    }

    public String getM_sName() {
        return m_sName;
    }

    public void setM_sName(String m_sName) {
        this.m_sName = m_sName;
    }

    public String getM_sRef() {
        return m_sRef;
    }

    public void setM_sRef(String m_sRef) {
        this.m_sRef = m_sRef;
    }

    public String getTaxcategoryid() {
        return taxcategoryid;
    }

    public void setTaxcategoryid(String taxcategoryid) {
        this.taxcategoryid = taxcategoryid;
    }
}
