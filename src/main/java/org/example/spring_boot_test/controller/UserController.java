package org.example.spring_boot_test.controller;

import lombok.RequiredArgsConstructor;
import org.example.spring_boot_test.model.User;
import org.example.spring_boot_test.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String userPage(@AuthenticationPrincipal User user, ModelMap model) {
        model.addAttribute("user", userService.findByEmail(user.getEmail()));
        return "user_page";
    }
}
