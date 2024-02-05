package com.jack.ridesharepro.BaseClasses;

import java.util.ArrayList;

public class Student extends User{
    private String graduatingYear;
    private ArrayList<String> parentUIDs;

    public Student(String type, String name, String email, String password, double lon, double lat, int mileage, String graduatingYear, ArrayList<String> parentUIDs) {
        super(type,name,email,password, lon, lat, mileage);
        this.graduatingYear = graduatingYear;
        this.parentUIDs = parentUIDs;
    }


    public String getGraduatingYear() {
        return graduatingYear;
    }

    public void setGraduatingYear(String graduatingYear) {
        this.graduatingYear = graduatingYear;
    }


    public ArrayList<String> getParentUIDs() {
        return parentUIDs;
    }

    public void setParentUIDs(ArrayList<String> parentUIDs) {
        this.parentUIDs = parentUIDs;
    }

}
