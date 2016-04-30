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

package com.openbravo.pos.pda.bo;

import com.openbravo.pos.pda.dao.CategoryDAO;
import com.openbravo.pos.pda.dao.FloorDAO;
import com.openbravo.pos.pda.dao.LoginDAO;
import com.openbravo.pos.pda.dao.PlaceDAO;
import com.openbravo.pos.pda.dao.ProductDAO;
import com.openbravo.pos.pda.dao.TaxDAO;
import com.openbravo.pos.pda.dao.TicketDAO;
import com.openbravo.pos.pda.dao.TicketLineDAO;
import com.openbravo.pos.ticket.CategoryInfo;
import com.openbravo.pos.ticket.Floor;
import com.openbravo.pos.ticket.Place;
import com.openbravo.pos.ticket.ProductInfo;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import com.openbravo.pos.ticket.UserInfo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jaroslawwozniak
 * @version 1.0
 */
public class RestaurantManager {

    private FloorDAO floor;
    private PlaceDAO place;
    private TicketDAO ticket;
    private TicketLineDAO lines;
    private ProductDAO product;
    private LoginDAO login;
    private CategoryDAO category;
    private TaxDAO tax;
    private TaxesLogic taxesLogic;

    public List<Floor> findAllFloors() {
        floor = new FloorDAO();

        return floor.findAllFloors();
    }

    public List<CategoryInfo> findAllSubcategories(String id) {
        category = new CategoryDAO();

        return category.findAllSubcategories(id);
    }

    public List<ProductInfo> findAuxiliars(String id) {
        product = new ProductDAO();

        return product.findAllAuxiliars(id);
    }

    public String findFloorNameById(String floorId) {
        floor = new FloorDAO();

        return floor.findFloorById(floorId).getName();
    }

    public Floor findFloorById(String floorId) {
        floor = new FloorDAO();

        return floor.findFloorById(floorId);
    }

    public List<Place> findAllPlaces(String floor) {
        place = new PlaceDAO();

        return place.findAllPlaceByFloor(floor);
    }

    public List<Place> findAllBusyTable(String floor) {
        place = new PlaceDAO();

        return place.findAllBusyPlacesByFloor(floor);
    }

    public String findTheFirstFloor() {
        floor = new FloorDAO();

        return floor.findAllFloors().get(0).getId();
    }

    public TicketInfo findTicket(String id) {
        ticket = new TicketDAO();

        return ticket.getTicket(id);
    }

    public void initTicket(String id) {
        ticket = new TicketDAO();
        ticket.initTicket(id);
    }

    public void deleteTicket(String id) {
        TicketDAO ticket = new TicketDAO();
        ticket.deleteTicket(id);
    }

    public List<TicketLineInfo> findTicketLines(String ticketId) {
        lines = new TicketLineDAO();

        return lines.findLinesByTicket(ticketId);
    }

    public ProductInfo findProductById(String productId) {
        product = new ProductDAO();
        if (productId.contains("+")) {
            String[] str = productId.split("+");
            productId = str[str.length - 1];
        }
        return product.findProductById(productId);
    }

    public UserInfo findUser(String aLogin, String password) {
        login = new LoginDAO();

        return login.findUser(aLogin, password);
    }

    public List<ProductInfo> findProductsByCategory(String categoryId) {
        product = new ProductDAO();

        return product.findProductsByCategory(categoryId);
    }

    public List<String> findAllTaxRatesByCategory(List<ProductInfo> products) {
        List<String> list = new ArrayList<String>();
        tax = new TaxDAO();
        taxesLogic = new TaxesLogic(tax.getTaxList());
        for(ProductInfo prod: products) {
            list.add(String.valueOf(taxesLogic.getTaxInfo(prod.getTaxcat()).getRate()));
        }

        return list;
    }

    public List<CategoryInfo> findAllCategories() {
        category = new CategoryDAO();

        return category.findAllCategories();
    }

    public String findPlaceNameById(String placeId) {
        place = new PlaceDAO();

        return place.findPlaceById(placeId).getName();
    }

    public Place findPlaceById(String placeId) {
        place = new PlaceDAO();

        return place.findPlaceById(placeId);
    }

    public void addLineToTicket(String ticketId, String productId) {
        ticket = new TicketDAO();
        product = new ProductDAO();
        category = new CategoryDAO();
        tax = new TaxDAO();
        taxesLogic = new TaxesLogic(tax.getTaxList());

        TicketInfo obj = ticket.getTicket(ticketId);
        ProductInfo productObj = product.findProductById(productId);
        TicketLineInfo line = new TicketLineInfo(productObj, productObj.getPriceSell(), taxesLogic.getTaxInfo(productObj.getTaxcat()));
        obj.addLine(line);
        ticket.updateTicket(ticketId, obj);
        refreshTax(obj);
    }

    public void updateLineFromTicket(String ticketId, TicketInfo aticket) {
        ticket = new TicketDAO();
        ticket.updateTicket(ticketId, aticket);
    }

    public BigDecimal getTotalOfaTicket(String place) {
        double total = 0;
        for (TicketLineInfo line : findTicket(place).getM_aLines()) {
            total += line.getMultiply() * line.getPrice();
        }
        return BigDecimal.valueOf(total);
    }

    public void insertTicket(TicketInfo aticket) {
        ticket = new TicketDAO();
        ticket.insertTicket(aticket);
    }

    public void refreshTax(TicketInfo ticket) {
        for (TicketLineInfo line : ticket.getLines()) {
            line.setTax(taxesLogic.getTaxInfo(line.getProductTaxCategoryID(), ticket.getM_Customer()));
        }
    }
}
