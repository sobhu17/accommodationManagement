package acc.management.accommodationmanagement.service;

import acc.management.accommodationmanagement.repository.ReportDao;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReportService {

    private final ReportDao reportDao;

    public ReportService(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public Map<String, Object> getReportSummary() {
        return reportDao.fetchReportSummary();
    }
}
