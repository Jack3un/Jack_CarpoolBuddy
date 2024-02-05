package com.jack.ridesharepro.BaseClasses;

import java.util.ArrayList;

public class User {

    private String type;
    private String name;
    private String email;
    private String password;

    private double lon;
    private double lat;
    private int mileage;

    private  ArrayList<Vehicle> userRides;;
    private ArrayList<Vehicle> userCars;;


    public int getMileage() {
        return mileage;
    }

    public ArrayList<Vehicle> getUserRides() {
        return userRides;
    }

    public void setUserRides(ArrayList<Vehicle> userRides) {
        this.userRides = userRides;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
    public User() {
    }
    public User(String type, String name, String email, String password, double lon, double lat, int mileage) {
        this.type = type;
        this.name = name;
        this.email = email;
        this.password = password;
        this.lon = lon;
        this.lat = lat;
        this.mileage=mileage;
        this.userRides = new ArrayList<>();  // 设置默认值
        this.userCars = new ArrayList<>();   // 设置默认值
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }


    public ArrayList<Vehicle> getUserCars() {
        return userCars;
    }

    public void setUserCars(ArrayList<Vehicle> userCars) {
        this.userCars = userCars;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
