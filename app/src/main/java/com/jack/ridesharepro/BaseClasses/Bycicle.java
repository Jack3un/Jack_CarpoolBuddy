package com.jack.ridesharepro.BaseClasses;

public class Bycicle extends Vehicle{
    String bycicleType;
    int weight;
    int weightCapacity;

    public Bycicle() {
    }

    public Bycicle(String licensePlate, String brand, int seatsAvailable, double price, String type, String owner, String id, int mileage, String bycicleType, int weight, int weightCapacity) {
        super(licensePlate, brand, seatsAvailable, price, type, owner, id, mileage);
        this.bycicleType = bycicleType;
        this.weight = weight;
        this.weightCapacity = weightCapacity;
    }

    public String getBycicleType() {
        return bycicleType;
    }

    public void setBycicleType(String bycicleType) {
        this.bycicleType = bycicleType;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeightCapacity() {
        return weightCapacity;
    }

    public void setWeightCapacity(int weightCapacity) {
        this.weightCapacity = weightCapacity;
    }
}
