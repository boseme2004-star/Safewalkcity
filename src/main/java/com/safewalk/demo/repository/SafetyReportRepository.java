package com.safewalk.demo.repository;

import com.safewalk.demo.model.SafetyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SafetyReportRepository
        extends JpaRepository<SafetyReport, Long> {

    // Find reports by severity
    List<SafetyReport> findBySeverity(
            SafetyReport.Severity severity);

    // Find reports by category
    List<SafetyReport> findByCategory(
            SafetyReport.ReportCategory category);

    // Find reports by location
    List<SafetyReport> findByLocationIgnoreCase(
            String location);

    // Find HIGH and CRITICAL reports
    List<SafetyReport> findBySeverityIn(
            List<SafetyReport.Severity> severities);

    // Find locations with at least 2 HIGH or CRITICAL reports
    @Query("""
        SELECT r.location, COUNT(r)
        FROM SafetyReport r
        WHERE r.severity IN :severities
        GROUP BY r.location
        HAVING COUNT(r) >= 2
        """)
    List<Object[]> findHighRiskLocations(
            @Param("severities")
            List<SafetyReport.Severity> severities);
}