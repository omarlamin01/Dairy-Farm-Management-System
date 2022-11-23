package com.dfms.dairy_farm_management_system.models;

public interface Model {
    public boolean save();
    public boolean update();

    abstract boolean delete();
}
