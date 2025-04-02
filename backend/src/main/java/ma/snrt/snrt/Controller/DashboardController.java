package ma.snrt.snrt.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.snrt.snrt.Services.DashboardService;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard",
        description = "This API provides the capability to get Dashboard data ")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;


    @GetMapping("/getDashboardData")
    public ResponseEntity<Object> getDashboardData() {
        return ResponseEntity.ok(dashboardService.getDashboardData());
    }

    @GetMapping("/getDashboardByType")
    public ResponseEntity<Object> getDashboardByType() {
        return ResponseEntity.ok(dashboardService.getDashboardByType());
    }

    @GetMapping("/getDashboardByUnite")
    public ResponseEntity<Object> getDashboardByUnite() {
        return ResponseEntity.ok(dashboardService.getDashboardByUnite());
    }

    @GetMapping("/getDashboardByUniteAndType")
    public ResponseEntity<Object> getDashboardByUniteType() {
        return ResponseEntity.ok(dashboardService.getDashboardByUniteAndType());
    }

}
