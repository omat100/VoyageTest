package in.th.transitops.dto;

import in.th.transitops.entity.Trip;
import in.th.transitops.enums.TripLifecycle;

public record TripResponse(
        long id,
        String source,
        String destination,
        VehicleResponse vehicle,
        DriverResponse driver,
        int weight,
        float plannedDistance,
        TripLifecycle lifecycle
) {

    public static TripResponse fromEntity(Trip trip) {
        return new TripResponse(
                trip.getId(),
                trip.getSource(),
                trip.getDestination(),
                VehicleResponse.fromEntity(trip.getVehicle()),
                DriverResponse.fromEntity(trip.getDriver()),
                trip.getWeight(),
                trip.getPlannedDistance(),
                trip.getLifecycle()
        );
    }
}
