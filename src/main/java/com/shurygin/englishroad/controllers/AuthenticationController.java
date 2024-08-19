package com.shurygin.englishroad.controllers;

import com.shurygin.englishroad.dto.UserDTO;
import com.shurygin.englishroad.model.User;
import com.shurygin.englishroad.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public AuthenticationController(
            UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

//    @PreAuthorize("hasRole('USER')")
//    private void userMethod() {
//        System.out.println("userMethod()");
//    }

    @PostMapping("/register")
    public String processRegistration(UserDTO user) {
        Optional<User> byUsername = userRepo.findByUsername(user.getUsername());
        if (byUsername.isPresent()) { // user already exists
            return "redirect:/auth?error-user-exists";
        }
        userRepo.save(new User(user.getUsername(), passwordEncoder.encode(user.getPassword())));
        return "redirect:/auth?success-registration";
    }

}
