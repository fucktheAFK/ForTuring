package com.hrmofturing.hrm.dto;

import com.hrmofturing.hrm.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class AttendanceDTO extends BaseDTO {
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    private LocalDate date;

    private String status;

    // Builder Pattern
    private AttendanceDTO(Builder builder) {
        this.setId(builder.id);
        this.userId = builder.userId;
        this.date = builder.date;
        this.status = builder.status;
    }

    public static class Builder {
        private Long id;
        private Long userId;
        private LocalDate date;
        private String status;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public AttendanceDTO build() {
            return new AttendanceDTO(this);
        }
    }
}
