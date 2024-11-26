package acc.management.accommodationmanagement.controller;

import acc.management.accommodationmanagement.Dtos.UpdateComplaintStatus;
import acc.management.accommodationmanagement.models.ComplaintDetails;
import acc.management.accommodationmanagement.service.ComplaintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @GetMapping("/all")
    public List<ComplaintDetails> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @PostMapping("/update-status")
    public ResponseEntity<String> updateComplaintStatus(@RequestBody UpdateComplaintStatus updateRequest) {
        boolean updated = complaintService.updateComplaintStatus(updateRequest.getComplaintId(), updateRequest.getStatus());
        if (updated) {
            return ResponseEntity.ok("Complaint status updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update complaint status.");
        }
    }
}

