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
@RequestMapping("/admin")
public class AdminController {

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

    // 员工管理
    @GetMapping("/employees")
    public List<User> getAllEmployees() {
        return userService.getAllUsers();
    }

    @PostMapping("/employee")
    public User addEmployee(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/employee")
    public User updateEmployee(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/employee/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        userService.deleteUser(id);
        return "Employee deleted successfully!";
    }

    // 考勤管理
    @GetMapping("/attendance/{userId}")
    public List<Attendance> getAttendanceByUserId(@PathVariable Long userId) {
        return attendanceService.getAttendanceByUserId(userId);
    }

    @PostMapping("/attendance")
    public Attendance addAttendance(@RequestBody Attendance attendance) {
        return attendanceService.saveAttendance(attendance);
    }

    // 请假申请管理
    @GetMapping("/leave-requests")
    public List<LeaveRequest> getAllPendingLeaveRequests() {
        return leaveRequestService.getAllPendingRequests();
    }

    @PutMapping("/leave-request/approve/{id}")
    public String approveLeaveRequest(@PathVariable Long id) {
        leaveRequestService.approveLeaveRequest(id);
        return "Leave request approved!";
    }

    @PutMapping("/leave-request/reject/{id}")
    public String rejectLeaveRequest(@PathVariable Long id) {
        leaveRequestService.rejectLeaveRequest(id);
        return "Leave request rejected!";
    }

    // 工资管理
    @GetMapping("/salary/{userId}")
    public List<Salary> getSalariesByUserId(@PathVariable Long userId) {
        return salaryService.getSalariesByUserId(userId);
    }

    @PostMapping("/salary")
    public Salary addSalary(@RequestBody Salary salary) {
        return salaryService.saveSalary(salary);
    }

    // 培训管理
    @GetMapping("/trainings")
    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @PostMapping("/training")
    public Training addTraining(@RequestBody Training training) {
        return trainingService.saveTraining(training);
    }

    @DeleteMapping("/training/{id}")
    public String deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return "Training deleted successfully!";
    }
}
