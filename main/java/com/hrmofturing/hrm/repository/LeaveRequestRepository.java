package com.hrmofturing.hrm.repository;

import com.hrmofturing.hrm.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByUserId(Long userId);
    List<LeaveRequest> findByStatus(String status);

    // 自定义查询示例：更新请假状态
    @org.springframework.data.jpa.repository.Modifying
    @org.springframework.data.jpa.repository.Query("UPDATE LeaveRequest lr SET lr.status = :status WHERE lr.id = :id")
    void updateStatus(Long id, String status);
}