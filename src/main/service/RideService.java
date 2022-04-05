package main.service;

import main.Data.*;

import java.util.*;

import static java.lang.Math.max;

public class RideService {
    private Map<User, List<Ride>> offeredRideMap;
    private Map<String, List<Ride>> originDestinationRideMap;
    //    private RideStrategyFactory rideStrategyFactory;
    private Map<User, List<Ride>> takenRideMap;

    public RideService() {
        this.offeredRideMap = new HashMap<>();
        this.originDestinationRideMap = new HashMap<>();
//        this.rideStrategyFactory = new RideStrategyFactory();
        this.takenRideMap = new HashMap<>();
    }

    public Ride createRide(User user, Vehicle vehicle, City origin, City destination, int availableSeats, Date startTime, int durationInHours) {
        // validation check if the vehicle is free

        String id = UUID.randomUUID().toString();


        Ride ride = new Ride(id, user, vehicle, origin, destination, availableSeats, startTime, durationInHours);

        offeredRideMap.computeIfAbsent(user, k -> new ArrayList<>()).add(ride);

        // adding to ridemap
        String originDestinationKey = createOriginDestinationKey(origin, destination);
        originDestinationRideMap.computeIfAbsent(originDestinationKey, k -> new ArrayList<>()).add(ride);
        return ride;
    }

    public Ride selectRide(User user, City origin, City destination, int requiredSeats, String rideType) {
        String originDestinationKey = createOriginDestinationKey(origin, destination);

        List<Ride> rides = originDestinationRideMap.get(originDestinationKey);

//        RideSelectionStrategy rideSelectionStrategy = rideStrategyFactory.getRideStrategy(rideType);

        Ride ride = selectRideBasedOnStrategy(rides, user, rideType);

        if (ride == null) {
            throw new RuntimeException("No ride found");
        }


        takenRideMap.computeIfAbsent(user, k -> new ArrayList<>()).add(ride);

        ride.setAvailableSeats(ride.getAvailableSeats() - 1);
        return ride;
    }

    private String createOriginDestinationKey(City origin, City destination) {
        String o = origin.toString();
        String d = destination.toString();

        return o + ":" + d;
    }

    public List<Ride> offeredRides(User user) {
        List<Ride> rides = offeredRideMap.get(user);
        return rides != null ? rides : Collections.emptyList();
    }

    public List<Ride> takenRides(User user) {
        List<Ride> rides = takenRideMap.get(user);

        return rides != null ? rides : Collections.emptyList();
    }

    public Ride selectRideBasedOnStrategy(List<Ride> rides, User user, String rideType) {
        if (rides == null || rides.size() == 0) {
            return null;
        }
        Optional<Ride> optionalRide = null;


        // Strategy to Fetch for Maximum Vacants Seat
        if (rideType=="Vacant" || rideType==""){
            optionalRide = Optional.of(rides.stream()
                    .max(Comparator.comparing(ride -> ride.getAvailableSeats())).get());
        }else {
           // Strategy to Fetch Based on vehicle
            optionalRide = rides.stream()
                    .filter(ride -> ride.getVehicle().toString().contains(rideType.toString()))
                    .findAny();
        }
        if (optionalRide.isEmpty()) {
            return null;
        }

        return optionalRide.get();
    }

    private int maxSeats(Ride ride1, Ride ride2) {
        int ride1Seats = ride1.getAvailableSeats();
        int ride2Seats = ride2.getAvailableSeats();
        return max(ride2Seats,ride1Seats);
    }

}