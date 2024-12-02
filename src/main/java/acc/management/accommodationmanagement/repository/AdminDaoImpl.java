package acc.management.accommodationmanagement.repository;

import acc.management.accommodationmanagement.Dtos.UserReminder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDaoImpl implements AdminDao {

    private final JdbcTemplate jdbcTemplate;

    public AdminDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getPasswordByUsername(String username) {
        String sql = "SELECT password FROM admin WHERE admin_user_name = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, username);
        } catch (Exception e) {
            return null; // Username not found
        }
    }


    @Override
    public List<UserReminder> getUsersWithRentDue() {
        String sql = """
        SELECT u.name, u.email, b.due_date
        FROM users u
        JOIN user_bookings ub ON u.user_id = ub.user_id
        JOIN bookings b ON ub.booking_id = b.booking_id
        WHERE b.due_date BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '7 days'
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            UserReminder reminder = new UserReminder();
            reminder.setName(rs.getString("name"));
            reminder.setEmail(rs.getString("email"));
            reminder.setDueDate(rs.getDate("due_date").toLocalDate());
            return reminder;
        });
    }

}

