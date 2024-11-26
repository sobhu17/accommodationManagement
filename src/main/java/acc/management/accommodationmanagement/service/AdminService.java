package acc.management.accommodationmanagement.service;

import acc.management.accommodationmanagement.repository.AdminDao;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminDao adminDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminService(AdminDao adminDao) {
        this.adminDao = adminDao;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean authenticateAdmin(String username, String password) {
        // Fetch hashed password from the database
        String storedHashedPassword = adminDao.getPasswordByUsername(username);

        if (storedHashedPassword == null) {
            // Username not found
            return false;
        }

        // Validate the password
        return passwordEncoder.matches(password, storedHashedPassword);
    }
}
