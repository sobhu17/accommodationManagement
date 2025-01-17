package acc.management.accommodationmanagement.repository;

import acc.management.accommodationmanagement.mappers.AccommodationRowMapper;
import acc.management.accommodationmanagement.models.Accommodation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class AccommodationDaoImpl implements AccommodationDao {

    private final JdbcTemplate jdbcTemplate;

    public AccommodationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Accommodation> findAvailableAccommodations() {
        String sql = "SELECT accommodation_id, description, size, availability, apartment_number FROM accommodations WHERE availability = TRUE";
        return jdbcTemplate.query(sql, new AccommodationRowMapper());
    }

    @Override
    public boolean bookAccommodation(int accommodationId, int userId) {
        // Calculate dates
        LocalDate bookingDate = LocalDate.now();
        LocalDate startDate = bookingDate.plusWeeks(1);
        LocalDate endDate = startDate.plusYears(1);

        // Insert into bookings table
        String insertBookingSql = "INSERT INTO bookings (booking_date, start_date, end_date) VALUES (?, ?, ?) RETURNING booking_id";
        Integer bookingId = jdbcTemplate.queryForObject(insertBookingSql, new Object[]{Date.valueOf(bookingDate), Date.valueOf(startDate), Date.valueOf(endDate)}, Integer.class);

        if (bookingId != null) {
            // Insert into user_bookings table
            String insertUserBookingSql = "INSERT INTO user_bookings (user_id, booking_id) VALUES (?, ?)";
            jdbcTemplate.update(insertUserBookingSql, userId, bookingId);

            // Insert into accommodation_bookings table
            String insertAccommodationBookingSql = "INSERT INTO accommodation_bookings (accommodation_id, booking_id) VALUES (?, ?)";
            jdbcTemplate.update(insertAccommodationBookingSql, accommodationId, bookingId);

            // Update accommodation availability
            String updateAccommodationSql = "UPDATE accommodations SET availability = FALSE WHERE accommodation_id = ?";
            jdbcTemplate.update(updateAccommodationSql, accommodationId);

            return true;
        }

        return false;
    }
}