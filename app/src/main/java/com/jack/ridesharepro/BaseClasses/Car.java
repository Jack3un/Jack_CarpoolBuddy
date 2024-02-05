package com.jack.ridesharepro.BaseClasses;

public class Car extends Vehicle{
    int range;
    String CarType;

    public Car() {
    }


    public Car(String licensePlate, String brand, int seatsAvailable, double price, String type, String owner, String id, int mileage, int range, String carType) {
        super(licensePlate, brand, seatsAvailable, price, type, owner, id, mileage);
        this.range = range;
        CarType = carType;
    }

    public Car(String licensePlate, String brand, int seatsAvailable, double price, String type, String owner, String id, int mileage, int range) {
        super(licensePlate, brand, seatsAvailable, price, type, owner, id, mileage);
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
