package in.th.transitops.controller;

import in.th.transitops.dto.VehicleResponse;
import in.th.transitops.entity.Vehicle;
import in.th.transitops.enums.VehicleStatus;
import in.th.transitops.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/add-csv")
    public ResponseEntity<Boolean> addDriverCsv(@RequestPart MultipartFile file){
        List<Vehicle> saved = vehicleService.createFromCsv(file);
        return (saved != null) ? ResponseEntity.ok().body(true) : ResponseEntity.badRequest().body(false);
    }

    @GetMapping("/")
    public ResponseEntity<List<VehicleResponse>> availableVehicles(@RequestParam String status) {
        List<VehicleResponse> response = vehicleService.getByStatus(status);
        return (response != null) ?
                ResponseEntity.ok().body(response) :
                ResponseEntity.badRequest().build();
    }
}
