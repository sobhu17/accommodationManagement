package acc.management.accommodationmanagement.service;

import acc.management.accommodationmanagement.Dtos.UserReminder;
import acc.management.accommodationmanagement.repository.AdminDao;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminService {

    private final AdminDao adminDao;
    private final BCryptPasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;

    public AdminService(AdminDao adminDao , JavaMailSender mailSender) {
        this.adminDao = adminDao;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.mailSender = mailSender;
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

    public void sendRentReminders() {
        List<UserReminder> reminders = adminDao.getUsersWithRentDue();

        for (UserReminder reminder : reminders) {
            sendEmail(reminder.getEmail(), reminder.getName(), reminder.getDueDate());
        }
    }

    private void sendEmail(String to, String name, LocalDate dueDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Rent Payment Reminder");
        message.setText("Dear " + name + ",\n\n" +
                "This is a friendly reminder that your rent is due on " + dueDate + ".\n" +
                "Please make your payment promptly to avoid late fees.\n\n" +
                "Thank you,\nManagement Team");
        mailSender.send(message);
    }
}
