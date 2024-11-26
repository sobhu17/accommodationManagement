package acc.management.accommodationmanagement.service;

import acc.management.accommodationmanagement.models.ComplaintDetails;
import acc.management.accommodationmanagement.repository.ComplaintDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintDao complaintDao;

    public ComplaintService(ComplaintDao complaintDao) {
        this.complaintDao = complaintDao;
    }

    public List<ComplaintDetails> getAllComplaints() {
        return complaintDao.fetchAllComplaints();
    }

    public boolean updateComplaintStatus(int complaintId, String status) {
        return complaintDao.updateComplaintStatus(complaintId, status);
    }
}

