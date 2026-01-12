package com.example.devpulse.controller;

import com.example.devpulse.security.CustomOAuth2User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class AuthController {
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/login/success")
    public String loginSuccess(@AuthenticationPrincipal CustomOAuth2User oAuth2User, Model model) {
        log.info("Login success page - User: {}", oAuth2User.getName());
        model.addAttribute("username", oAuth2User.getAttributes().get("login"));
        model.addAttribute("avatarUrl", oAuth2User.getAttributes().get("avatar_url"));
        return "success";
    }
}