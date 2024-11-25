package acc.management.accommodationmanagement.repository;

import acc.management.accommodationmanagement.models.Accommodation;
import acc.management.accommodationmanagement.models.Booking;
import acc.management.accommodationmanagement.models.Complaint;
import acc.management.accommodationmanagement.models.UserAccommodationDetails;

import java.util.List;
import java.util.Map;

public interface AccommodationDao {
    List<Accommodation> findAvailableAccommodations();

    boolean bookAccommodation(int accommodationId, int userId);

    UserAccommodationDetails getUserAccommodationDetails(int userId);

    boolean processRentPayment(int userId, double rentAmount);

    boolean fileComplaint(int userId, String description);

    List<Complaint> findComplaintsByUserId(int userId);

    boolean updateComplaintDescription(int complaintId, String description);

    Booking findActiveBookingByUserId(int userId);

}
