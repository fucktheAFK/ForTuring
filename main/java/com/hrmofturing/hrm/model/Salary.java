package com.hrmofturing.hrm.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "salaries")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal baseSalary;

    @Column(nullable = false)
    private BigDecimal performanceBonus;

    @Column(nullable = false)
    private BigDecimal fullAttendanceBonus;

    @Column(nullable = false)
    private LocalDate paymentDate;

    // Salary 类
    public Salary(Long id, User user, BigDecimal baseSalary, BigDecimal performanceBonus, BigDecimal fullAttendanceBonus, LocalDate paymentDate) {
        this.id = id;
        this.user = user;
        this.baseSalary = baseSalary;
        this.performanceBonus = performanceBonus;
        this.fullAttendanceBonus = fullAttendanceBonus;
        this.paymentDate = paymentDate;
    }
    public Salary(){}
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public BigDecimal getPerformanceBonus() {
        return performanceBonus;
    }

    public void setPerformanceBonus(BigDecimal performanceBonus) {
        this.performanceBonus = performanceBonus;
    }

    public BigDecimal getFullAttendanceBonus() {
        return fullAttendanceBonus;
    }

    public void setFullAttendanceBonus(BigDecimal fullAttendanceBonus) {
        this.fullAttendanceBonus = fullAttendanceBonus;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
