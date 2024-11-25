package acc.management.accommodationmanagement.controller;

import acc.management.accommodationmanagement.Dtos.BookingRequest;
import acc.management.accommodationmanagement.Dtos.ComplaintRequest;
import acc.management.accommodationmanagement.Dtos.RentPaymentRequest;
import acc.management.accommodationmanagement.Dtos.UpdateComplaintRequest;
import acc.management.accommodationmanagement.models.Accommodation;
import acc.management.accommodationmanagement.models.Complaint;
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

    @PostMapping("/pay-rent")
    public ResponseEntity<String> payRent(@RequestBody RentPaymentRequest rentPaymentRequest) {
        boolean isPaid = accommodationService.payRent(rentPaymentRequest.getUserId(), rentPaymentRequest.getRentAmount());
        if (isPaid) {
            return ResponseEntity.ok("Rent payment successful.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rent payment failed.");
        }
    }

    @PostMapping("/file-complaint")
    public ResponseEntity<String> fileComplaint(@RequestBody ComplaintRequest complaintRequest) {
        boolean isFiled = accommodationService.fileComplaint(complaintRequest.getUserId(), complaintRequest.getDescription());
        if (isFiled) {
            return ResponseEntity.ok("Complaint filed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to file complaint.");
        }
    }

    @GetMapping("/user-complaints/{userId}")
    public ResponseEntity<List<Complaint>> getUserComplaints(@PathVariable int userId) {
        List<Complaint> complaints = accommodationService.getUserComplaints(userId);
        return ResponseEntity.ok(complaints);
    }

    @PutMapping("/update-complaint")
    public ResponseEntity<String> updateComplaint(@RequestBody UpdateComplaintRequest request) {
        boolean isUpdated = accommodationService.updateComplaintDescription(request.getComplaintId(), request.getDescription());
        if (isUpdated) {
            return ResponseEntity.ok("Complaint updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update complaint.");
        }
    }

}
