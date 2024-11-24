package acc.management.accommodationmanagement.service;

import acc.management.accommodationmanagement.models.Accommodation;
import acc.management.accommodationmanagement.models.UserAccommodationDetails;
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

    public UserAccommodationDetails getUserAccommodationDetails(int userId) {
        return accommodationDao.getUserAccommodationDetails(userId);
    }

    public boolean payRent(int userId, double rentAmount) {
        return accommodationDao.processRentPayment(userId, rentAmount);
    }

    public boolean fileComplaint(int userId, String description) {
        return accommodationDao.fileComplaint(userId, description);
    }

}