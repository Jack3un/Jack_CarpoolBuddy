package com.jack.ridesharepro.BaseClasses;

public class Segway extends Vehicle{
    int range;
    int weightCapacity;

    public Segway() {
    }

    public Segway(String licensePlate, String brand, int seatsAvailable, double price, String type, String owner, String id, int mileage, int range, int weightCapacity) {
        super(licensePlate, brand, seatsAvailable, price, type, owner, id, mileage);
        this.range = range;
        this.weightCapacity = weightCapacity;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getWeightCapacity() {
        return weightCapacity;
    }

    public void setWeightCapacity(int weightCapacity) {
        this.weightCapacity = weightCapacity;
    }
}
