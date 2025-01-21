package com.hrmofturing.hrm.service;

import com.hrmofturing.hrm.model.Salary;
import com.hrmofturing.hrm.repository.SalaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {
    private final SalaryRepository salaryRepository;

    public SalaryService(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    public List<Salary> getSalariesByUserId(Long userId) {
        return salaryRepository.findByUserId(userId);
    }

    public Salary saveSalary(Salary salary) {
        return salaryRepository.save(salary);
    }
}
