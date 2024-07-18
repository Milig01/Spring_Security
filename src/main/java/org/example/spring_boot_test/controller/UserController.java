package org.example.spring_boot_test.controller;

import lombok.RequiredArgsConstructor;
import org.example.spring_boot_test.model.User;
import org.example.spring_boot_test.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String user(@AuthenticationPrincipal UserDetails user, ModelMap model) {
        user = userService.loadUserByUsername(user.getUsername());
        model.addAttribute("user", user);
        return "user/user";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") long id, ModelMap model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") long id) {
        userService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateUser(@AuthenticationPrincipal User user, @ModelAttribute("user") User userUpdate) {
        user =(User) userService.loadUserByUsername(user.getUsername());
        user.setUsername(userUpdate.getUsername());
        user.setAge(userUpdate.getAge());
        userService.save(user);
        return "redirect:/user";
    }
}
