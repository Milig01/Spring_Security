package org.example.spring_boot_test.service;

import org.example.spring_boot_test.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void save(User user);
    List<User> findAll();
    void deleteById(long id);
    User findById(long id);
}
