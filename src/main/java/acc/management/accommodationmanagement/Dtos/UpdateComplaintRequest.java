package acc.management.accommodationmanagement.Dtos;

public class UpdateComplaintRequest {
    private int complaintId;
    private String description;

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
}