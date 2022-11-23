package com.dfms.dairy_farm_management_system.models;

public enum Availability {
    AVAILABLE("Available"),
    NOT_AVAILABLE("Not Available");

    private String availability;

    Availability(String availability) {
        this.availability = availability;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
