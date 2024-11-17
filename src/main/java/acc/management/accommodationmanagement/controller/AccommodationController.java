package acc.management.accommodationmanagement.controller;

import acc.management.accommodationmanagement.Dtos.BookingRequest;
import acc.management.accommodationmanagement.models.Accommodation;
import acc.management.accommodationmanagement.models.UserAccommodationDetails;
import acc.management.accommodationmanagement.service.AccommodationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accommodations")
public class AccommodationController {

    private final AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping("/available")
    public List<Accommodation> getAvailableAccommodations() {
        return accommodationService.getAvailableAccommodations();
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookAccommodation(@RequestBody BookingRequest bookingRequest) {
        boolean isBooked = accommodationService.bookAccommodation(bookingRequest.getAccommodationId(), bookingRequest.getUserId());
        if (isBooked) {
            return ResponseEntity.ok("Accommodation booked successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to book accommodation.");
        }
    }

    @GetMapping("/my-accommodation/{userId}")
    public ResponseEntity<UserAccommodationDetails> getUserAccommodationDetails(@PathVariable int userId) {
        UserAccommodationDetails userDetails = accommodationService.getUserAccommodationDetails(userId);
        if (userDetails != null) {
            return ResponseEntity.ok(userDetails);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
