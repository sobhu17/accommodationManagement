package acc.management.accommodationmanagement.repository;

import acc.management.accommodationmanagement.models.Accommodation;

import java.util.List;
import java.util.Map;

public interface AccommodationDao {
    List<Accommodation> findAvailableAccommodations();

    boolean bookAccommodation(int accommodationId, int userId);

}
