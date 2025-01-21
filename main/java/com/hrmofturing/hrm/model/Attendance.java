package com.hrmofturing.hrm.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendances", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date"}))
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // 启用乐观锁
    @Version
    private Integer version;

    public enum Status {
        PRESENT, LATE, ABSENT, EARLY_LEAVE
    }

    public Attendance(LocalDate date, Status status, User user) {
        this.date = date;
        this.status = status;
        this.user = user;
    }

    public Attendance() {}

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}