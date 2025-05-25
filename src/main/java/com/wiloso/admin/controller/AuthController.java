package com.wiloso.admin.controller;

import com.wiloso.admin.model.User;
import com.wiloso.admin.repository.UserRepository;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// @RestController
@Controller
// @CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String nama,
            @RequestParam String email,
            @RequestParam String password,
            Model model
    ) {
        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("message", "Email sudah digunakan.");
            return "register";
        }

        User user = new User();
        user.setNama(nama);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        model.addAttribute("message", "Registrasi berhasil. Silakan login.");
        return "login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            Model model,
            HttpSession session
    ) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            session.setAttribute("user", userOpt.get());
            return "redirect:/dashboard";
        }
        model.addAttribute("message", "Email atau password salah.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("nama", user.getNama());
        return "dashboard"; // create dashboard.html
    }
}
