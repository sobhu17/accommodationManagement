package acc.management.accommodationmanagement.models;

import java.time.LocalDate;

public class ComplaintDetails {
    private int complaintId;
    private String description;
    private String status;
    private LocalDate createdAt;
    private String apartmentNumber;
    private String accommodationSize;
    private String accommodationDescription;

    // Getters and Setters
    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getAccommodationSize() {
        return accommodationSize;
    }

    public void setAccommodationSize(String accommodationSize) {
        this.accommodationSize = accommodationSize;
    }

    public String getAccommodationDescription() {
        return accommodationDescription;
    }

    public void setAccommodationDescription(String accommodationDescription) {
        this.accommodationDescription = accommodationDescription;
    }
}
