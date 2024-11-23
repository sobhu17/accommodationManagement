package acc.management.accommodationmanagement.repository;

import acc.management.accommodationmanagement.models.Accommodation;
import acc.management.accommodationmanagement.models.UserAccommodationDetails;

import java.util.List;
import java.util.Map;

public interface AccommodationDao {
    List<Accommodation> findAvailableAccommodations();

    boolean bookAccommodation(int accommodationId, int userId);

    UserAccommodationDetails getUserAccommodationDetails(int userId);

    boolean processRentPayment(int userId, double rentAmount);
}
