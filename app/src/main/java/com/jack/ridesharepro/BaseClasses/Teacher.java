package com.jack.ridesharepro.BaseClasses;

public class Teacher extends User{
    private String inSchoolTitle;

    public Teacher(String type, String name, String email, String password, double lon, double lat, int mileage, String inSchoolTitle) {
        super(type,name,email,password, lon, lat, mileage);
        this.inSchoolTitle = inSchoolTitle;
    }


    public String getInSchoolTitle() {
        return inSchoolTitle;
    }

    public void setInSchoolTitle(String inSchoolTitle) {
        this.inSchoolTitle = inSchoolTitle;
    }

}
