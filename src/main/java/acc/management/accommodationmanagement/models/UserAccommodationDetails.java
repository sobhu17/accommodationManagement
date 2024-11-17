package acc.management.accommodationmanagement.models;
import java.time.LocalDate;

public class UserAccommodationDetails {
    private String userName;
    private String email;
    private String accommodationSize;
    private String apartmentNumber;
    private LocalDate leaseStartDate;
    private LocalDate leaseEndDate;
    private LocalDate dueDate;
    private double currentRent;

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccommodationSize() {
        return accommodationSize;
    }

    public void setAccommodationSize(String accommodationSize) {
        this.accommodationSize = accommodationSize;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public LocalDate getLeaseStartDate() {
        return leaseStartDate;
    }

    public void setLeaseStartDate(LocalDate leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public LocalDate getLeaseEndDate() {
        return leaseEndDate;
    }

    public void setLeaseEndDate(LocalDate leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public double getCurrentRent() {
        return currentRent;
    }

    public void setCurrentRent(double currentRent) {
        this.currentRent = currentRent;
    }
}
