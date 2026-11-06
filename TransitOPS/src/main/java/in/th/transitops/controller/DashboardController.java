package in.th.transitops.controller;

import in.th.transitops.dto.DashboardResponse;
import in.th.transitops.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/dashboard")
class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @PostMapping("/")
    public ResponseEntity<DashboardResponse> dashboard(){
        DashboardResponse res = dashboardService.dashboard();
        if (res != null) return ResponseEntity.ok().body(res);
        else return ResponseEntity.notFound().build();
    }
}
