package com.hrmofturing.hrm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class UserDTO extends BaseDTO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "密码不能为空")
    private String password;

    private String role;

    private String avatar;

    // Builder Pattern
    private UserDTO(Builder builder) {
        this.setId(builder.id);
        this.username = builder.username;
        this.password = builder.password;
        this.role = builder.role;
        this.avatar = builder.avatar;
    }

    public static class Builder {
        private Long id;
        private String username;
        private String password;
        private String role;
        private String avatar;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder avatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this);
        }
    }
}