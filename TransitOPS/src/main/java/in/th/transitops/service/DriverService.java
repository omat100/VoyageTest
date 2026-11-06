package in.th.transitops.service;

import in.th.transitops.dto.DriverRequest;
import in.th.transitops.dto.DriverResponse;
import in.th.transitops.dto.VehicleResponse;
import in.th.transitops.entity.Driver;
import in.th.transitops.enums.DriverStatus;
import in.th.transitops.enums.VehicleStatus;
import in.th.transitops.exception.ResourceNotFoundException;
import in.th.transitops.repos.DriverRepository;
import in.th.transitops.utils.CsvParser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class DriverService {

    private static final String RESOURCE = "Driver";

    private final DriverRepository driverRepository;

    public Driver get(long id) {
        return driverRepository.findById(id)
            .orElseThrow( () -> new ResourceNotFoundException(RESOURCE, id));
    }

    public long countByStatus(DriverStatus status){
        return driverRepository.countByStatus(status);
    }

    public List<DriverResponse> getByStatus(String status){
        DriverStatus stat = switch (status) {
            case "Available" -> DriverStatus.AVAILABLE;
            case "On Trip" -> DriverStatus.ON_TRIP;
            case "Off Duty" -> DriverStatus.OFF_DUTY;
            case "Suspended" -> DriverStatus.SUSPENDED;
            default -> null;
        };
        if (stat == null) throw new IllegalArgumentException(status+" Status not found");
        return driverRepository.findByStatus(stat).stream()
                .map(DriverResponse::fromEntity)
                .toList();
    }

    public List<DriverResponse> getAll() {
        return driverRepository.findAll().stream()
                .map(DriverResponse::fromEntity)
                .toList();
    }

    public List<DriverResponse> availableDrivers(){
        return driverRepository.findFirstByStatus(DriverStatus.AVAILABLE).orElse(new ArrayList<>());
    }

    public DriverResponse create(@Valid DriverRequest request) {
        return DriverResponse.fromEntity(driverRepository.save(request.toEntity()));
    }

    public List<Driver> createFromCsv(MultipartFile file){
        return (driverRepository.saveAll(new CsvParser().extractCsv(file, Driver.class)));
    }

    public DriverResponse update(long id, @Valid DriverRequest request) {
        Driver driver = findEntity(id);
        request.applyTo(driver);
        return DriverResponse.fromEntity(driverRepository.save(driver));
    }

    public void delete(long id) {
        findEntity(id);
        driverRepository.deleteById(id);
    }

    private Driver findEntity(long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }
}
