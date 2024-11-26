package acc.management.accommodationmanagement.repository;

import acc.management.accommodationmanagement.models.ComplaintDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComplaintDaoImpl implements ComplaintDao {

    private final JdbcTemplate jdbcTemplate;

    public ComplaintDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ComplaintDetails> fetchAllComplaints() {
        String sql = """
                SELECT c.complaint_id, c.description, c.status, c.created_at,
                       a.apartment_number, a.size, a.description AS accommodation_description
                FROM complaints c
                JOIN accommodation_complaints ac ON c.complaint_id = ac.complaint_id
                JOIN accommodations a ON ac.accommodation_id = a.accommodation_id
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ComplaintDetails details = new ComplaintDetails();
            details.setComplaintId(rs.getInt("complaint_id"));
            details.setDescription(rs.getString("description"));
            details.setStatus(rs.getString("status"));
            details.setCreatedAt(rs.getDate("created_at").toLocalDate());
            details.setApartmentNumber(rs.getString("apartment_number"));
            details.setAccommodationSize(rs.getString("size"));
            details.setAccommodationDescription(rs.getString("accommodation_description"));
            return details;
        });
    }

    @Override
    public boolean updateComplaintStatus(int complaintId, String status) {
        String sql = "UPDATE complaints SET status = ? WHERE complaint_id = ?";
        int rowsUpdated = jdbcTemplate.update(sql, status, complaintId);
        return rowsUpdated > 0;
    }
}

