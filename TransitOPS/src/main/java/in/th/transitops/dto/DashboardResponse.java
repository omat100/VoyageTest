package in.th.transitops.dto;

import in.th.transitops.entity.Trip;

import java.util.List;

public record DashboardResponse(
    List<Trip> activeAndPendingTrips,
    long availableVehicle,
    long driversOnDuty,
    long activeVehicles
) {
}