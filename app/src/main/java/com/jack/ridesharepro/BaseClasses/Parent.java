package com.jack.ridesharepro.BaseClasses;

import java.util.ArrayList;

public class Parent extends User{
    private ArrayList<String> childrenUIDs;

    public Parent(String type, String name, String email, String password, double lon, double lat, int mileage, ArrayList<String> childrenUIDs) {
        super(type,name,email,password, lon, lat, mileage);
        this.childrenUIDs = childrenUIDs;
    }

    public ArrayList<String> getChildrenUIDs() {
        return childrenUIDs;
    }

    public void setChildrenUIDs(ArrayList<String> childrenUIDs) {
        this.childrenUIDs = childrenUIDs;
    }
}
