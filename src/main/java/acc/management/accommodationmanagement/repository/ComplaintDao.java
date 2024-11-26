package acc.management.accommodationmanagement.repository;

import acc.management.accommodationmanagement.models.ComplaintDetails;

import java.util.List;

public interface ComplaintDao {

    public List<ComplaintDetails> fetchAllComplaints();

    public boolean updateComplaintStatus(int complaintId, String status);
}
