package com.dfms.dairy_farm_management_system.controllers;

import junit.framework.TestCase;

public class DashboardControllerTest extends TestCase {

    public void testGetSalesOfSpecificDay() {
        DashboardController dashboardController = new DashboardController();
        int actual = dashboardController.getSalesOfSpecificDay("Fri", "animals_sales");
        assertEquals(2, actual);
    }

    public void testGetEarningsOfSpecificDay() {
    }
}