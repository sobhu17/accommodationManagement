package acc.management.accommodationmanagement.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}

