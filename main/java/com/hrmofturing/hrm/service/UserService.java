package com.hrmofturing.hrm.service;

import com.hrmofturing.hrm.model.User;
import com.hrmofturing.hrm.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 用户认证
     *
     * @param username 用户名
     * @param password 密码
     * @return 认证成功的用户信息（Optional 包装）
     */
    public Optional<User> authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }

    /**
     * 保存或更新用户信息
     *
     * @param user 用户信息
     * @return 保存后的用户对象
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 根据 ID 获取用户信息
     *
     * @param id 用户 ID
     * @return 用户信息（Optional 包装）
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 删除用户
     *
     * @param id 用户 ID
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
