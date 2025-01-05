package io.github.luminaire1337.swimfacilityapi.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    @GetMapping("/generate-report")
    @Operation(summary = "Generates a report of all Logs")
    @ApiResponse(description = "Returns a report of all Logs", responseCode = "200")
    public ResponseEntity<String> generateReport() {
        String csvContent = this.adminService.generateReportCSV();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=report.csv");
        headers.add("Content-Type", "text/csv");

        // Log the event
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.logger.info("User {} generated a report", userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(csvContent);
    }
}
