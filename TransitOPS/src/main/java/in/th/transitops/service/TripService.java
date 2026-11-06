package in.th.transitops.service;

import in.th.transitops.dto.TripRequest;
import in.th.transitops.dto.TripResponse;
import in.th.transitops.entity.Driver;
import in.th.transitops.entity.Trip;
import in.th.transitops.entity.Vehicle;
import in.th.transitops.enums.TripLifecycle;
import in.th.transitops.exception.ResourceNotFoundException;
import in.th.transitops.repos.TripRepository;
import in.th.transitops.utils.CsvParser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class TripService {

    private static final String RESOURCE = "Trip";

    private final TripRepository tripRepository;

    private final VehicleService vehicleService;
    private final DriverService driverService;

    public List<TripResponse> getAll() {
        return tripRepository.findAll().stream()
                .map(TripResponse::fromEntity)
                .toList();
    }

    public List<Trip> findByActiveAndPending(){
        return tripRepository.findByActiveAndPending();
    }

    public TripResponse create(@Valid TripRequest request) {
        Trip trip = new Trip();
        applyRequest(trip, request);
        return TripResponse.fromEntity(tripRepository.save(trip));
    }

    public List<Trip> createFromCsv(MultipartFile file){
        return (tripRepository.saveAll(new CsvParser().extractCsv(file, Trip.class)));
    }

    public TripResponse update(long id, @Valid TripRequest request) {
        Trip trip = findEntity(id);
        applyRequest(trip, request);
        return TripResponse.fromEntity(tripRepository.save(trip));
    }

    public TripResponse updateStatus(long id, TripLifecycle lifecycle) {
        Trip trip = findEntity(id);
        trip.setLifecycle(lifecycle);
        return TripResponse.fromEntity(tripRepository.save(trip));
    }

    public void delete(long id) {
        if (tripRepository.existsById(id)) tripRepository.deleteById(id);
        else throw new RuntimeException("No trip found by id");
    }

    private void applyRequest(Trip trip, TripRequest request) {
        trip.setSource(request.source());
        trip.setDestination(request.destination());
        trip.setVehicle(findVehicle(request.vehicleId()));
        trip.setDriver(findDriver(request.driverId()));
        trip.setWeight(request.weight());
        trip.setPlannedDistance(request.plannedDistance());
        if (request.lifecycle() != null) {
            trip.setLifecycle(request.lifecycle());
        }
    }

    private Trip findEntity(long id) {
        return tripRepository.findById(id)
                .orElse(null);
    }

    private Vehicle findVehicle(long id) {
        return vehicleService.get(id);
    }

    private Driver findDriver(long id) {
        return driverService.get(id);
    }
}
