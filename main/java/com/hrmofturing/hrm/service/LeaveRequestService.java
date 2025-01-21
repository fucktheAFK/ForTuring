package com.hrmofturing.hrm.service;

import com.hrmofturing.hrm.model.LeaveRequest;
import com.hrmofturing.hrm.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestService {
    private final LeaveRequestRepository leaveRequestRepository;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    public List<LeaveRequest> getLeaveRequestsByUserId(Long userId) {
        return leaveRequestRepository.findByUserId(userId);
    }

    public LeaveRequest saveLeaveRequest(LeaveRequest leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getAllPendingRequests() {
        return leaveRequestRepository.findByStatus("pending");
    }

    public void approveLeaveRequest(Long id) {
        leaveRequestRepository.updateStatus(id, "approved");
    }

    public void rejectLeaveRequest(Long id) {
        leaveRequestRepository.updateStatus(id, "rejected");
    }
}
