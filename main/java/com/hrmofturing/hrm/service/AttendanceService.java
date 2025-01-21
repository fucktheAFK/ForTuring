package com.hrmofturing.hrm.service;

import com.hrmofturing.hrm.model.Attendance;
import com.hrmofturing.hrm.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public List<Attendance> getAttendanceByUserId(Long userId) {
        return attendanceRepository.findByUserId(userId);
    }

    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }
}
