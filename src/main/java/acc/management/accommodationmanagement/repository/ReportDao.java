package acc.management.accommodationmanagement.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ReportDao {

    private final JdbcTemplate jdbcTemplate;

    public ReportDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> fetchReportSummary() {
        Map<String, Object> reportData = new HashMap<>();

        // Query for number of families (distinct users who booked accommodations)
        String familiesQuery = "SELECT COUNT(DISTINCT user_id) FROM user_bookings";
        int families = jdbcTemplate.queryForObject(familiesQuery, Integer.class);

        // Total Accommodations
        String totalAccommodationsQuery = "SELECT COUNT(*) FROM accommodations";
        int totalAccommodations = jdbcTemplate.queryForObject(totalAccommodationsQuery, Integer.class);

        // Vacant Accommodations
        String vacantQuery = "SELECT COUNT(*) FROM accommodations WHERE availability = TRUE";
        int vacant = jdbcTemplate.queryForObject(vacantQuery, Integer.class);

        // Occupied Accommodations
        int occupied = totalAccommodations - vacant;

        // Total Complaints
        String totalComplaintsQuery = "SELECT COUNT(*) FROM complaints";
        int totalComplaints = jdbcTemplate.queryForObject(totalComplaintsQuery, Integer.class);

        // Pending Complaints
        String pendingComplaintsQuery = "SELECT COUNT(*) FROM complaints WHERE status = 'pending'";
        int pendingComplaints = jdbcTemplate.queryForObject(pendingComplaintsQuery, Integer.class);

        // Resolved Complaints
        int resolvedComplaints = totalComplaints - pendingComplaints;

        // Add data to the map
        reportData.put("families", families);
        reportData.put("totalAccommodations", totalAccommodations);
        reportData.put("vacant", vacant);
        reportData.put("occupied", occupied);
        reportData.put("totalComplaints", totalComplaints);
        reportData.put("pendingComplaints", pendingComplaints);
        reportData.put("resolvedComplaints", resolvedComplaints);

        return reportData;
    }
}