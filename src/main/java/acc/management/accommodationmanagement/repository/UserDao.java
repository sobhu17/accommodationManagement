package acc.management.accommodationmanagement.repository;

import acc.management.accommodationmanagement.models.User;

public interface UserDao {
    User findUserById(int id);
    int registerUser(User user);
    // Additional methods as needed

    User findByEmail(String email);
}
