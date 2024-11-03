package acc.management.accommodationmanagement.repository;

import acc.management.accommodationmanagement.models.Accommodation;

import java.util.List;

public interface AccommodationDao {
    List<Accommodation> findAvailableAccommodations();

    boolean bookAccommodation(int accommodationId, int userId);
}
