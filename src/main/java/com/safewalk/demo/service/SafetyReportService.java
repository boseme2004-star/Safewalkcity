package com.safewalk.demo.service;

import com.safewalk.demo.model.SafetyReport;
import com.safewalk.demo.model.User;
import com.safewalk.demo.repository.SafetyReportRepository;
import com.safewalk.demo.repository.UserRepository;
import java.util.Map;
import java.util.LinkedHashMap;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class SafetyReportService {

    private final SafetyReportRepository reportRepository;
    private final UserRepository userRepository;

    public SafetyReportService(
            SafetyReportRepository reportRepository,
            UserRepository userRepository) {

        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    // Create a safety report
    public SafetyReport createReport(
            Long userId,
            String location,
            String description,
            SafetyReport.ReportCategory category,
            SafetyReport.Severity severity,
            boolean anonymous) {

        SafetyReport report = new SafetyReport();

        // Attach the user only when the report is not anonymous
        if (userId != null && !anonymous) {

            User user = userRepository.findById(userId)
                    .orElseThrow(() ->
                            new RuntimeException("User not found"));

            report.setUser(user);
        }

        report.setLocation(location);
        report.setDescription(description);
        report.setCategory(category);
        report.setSeverity(severity);
        report.setAnonymous(anonymous);
        report.setReportedAt(LocalDateTime.now());

        return reportRepository.save(report);
    }

    // Get all safety reports
    public List<SafetyReport> getAllReports() {

        return reportRepository.findAll();
    }

    // Get one report by ID
    public SafetyReport getReport(Long id) {

        return reportRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Safety report not found"));
    }

    // Get reports by severity
    public List<SafetyReport> getReportsBySeverity(
            SafetyReport.Severity severity) {

        return reportRepository.findBySeverity(severity);
    }

    // Get reports by category
    public List<SafetyReport> getReportsByCategory(
            SafetyReport.ReportCategory category) {

        return reportRepository.findByCategory(category);
    }

    // Get reports by location
    public List<SafetyReport> getReportsByLocation(
            String location) {

        return reportRepository.findByLocationIgnoreCase(location);
    }

    // Get HIGH and CRITICAL community alerts
    public List<SafetyReport> getCommunityAlerts() {

        return reportRepository.findBySeverityIn(
                List.of(
                        SafetyReport.Severity.HIGH,
                        SafetyReport.Severity.CRITICAL
                )
        );
    }

    // Get high-risk locations
    public List<Object[]> getHighRiskLocations() {

        return reportRepository.findHighRiskLocations(
                List.of(
                        SafetyReport.Severity.HIGH,
                        SafetyReport.Severity.CRITICAL
                )
        );
    }

    public List<Object[]> getSafetyHeatMapData() {

    return reportRepository.getSafetyHeatMapData();
}
   public Map<String, Object> getAnalytics() {

    Map<String, Object> analytics = new LinkedHashMap<>();

    analytics.put(
            "totalReports",
            reportRepository.count()
    );

    analytics.put(
            "lowReports",
            reportRepository.countBySeverity(
                    SafetyReport.Severity.LOW
            )
    );

    analytics.put(
            "mediumReports",
            reportRepository.countBySeverity(
                    SafetyReport.Severity.MEDIUM
            )
    );

    analytics.put(
            "highReports",
            reportRepository.countBySeverity(
                    SafetyReport.Severity.HIGH
            )
    );

    analytics.put(
            "criticalReports",
            reportRepository.countBySeverity(
                    SafetyReport.Severity.CRITICAL
            )
    );

    return analytics;
}

}