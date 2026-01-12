package com.example.devpulse.security;

import com.example.devpulse.security.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                       HttpServletResponse response,
                                       Authentication authentication) throws IOException, ServletException {
        
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        
        log.info("OAuth2 Login Success - User ID: {}, GitHub ID: {}", 
                oAuth2User.getUserId(), oAuth2User.getGithubId());
        
        // 프론트엔드 URL로 리다이렉트 (나중에 프론트엔드 개발 시 수정)
        String targetUrl = "http://localhost:3000/dashboard?login=success";
        
        // 개발 중에는 간단한 성공 페이지로 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, "/login/success");
    }
}