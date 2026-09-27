package com.safewalk.demo.controller;

import com.safewalk.demo.model.SafetyReport;
import com.safewalk.demo.service.SafetyReportService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/safety-reports")
public class SafetyReportController {

    private final SafetyReportService safetyReportService;

    public SafetyReportController(
            SafetyReportService safetyReportService) {

        this.safetyReportService = safetyReportService;
    }

    // Create a safety report
    @PostMapping
    public ResponseEntity<SafetyReport> createReport(

            @RequestParam(required = false)
            Long userId,

            @RequestParam
            String location,

            @RequestParam
            String description,

            @RequestParam
            SafetyReport.ReportCategory category,

            @RequestParam
            SafetyReport.Severity severity,

            @RequestParam
            boolean anonymous) {

        SafetyReport report =
                safetyReportService.createReport(
                        userId,
                        location,
                        description,
                        category,
                        severity,
                        anonymous
                );

        return ResponseEntity.ok(report);
    }

    // Get all safety reports
    @GetMapping
    public ResponseEntity<List<SafetyReport>> getAllReports() {

        return ResponseEntity.ok(
                safetyReportService.getAllReports()
        );
    }

    // Get a specific report
    @GetMapping("/{id}")
    public ResponseEntity<SafetyReport> getReport(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                safetyReportService.getReport(id)
        );
    }

    // Get reports by severity
    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<SafetyReport>> getBySeverity(
            @PathVariable SafetyReport.Severity severity) {

        return ResponseEntity.ok(
                safetyReportService.getReportsBySeverity(severity)
        );
    }

    // Get reports by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<SafetyReport>> getByCategory(
            @PathVariable SafetyReport.ReportCategory category) {

        return ResponseEntity.ok(
                safetyReportService.getReportsByCategory(category)
        );
    }

    // Get reports by location
    @GetMapping("/location/{location}")
    public ResponseEntity<List<SafetyReport>> getByLocation(
            @PathVariable String location) {

        return ResponseEntity.ok(
                safetyReportService.getReportsByLocation(location)
        );
    }

    // Get HIGH and CRITICAL community alerts
    @GetMapping("/alerts")
    public ResponseEntity<List<SafetyReport>> getCommunityAlerts() {

        return ResponseEntity.ok(
                safetyReportService.getCommunityAlerts()
        );
    }

    // Get high-risk locations
    @GetMapping("/high-risk-areas")
    public ResponseEntity<List<Object[]>> getHighRiskLocations() {

        return ResponseEntity.ok(
                safetyReportService.getHighRiskLocations()
        );
    }

    @GetMapping("/heat-map")
public ResponseEntity<List<Object[]>> getSafetyHeatMapData() {

    return ResponseEntity.ok(
            safetyReportService.getSafetyHeatMapData()
    );
}
@GetMapping("/analytics")
public ResponseEntity<Map<String, Object>> getAnalytics() {

    return ResponseEntity.ok(
            safetyReportService.getAnalytics()
    );
}

}