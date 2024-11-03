package acc.management.accommodationmanagement.service;

import acc.management.accommodationmanagement.models.User;
import acc.management.accommodationmanagement.repository.UserDao;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDao userDao , PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserById(int id) {
        return userDao.findUserById(id);
    }

    public int registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userDao.registerUser(user);
    }

    public User authenticateUser(String email, String rawPassword) {
        User user = userDao.findByEmail(email);

        // Check if user exists and the password matches
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return user; // Return the full User object if authentication is successful
        }
        return null; // Return null if authentication fails
    }
}
