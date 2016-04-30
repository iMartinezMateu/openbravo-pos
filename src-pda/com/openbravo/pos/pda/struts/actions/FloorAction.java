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

package com.openbravo.pos.pda.struts.actions;

import com.openbravo.pos.pda.bo.RestaurantManager;
import com.openbravo.pos.ticket.Floor;
import com.openbravo.pos.ticket.Place;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.DynaActionForm;

/**
 *
 * @author jaroslawwozniak
 */
public class FloorAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private final static String SUCCESS = "success";
    private final static String FAILURE = "failure";

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        DynaActionForm inputFormPlace = (DynaActionForm) form;
        RestaurantManager manager = new RestaurantManager();
        //gets floors
        ArrayList<Floor> floors = new ArrayList<Floor>();
        floors = (ArrayList<Floor>) manager.findAllFloors();
        List busyTables = new ArrayList<Floor>();
        //floorId
        String floorId = (String) inputFormPlace.get("floorId");
        try {
            String deletedPlace = (String) inputFormPlace.get("place");
            manager.deleteTicket(deletedPlace);
        } catch (java.lang.IllegalArgumentException ex) {
            //no receipt to delete so just continue and show floors
        }
        List places = new ArrayList<Place>();
        if (floorId.equals("") || floorId.equals("undefined")) {
            places = manager.findAllPlaces(floors.get(0).getId());
            floorId = floors.get(0).getId();
            busyTables = manager.findAllBusyTable(floorId);
        } else {
            places = manager.findAllPlaces(floorId);
            busyTables = manager.findAllBusyTable(floorId);
        }

        request.setAttribute("i", 0);
        request.setAttribute("busy", busyTables);
        request.getSession().setAttribute("places", places);
        request.setAttribute("floorId", floorId);
        request.setAttribute("floors", floors);

        return mapping.findForward(SUCCESS);


    }
}