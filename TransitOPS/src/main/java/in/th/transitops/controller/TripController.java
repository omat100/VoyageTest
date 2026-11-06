package in.th.transitops.controller;

import in.th.transitops.dto.TripResponse;
import in.th.transitops.entity.Trip;
import in.th.transitops.enums.TripLifecycle;
import in.th.transitops.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/trips")
class TripController {

    @Autowired
    private TripService tripService;

    @GetMapping("/live")
    public ResponseEntity<List<TripResponse>> fetchAll(){
        List<TripResponse> response = tripService.getAll();
        return (response !=null && !response.isEmpty()) ?
                ResponseEntity.ok(response) :
                ResponseEntity.notFound().build();
    }

    @PostMapping("/bulk-trip")
    public ResponseEntity<Boolean> addDriverCsv(@RequestPart MultipartFile file){
        List<Trip> saved = tripService.createFromCsv(file);
        return (saved != null) ? ResponseEntity.ok().body(true) : ResponseEntity.badRequest().body(false);
    }

    @PutMapping("/{tripId}/complete")
    public ResponseEntity<Boolean> completeTrip(@PathVariable long tripId) {
        TripResponse response = tripService.updateStatus(tripId, TripLifecycle.COMPLETED);
        return (response!= null) ?
                ResponseEntity.ok(true) :
                ResponseEntity.internalServerError().build();
    }

    @PutMapping("/{tripId}/cancel")
    public ResponseEntity<Boolean> cancelTrip(@PathVariable long tripId) {
        TripResponse response = tripService.updateStatus(tripId, TripLifecycle.CANCELLED);
        return (response!= null) ?
                ResponseEntity.ok(true) :
                ResponseEntity.internalServerError().build();
    }
}
