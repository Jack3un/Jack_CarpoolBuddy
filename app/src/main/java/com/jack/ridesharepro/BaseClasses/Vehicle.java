package com.jack.ridesharepro.BaseClasses;

public class Vehicle {

    private String licensePlate;
    private int mileage;


    private String brand;
    private int seatsAvailable;
    private double price;
    private String type;
    private String owner;
    private boolean open;
    private String id;

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Vehicle() {
    }

    public Vehicle(String licensePlate, String brand, int seatsAvailable, double price, String type, String owner,String id,int mileage) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.seatsAvailable = seatsAvailable;
        this.price = price;
        this.type = type;
        this.owner = owner;
        open = true;
        this.id = id;
        this.mileage = mileage;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
