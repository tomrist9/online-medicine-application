package com.online.medicine.application.controller;

import com.online.medicine.application.security.jwt.JwtUtil;
import com.online.medicine.application.security.model.AuthRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("authRequest", new AuthRequestDto("", "")
        );
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute AuthRequestDto authRequest, Model model) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.username(),
                            authRequest.password()
                    )
            );
            String token = jwtUtil.generateToken(authentication.getName());
            model.addAttribute("token", token);
            model.addAttribute("message", "Login successful! Your token:");
            return "token";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
}
