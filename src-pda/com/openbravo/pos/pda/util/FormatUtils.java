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

package com.openbravo.pos.pda.util;

import java.text.NumberFormat;

/**
 *
 * @author jaroslawwozniak
 */
public class FormatUtils {

    public static String formatCurrency(Object value) {
        NumberFormat m_currencyformat = NumberFormat.getCurrencyInstance();
        return m_currencyformat.format(((Number) value).doubleValue());
    }

    public static String formatDouble(Object value) {
        NumberFormat doubleFormat = NumberFormat.getNumberInstance();

        return doubleFormat.format(((Number) value).doubleValue());
    }
}
