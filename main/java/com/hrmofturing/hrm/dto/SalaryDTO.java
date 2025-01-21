package com.hrmofturing.hrm.dto;

import com.hrmofturing.hrm.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SalaryDTO extends BaseDTO {
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotNull(message = "基本工资不能为空")
    private BigDecimal baseSalary;

    @NotNull(message = "绩效奖金不能为空")
    private BigDecimal performanceBonus;

    @NotNull(message = "全勤奖金不能为空")
    private BigDecimal fullAttendanceBonus;

    private LocalDate paymentDate;

    // Builder Pattern
    private SalaryDTO(Builder builder) {
        this.setId(builder.id);
        this.userId = builder.userId;
        this.baseSalary = builder.baseSalary;
        this.performanceBonus = builder.performanceBonus;
        this.fullAttendanceBonus = builder.fullAttendanceBonus;
        this.paymentDate = builder.paymentDate;
    }

    public static class Builder {
        private Long id;
        private Long userId;
        private BigDecimal baseSalary;
        private BigDecimal performanceBonus;
        private BigDecimal fullAttendanceBonus;
        private LocalDate paymentDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder baseSalary(BigDecimal baseSalary) {
            this.baseSalary = baseSalary;
            return this;
        }

        public Builder performanceBonus(BigDecimal performanceBonus) {
            this.performanceBonus = performanceBonus;
            return this;
        }

        public Builder fullAttendanceBonus(BigDecimal fullAttendanceBonus) {
            this.fullAttendanceBonus = fullAttendanceBonus;
            return this;
        }

        public Builder paymentDate(LocalDate paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public SalaryDTO build() {
            return new SalaryDTO(this);
        }
    }
}
