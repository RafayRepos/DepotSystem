package Model;

import java.util.HashMap;
import java.util.Map;

public class Parcel {
    private String id;
    private int daysInDepot;
    private double weight;
    private int length, width, height;
    private String status; // Possible values could include "Received", "In Transit", "Delivered", "Processed"

    private static Map<String, Parcel> parcelMap = new HashMap<>();  // To store parcels

    public Parcel(String id, int daysInDepot, double weight, int length, int width, int height) {
        this.id = id;
        this.daysInDepot = daysInDepot;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.status = "Received"; // Default status when a parcel is created

        // Add parcel to the static map for global access
        parcelMap.put(id, this);
    }

    // Getters
    public String getId() {
        return id;
    }

    public int getDaysInDepot() {
        return daysInDepot;
    }

    public double getWeight() {
        return weight;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setDaysInDepot(int daysInDepot) {
        if (daysInDepot < 0) throw new IllegalArgumentException("Days in depot cannot be negative.");
        this.daysInDepot = daysInDepot;
    }

    public void setWeight(double weight) {
        if (weight < 0) throw new IllegalArgumentException("Weight cannot be negative.");
        this.weight = weight;
    }

    public void setDimensions(int length, int width, int height) {
        if (length < 0 || width < 0 || height < 0) {
            throw new IllegalArgumentException("Dimensions cannot be negative.");
        }
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty.");
        }
        this.status = status;
    }

    public void markAsProcessed() {
        this.status = "Processed";
    }

    public double calculateFee() {
        double baseFee = 10.0;
        double weightFee = weight * 0.5;
        double storageFee = daysInDepot * 0.75;
        return baseFee + weightFee + storageFee;
    }

    // Static method to get a parcel by ID
    public static Parcel getParcelById(String parcelId) {
        return parcelMap.get(parcelId);
    }

    // Optional: Method to remove a parcel by ID
    public static void removeParcelById(String parcelId) {
        parcelMap.remove(parcelId);
    }

    // Optional: List all parcels (for debugging purposes)
    public static void listAllParcels() {
        for (Parcel parcel : parcelMap.values()) {
            System.out.println(parcel);
        }
    }

    @Override
    public String toString() {
        return String.format("Parcel{id='%s', daysInDepot=%d, weight=%.2f, length=%d, width=%d, height=%d, status='%s'}",
                id, daysInDepot, weight, length, width, height, status);
    }
}
