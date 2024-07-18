package org.example.spring_boot_test.controller;

import lombok.RequiredArgsConstructor;
import org.example.spring_boot_test.model.User;
import org.example.spring_boot_test.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping
    public String admin(@AuthenticationPrincipal UserDetails user, ModelMap model) {
        user = userService.loadUserByUsername(user.getUsername());
        model.addAttribute("user", user);
        return "admin/admin";
    }

    @GetMapping("/getAll")
    public String getAll(ModelMap model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/getAll";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin/getAll";
    }
}
