package main.service;

import main.Data.User;
import main.Data.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VehicleService {

    // user to Vehicle Map
    private Map<String, Map<String, Vehicle>> vehicleMap;

    public VehicleService() {
        this.vehicleMap = new HashMap<>();
    }

    public Vehicle createVehicle(User user, String name, String regNo) {
        String id = UUID.randomUUID().toString();
        Vehicle vehicle = new Vehicle(id,user,name,regNo);
        addVehicleToUser(user,vehicle);
        return vehicle;
    }

    private void addVehicleToUser(User user, Vehicle vehicle) {
        Map<String,Vehicle> userVehicles = vehicleMap.get(user.getId());
        if(userVehicles == null) {
            vehicleMap.put(user.getId(), new HashMap<>());
        }
        vehicleMap.get(user.getId()).put(vehicle.getId(), vehicle);
    }

    public boolean validateVehicle(Vehicle vehicle) {
        String userID = vehicle.getUser().getId();
        String vehicleID = vehicle.getId();

        Map<String,Vehicle> userVehicles = vehicleMap.get(userID);
        if(userVehicles == null) {
            return false;
        }
        return userVehicles.get(vehicleID) != null;

    }


}
