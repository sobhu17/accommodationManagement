package acc.management.accommodationmanagement.Dtos;

public class BookingRequest {
    private int accommodationId;
    private int userId;

    // Getters and Setters
    public int getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
