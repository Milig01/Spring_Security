package org.example.spring_boot_test.controller;

import lombok.RequiredArgsConstructor;
import org.example.spring_boot_test.model.User;
import org.example.spring_boot_test.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping
    public String adminPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", userService.findByEmail(user.getEmail()));
        return "admin_page";
    }

    @GetMapping("/updatePage")
    public String updatePage(Model model, @RequestParam String email) {
        model.addAttribute("user", userService.findByEmail(email));
        return "update_page";
    }

    @GetMapping("/deletePage")
    public String deletePage(Model model, @RequestParam String email) {
        model.addAttribute("user", userService.findByEmail(email));
        return "delete_page";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(userService.findByEmail(user.getEmail()).getPassword());
            userService.update(user);
        } else {
            userService.save(user);
        }
        return "redirect:/admin";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@ModelAttribute("user") User user, @AuthenticationPrincipal User admin) {
        User userToDelete = userService.findByEmail(user.getEmail());
        userService.delete(userToDelete);
        if (admin.getEmail().equals(userToDelete.getEmail())) {
            return "redirect:/login";
        }
        return "redirect:/admin";
    }
}
