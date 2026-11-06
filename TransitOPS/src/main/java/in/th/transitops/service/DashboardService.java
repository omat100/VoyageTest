package in.th.transitops.service;

import in.th.transitops.dto.DashboardResponse;
import in.th.transitops.entity.Trip;
import in.th.transitops.enums.DriverStatus;
import in.th.transitops.enums.TripLifecycle;
import in.th.transitops.enums.VehicleStatus;
import in.th.transitops.repos.DriverRepository;
import in.th.transitops.repos.TripRepository;
import in.th.transitops.repos.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private TripService tripService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private DriverService driverService;

    public DashboardResponse dashboard(){
        List<Trip> activeAndPendingTrips = tripService.findByActiveAndPending();
        long availableVehicle = vehicleService.countByStatus(VehicleStatus.AVAILABLE);
        long driversOnDuty = driverService.countByStatus(DriverStatus.ON_TRIP);
        long activeVehicles = activeAndPendingTrips.stream()
                .filter(t -> t.getLifecycle() == TripLifecycle.DISPATCHED)
                .count();
        return new DashboardResponse(activeAndPendingTrips,availableVehicle,driversOnDuty,activeVehicles);
    }
}
