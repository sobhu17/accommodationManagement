package acc.management.accommodationmanagement.repository;

import acc.management.accommodationmanagement.mappers.AccommodationRowMapper;
import acc.management.accommodationmanagement.models.Accommodation;
import acc.management.accommodationmanagement.models.Booking;
import acc.management.accommodationmanagement.models.Complaint;
import acc.management.accommodationmanagement.models.UserAccommodationDetails;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        LocalDate dueDate = startDate.plusMonths(1);

        // Insert into bookings table
        String insertBookingSql = "INSERT INTO bookings (booking_date, start_date, end_date, due_date) VALUES (?, ?, ?, ?) RETURNING booking_id";
        Integer bookingId = jdbcTemplate.queryForObject(insertBookingSql, new Object[]{Date.valueOf(bookingDate), Date.valueOf(startDate), Date.valueOf(endDate), Date.valueOf(dueDate)}, Integer.class);

        if (bookingId != null) {
            // Insert into user_bookings table
            String insertUserBookingSql = "INSERT INTO user_bookings (user_id, booking_id) VALUES (?, ?)";
            jdbcTemplate.update(insertUserBookingSql, userId, bookingId);

            // Insert into accommodation_bookings table
            String insertAccommodationBookingSql = "INSERT INTO accommodation_bookings (accommodation_id, booking_id) VALUES (?, ?)";
            jdbcTemplate.update(insertAccommodationBookingSql, accommodationId, bookingId);

            // Create a lease payment record
            Integer paymentId = createLeasePayment(1000.00); // Assuming a fixed lease amount, adjust as necessary

            if (paymentId != null) {
                // Insert into booking_payments table
                String insertBookingPaymentSql = "INSERT INTO booking_payments (booking_id, payment_id) VALUES (?, ?)";
                jdbcTemplate.update(insertBookingPaymentSql, bookingId, paymentId);

                // Update accommodation availability
                String updateAccommodationSql = "UPDATE accommodations SET availability = FALSE WHERE accommodation_id = ?";
                jdbcTemplate.update(updateAccommodationSql, accommodationId);

                return true;
            }
        }
        return false;
    }

    private Integer createLeasePayment(double amount) {
        // Insert into payments table
        String insertPaymentSql = "INSERT INTO payments (amount, payment_date, payment_status, payment_type) VALUES (?, ?, ?, ?) RETURNING payment_id";
        return jdbcTemplate.queryForObject(insertPaymentSql, new Object[]{
                amount,
                Date.valueOf(LocalDate.now()),
                true, // Assuming payment is successful
                "lease"
        }, Integer.class);
    }

    @Override
    public UserAccommodationDetails getUserAccommodationDetails(int userId) {

        String sql = """
        SELECT u.name AS user_name, u.email, a.size, a.apartment_number,
               b.start_date, b.end_date, b.due_date,
               CASE
                   WHEN CURRENT_DATE > b.due_date THEN
                       1900 + 100 * FLOOR((CURRENT_DATE - b.due_date)::INTEGER / 7)
                   ELSE 1900
               END AS current_rent
        FROM users u
        JOIN user_bookings ub ON u.user_id = ub.user_id
        JOIN bookings b ON ub.booking_id = b.booking_id
        JOIN accommodation_bookings ab ON b.booking_id = ab.booking_id
        JOIN accommodations a ON ab.accommodation_id = a.accommodation_id
        WHERE u.user_id = ?
    """;

        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, this::mapToUserAccommodationDetails);
    }


    private UserAccommodationDetails mapToUserAccommodationDetails(ResultSet rs, int rowNum) throws SQLException {
        UserAccommodationDetails details = new UserAccommodationDetails();
        details.setUserName(rs.getString("user_name"));
        details.setEmail(rs.getString("email"));
        details.setAccommodationSize(rs.getString("size"));
        details.setApartmentNumber(rs.getString("apartment_number"));
        details.setLeaseStartDate(rs.getDate("start_date").toLocalDate());
        details.setLeaseEndDate(rs.getDate("end_date").toLocalDate());
        details.setDueDate(rs.getDate("due_date").toLocalDate());
        details.setCurrentRent(rs.getDouble("current_rent"));
        return details;
    }


    @Override
    public boolean processRentPayment(int userId, double rentAmount) {
        try {
            // Fetch the booking ID associated with the user
            String getBookingIdSql = """
            SELECT b.booking_id 
            FROM bookings b
            JOIN user_bookings ub ON b.booking_id = ub.booking_id
            WHERE ub.user_id = ?
            """;
            Integer bookingId = jdbcTemplate.queryForObject(getBookingIdSql, Integer.class, userId);

            if (bookingId == null) {
                throw new IllegalArgumentException("No booking found for user ID: " + userId);
            }

            // Insert payment into payments table
            String insertPaymentSql = """
            INSERT INTO payments (amount, payment_date, payment_status, payment_type) 
            VALUES (?, ?, ?, ?) RETURNING payment_id
            """;
            Integer paymentId = jdbcTemplate.queryForObject(insertPaymentSql, Integer.class, rentAmount, Date.valueOf(LocalDate.now()), true, "rent");

            if (paymentId == null) {
                throw new IllegalStateException("Failed to create a payment record.");
            }

            // Associate the payment with the booking
            String insertBookingPaymentSql = """
            INSERT INTO booking_payments (booking_id, payment_id) 
            VALUES (?, ?)
            """;
            jdbcTemplate.update(insertBookingPaymentSql, bookingId, paymentId);

            // Update the next rent due date
            String updateDueDateSql = """
            UPDATE bookings 
            SET due_date = due_date + INTERVAL '1 MONTH' 
            WHERE booking_id = ?
            """;
            jdbcTemplate.update(updateDueDateSql, bookingId);

            // Insert into user_payments table
            String insertUserPaymentSql = """
            INSERT INTO user_payments (user_id, payment_id)
            VALUES (?, ?)
            """;
            jdbcTemplate.update(insertUserPaymentSql, userId, paymentId);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean fileComplaint(int userId, String description) {
        try {
            // Fetch the associated accommodation ID
            String getAccommodationIdSql = """
            SELECT a.accommodation_id
            FROM accommodations a
            JOIN accommodation_bookings ab ON a.accommodation_id = ab.accommodation_id
            JOIN user_bookings ub ON ab.booking_id = ub.booking_id
            WHERE ub.user_id = ?
            """;
            Integer accommodationId = jdbcTemplate.queryForObject(getAccommodationIdSql, Integer.class, userId);

            if (accommodationId == null) {
                throw new IllegalArgumentException("No accommodation found for user ID: " + userId);
            }

            // Insert the complaint
            String insertComplaintSql = """
            INSERT INTO complaints (description, status, created_at) 
            VALUES (?, ?, ?) RETURNING complaint_id
            """;
            Integer complaintId = jdbcTemplate.queryForObject(insertComplaintSql, Integer.class, description, "pending", Date.valueOf(LocalDate.now()));

            if (complaintId == null) {
                throw new IllegalStateException("Failed to create complaint.");
            }

            // Link complaint with the accommodation
            String insertAccommodationComplaintSql = """
            INSERT INTO accommodation_complaints (accommodation_id, complaint_id) 
            VALUES (?, ?)
            """;
            jdbcTemplate.update(insertAccommodationComplaintSql, accommodationId, complaintId);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Complaint> findComplaintsByUserId(int userId) {
        String sql = """
        SELECT c.complaint_id AS id, c.description, c.status
        FROM complaints c
        JOIN accommodation_complaints ac ON c.complaint_id = ac.complaint_id
        JOIN accommodation_bookings ab ON ac.accommodation_id = ab.accommodation_id
        JOIN bookings b ON ab.booking_id = b.booking_id
        JOIN user_bookings ub ON b.booking_id = ub.booking_id
        WHERE ub.user_id = ? AND c.status = 'pending'
    """;

        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            Complaint complaint = new Complaint();
            complaint.setId(rs.getInt("id"));
            complaint.setDescription(rs.getString("description"));
            complaint.setStatus(rs.getString("status"));
            return complaint;
        });
    }

    @Override
    public boolean updateComplaintDescription(int complaintId, String description) {
        String sql = "UPDATE complaints SET description = ? WHERE complaint_id = ?";
        int rowsUpdated = jdbcTemplate.update(sql, description, complaintId);
        return rowsUpdated > 0;
    }

    @Override
    public Booking findActiveBookingByUserId(int userId) {
        String sql = """
        SELECT b.booking_id, b.booking_date, b.start_date, b.end_date, b.due_date
        FROM bookings b
        JOIN user_bookings ub ON b.booking_id = ub.booking_id
        WHERE ub.user_id = ? AND CURRENT_DATE BETWEEN b.start_date AND b.end_date
        """;
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userId}, (rs, rowNum) -> {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setBookingDate(rs.getDate("booking_date").toLocalDate());
                booking.setStartDate(rs.getDate("start_date").toLocalDate());
                booking.setEndDate(rs.getDate("end_date").toLocalDate());
                booking.setDueDate(rs.getDate("due_date").toLocalDate());
                return booking;
            });
        } catch (EmptyResultDataAccessException e) {
            return null; // No active booking found
        }
    }
}
