package in.th.transitops.service;

import in.th.transitops.dto.VehicleRequest;
import in.th.transitops.dto.VehicleResponse;
import in.th.transitops.entity.Vehicle;
import in.th.transitops.enums.VehicleStatus;
import in.th.transitops.exception.ResourceNotFoundException;
import in.th.transitops.repos.VehicleRepository;
import in.th.transitops.utils.CsvParser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class VehicleService {

    private static final String RESOURCE = "Vehicle";

    @Autowired
    private final VehicleRepository vehicleRepository;

    public Vehicle get(long id){
        return vehicleRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(RESOURCE, id) );
    }

    public List<VehicleResponse> getByStatus(String status){
        VehicleStatus stat = switch (status) {
            case "Available" -> VehicleStatus.AVAILABLE;
            case "On Trip" -> VehicleStatus.ON_TRIP;
            case "In Shop" -> VehicleStatus.IN_SHOP;
            case "Retired" -> VehicleStatus.RETIRED;
            default -> null;
        };
        if (stat == null) throw new IllegalArgumentException(status+" Status not found");
        return vehicleRepository.findByStatus(stat).stream()
                .map(VehicleResponse::fromEntity)
                .toList();
    }

    public long countByStatus(VehicleStatus status){
        return vehicleRepository.countByStatus(status);
    }

    public List<VehicleResponse> getAll() {
        return vehicleRepository.findAll().stream()
                .map(VehicleResponse::fromEntity)
                .toList();
    }

    public VehicleResponse create(@Valid VehicleRequest request) {
        return VehicleResponse.fromEntity(vehicleRepository.save(request.toEntity()));
    }

    public List<Vehicle> createFromCsv(MultipartFile file){
        return (vehicleRepository.saveAll(new CsvParser().extractCsv(file, Vehicle.class)));
    }

    public VehicleResponse update(long id, @Valid VehicleRequest request) {
        Vehicle vehicle = findEntity(id);
        request.applyTo(vehicle);
        return VehicleResponse.fromEntity(vehicleRepository.save(vehicle));
    }

    public void delete(long id) {
        findEntity(id);
        vehicleRepository.deleteById(id);
    }

    private Vehicle findEntity(long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }
}
