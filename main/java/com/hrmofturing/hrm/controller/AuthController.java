package com.hrmofturing.hrm.controller;

import com.hrmofturing.hrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // 用户登录
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        if (userService.authenticate(username, password).isPresent()) {
            return "Login successful!";
        } else {
            return "Invalid username or password.";
        }
    }

    // 用户登出
    @PostMapping("/logout")
    public String logout() {
        // 处理用户登出逻辑（清理会话或 Token）
        return "Logout successful!";
    }
}
