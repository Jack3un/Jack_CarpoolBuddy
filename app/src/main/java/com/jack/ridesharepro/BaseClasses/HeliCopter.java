package com.jack.ridesharepro.BaseClasses;

public class HeliCopter extends Vehicle{
    int maxAltitude;
    int maxAirSpeed;

    public HeliCopter() {
    }

    public HeliCopter(String licensePlate, String brand, int seatsAvailable, double price, String type, String owner, String id, int mileage, int maxAltitude, int maxAirSpeed) {
        super(licensePlate, brand, seatsAvailable, price, type, owner, id, mileage);
        this.maxAltitude = maxAltitude;
        this.maxAirSpeed = maxAirSpeed;
    }

    public int getMaxAltitude() {
        return maxAltitude;
    }

    public void setMaxAltitude(int maxAltitude) {
        this.maxAltitude = maxAltitude;
    }

    public int getMaxAirSpeed() {
        return maxAirSpeed;
    }

    public void setMaxAirSpeed(int maxAirSpeed) {
        this.maxAirSpeed = maxAirSpeed;
    }
}
