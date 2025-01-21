package com.hrmofturing.hrm.dto;

import com.hrmofturing.hrm.dto.BaseDTO;

import java.time.LocalDate;

public class TrainingDTO extends BaseDTO {
    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    // Builder Pattern
    private TrainingDTO(Builder builder) {
        this.setId(builder.id);
        this.title = builder.title;
        this.description = builder.description;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.status = builder.status;
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;
        private String status;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
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

        public TrainingDTO build() {
            return new TrainingDTO(this);
        }
    }
}
