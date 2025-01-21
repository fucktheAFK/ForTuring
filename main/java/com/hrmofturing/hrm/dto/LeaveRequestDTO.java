package com.hrmofturing.hrm.dto;

import com.hrmofturing.hrm.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class LeaveRequestDTO extends BaseDTO {
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    private String leaveType;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    // Builder Pattern
    private LeaveRequestDTO(Builder builder) {
        this.setId(builder.id);
        this.userId = builder.userId;
        this.leaveType = builder.leaveType;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.status = builder.status;
    }

    public static class Builder {
        private Long id;
        private Long userId;
        private String leaveType;
        private LocalDate startDate;
        private LocalDate endDate;
        private String status;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder leaveType(String leaveType) {
            this.leaveType = leaveType;
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public LeaveRequestDTO build() {
            return new LeaveRequestDTO(this);
        }
    }
}
