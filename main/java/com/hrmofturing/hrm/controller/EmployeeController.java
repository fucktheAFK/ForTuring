package com.hrmofturing.hrm.controller;

import com.hrmofturing.hrm.model.Attendance;
import com.hrmofturing.hrm.model.LeaveRequest;
import com.hrmofturing.hrm.model.Salary;
import com.hrmofturing.hrm.model.Training;
import com.hrmofturing.hrm.model.User;
import com.hrmofturing.hrm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private UserService userService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private TrainingService trainingService;

    // 查看个人信息
    @GetMapping("/profile/{id}")
    public User getProfile(@PathVariable Long id) {
        return userService.getUserById(id).orElse(null);
    }

    // 修改个人信息
    @PutMapping("/profile")
    public User updateProfile(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // 查看工资
    @GetMapping("/salary/{id}")
    public List<Salary> getSalaries(@PathVariable Long id) {
        return salaryService.getSalariesByUserId(id);
    }

    // 查看考勤记录
    @GetMapping("/attendance/{id}")
    public List<Attendance> getAttendanceRecords(@PathVariable Long id) {
        return attendanceService.getAttendanceByUserId(id);
    }

    // 提交请假申请
    @PostMapping("/leave-request")
    public LeaveRequest submitLeaveRequest(@RequestBody LeaveRequest leaveRequest) {
        return leaveRequestService.saveLeaveRequest(leaveRequest);
    }

    // 查看培训活动
    @GetMapping("/trainings")
    public List<Training> getTrainings() {
        return trainingService.getAllTrainings();
    }
}
