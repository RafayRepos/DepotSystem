package Model;

import java.util.HashMap;

public class ParcelMap {
    private HashMap<String, Parcel> parcels;

    public ParcelMap() {
        parcels = new HashMap<>();
    }

    // Add parcel to the map
    public void addParcel(Parcel parcel) {
        parcels.put(parcel.getId(), parcel);
    }

    // Find parcel by ID
    public Parcel findParcel(String id) {
        return parcels.get(id);
    }

    // Check if the parcel exists
    public boolean containsParcel(String id) {
        return parcels.containsKey(id);
    }

    // Remove parcel by ID
    public void removeParcel(String id) {
        parcels.remove(id);
    }

    // New method to get parcel by ID (same as findParcel)
    public Parcel getParcelById(String id) {
        return findParcel(id); // Uses the existing method for finding the parcel
    }
}
