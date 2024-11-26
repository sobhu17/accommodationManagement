package acc.management.accommodationmanagement.controller;

import acc.management.accommodationmanagement.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getReportSummary() {
        Map<String, Object> reportData = reportService.getReportSummary();
        return ResponseEntity.ok(reportData);
    }
}
