package acc.management.accommodationmanagement.service;

import acc.management.accommodationmanagement.models.Accommodation;
import acc.management.accommodationmanagement.repository.AccommodationDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationService {

    private final AccommodationDao accommodationDao;

    public AccommodationService(AccommodationDao accommodationDao) {
        this.accommodationDao = accommodationDao;
    }

    public List<Accommodation> getAvailableAccommodations() {
        return accommodationDao.findAvailableAccommodations();
    }

    public boolean bookAccommodation(int accommodationId, int userId) {
        return accommodationDao.bookAccommodation(accommodationId, userId);
    }
}