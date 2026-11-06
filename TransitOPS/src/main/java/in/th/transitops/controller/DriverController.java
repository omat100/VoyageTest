package in.th.transitops.controller;

import in.th.transitops.dto.DriverResponse;
import in.th.transitops.dto.VehicleResponse;
import in.th.transitops.entity.Driver;
import in.th.transitops.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/")
class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/add-csv")
    public ResponseEntity<Boolean> addDriverCsv(@RequestPart MultipartFile file){
        List<Driver> saved = driverService.createFromCsv(file);
        return (saved != null) ? ResponseEntity.ok().body(true) : ResponseEntity.badRequest().body(false);
    }

    @GetMapping("/")
    public ResponseEntity<List<DriverResponse>> availableVehicles(@RequestParam String status) {
        List<DriverResponse> response = driverService.getByStatus(status);
        return (response != null) ?
                ResponseEntity.ok().body(response) :
                ResponseEntity.badRequest().build();
    }
}
