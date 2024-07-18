package org.example.spring_boot_test.service;

import lombok.RequiredArgsConstructor;
import org.example.spring_boot_test.model.Role;
import org.example.spring_boot_test.model.User;
import org.example.spring_boot_test.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public void save(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {return;}
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {return;}
        for (Role role : user.getRoles()) {
            if (role.getRole().equals("ROLE_ADMIN")) return;
        }
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public User findById(long id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {throw new UsernameNotFoundException("User not found");}
        return user;
    }
}
