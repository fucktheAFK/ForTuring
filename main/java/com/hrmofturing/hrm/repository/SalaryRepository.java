package com.hrmofturing.hrm.repository;

import com.hrmofturing.hrm.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findByUserId(Long userId);
}
