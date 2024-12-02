package acc.management.accommodationmanagement.repository;

import acc.management.accommodationmanagement.Dtos.UserReminder;

import java.util.List;

public interface AdminDao {
    String getPasswordByUsername(String username);

    List<UserReminder> getUsersWithRentDue();

}

