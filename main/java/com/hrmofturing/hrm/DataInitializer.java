package com.hrmofturing.hrm;

import com.hrmofturing.hrm.model.*;
import com.hrmofturing.hrm.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SalaryRepository salaryRepository;
    private final AttendanceRepository attendanceRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final TrainingRepository trainingRepository;

    public DataInitializer(UserRepository userRepository,
                           SalaryRepository salaryRepository,
                           AttendanceRepository attendanceRepository,
                           LeaveRequestRepository leaveRequestRepository,
                           TrainingRepository trainingRepository) {
        this.userRepository = userRepository;
        this.salaryRepository = salaryRepository;
        this.attendanceRepository = attendanceRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.trainingRepository = trainingRepository;
    }

    @Override
    public void run(String... args) {
        // Check if admin user exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User(null, "admin", "admin123", User.Role.ADMIN, null);
            userRepository.save(admin);
        }

        // Check if employee user exists
        if (userRepository.findByUsername("employee").isEmpty()) {
            User employee = new User(null, "employee", "employee123", User.Role.EMPLOYEE, null);
            userRepository.save(employee);
        }

        // Fetch users
        User admin = userRepository.findByUsername("admin").orElseThrow();
        User employee = userRepository.findByUsername("employee").orElseThrow();

        // Initialize salary data
        if (salaryRepository.count() == 0) {
            Salary salary = new Salary(null, employee, new BigDecimal("3000.00"),
                    new BigDecimal("500.00"), new BigDecimal("200.00"), LocalDate.now());
            salaryRepository.save(salary);
        }

        // Initialize attendance data
        if (attendanceRepository.count() == 0) {
            Attendance attendance = new Attendance(LocalDate.now(), Attendance.Status.PRESENT, employee);
            attendanceRepository.save(attendance);
        }

        // Initialize leave request data
        if (leaveRequestRepository.count() == 0) {
            LeaveRequest leaveRequest = new LeaveRequest(
                    employee,
                    LeaveRequest.LeaveType.SICK,
                    LocalDate.now().minusDays(2),
                    LocalDate.now(),
                    LeaveRequest.Status.APPROVED
            );
            leaveRequestRepository.save(leaveRequest);
        }

        // Initialize training data
        if (trainingRepository.count() == 0) {
            Training training = new Training(
                    "Learn Spring Boot fundamentals",
                    LocalDate.now().minusDays(5),
                    null,
                    LocalDate.now().minusDays(10),
                    Training.Status.COMPLETED,
                    "Spring Boot Basics",
                    employee.getId()
            );
            trainingRepository.save(training);
        }

        System.out.println("Test data initialized successfully!");
    }
}
