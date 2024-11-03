package acc.management.accommodationmanagement.models;

public class Accommodation {
    private int accommodation_id;
    private String description;
    private String size;
    private boolean availability;
    private String apartment_number;

    // Getters and Setters
    public int getAccommodation_id() {
        return accommodation_id;
    }

    public void setAccommodation_id(int accommodation_id) {
        this.accommodation_id = accommodation_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getApartment_number() {
        return apartment_number;
    }

    public void setApartment_number(String apartment_number) {
        this.apartment_number = apartment_number;
    }
}
