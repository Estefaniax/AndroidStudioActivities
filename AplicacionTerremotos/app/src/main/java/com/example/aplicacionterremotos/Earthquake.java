// Earthquake.java
package com.example.aplicacionterremotos;

public class Earthquake {
    public final double latitude;
    public final double longitude;
    public final double magnitude;
    public final String place;

    public Earthquake(double latitude, double longitude, double magnitude, String place) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.magnitude = magnitude;
        this.place = place;
    }
}