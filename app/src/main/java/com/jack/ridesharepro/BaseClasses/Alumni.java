package com.jack.ridesharepro.BaseClasses;

public class Alumni extends User {
    private String graduateYear;

    public Alumni(String type, String name, String email, String password, double lon, double lat, int mileage, String graduateYear) {
        super(type,name,email,password, lon, lat, mileage);
        this.graduateYear = graduateYear;
    }


    public String getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(String graduateYear) {
        this.graduateYear = graduateYear;
    }

}
