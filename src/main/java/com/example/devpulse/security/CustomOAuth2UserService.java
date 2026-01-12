package com.example.devpulse.security;

import com.example.devpulse.entity.User;
import com.example.devpulse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    
    private final UserRepository userRepository;
    
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        
        log.info("OAuth2 User Attributes: {}", oauth2User.getAttributes());
        
        // GitHub에서 받은 사용자 정보 추출
        Map<String, Object> attributes = oauth2User.getAttributes();
        String githubId = String.valueOf(attributes.get("id"));
        String username = (String) attributes.get("login");
        String email = (String) attributes.get("email");
        String avatarUrl = (String) attributes.get("avatar_url");
        String bio = (String) attributes.get("bio");
        
        // Access Token 추출
        String accessToken = userRequest.getAccessToken().getTokenValue();
        
        // 사용자 조회 또는 생성
        User user = userRepository.findByGithubId(githubId)
                .map(existingUser -> updateUser(existingUser, username, email, avatarUrl, bio, accessToken))
                .orElseGet(() -> createUser(githubId, username, email, avatarUrl, bio, accessToken));
        
        log.info("User saved/updated: {}", user.getUsername());
        
        return new CustomOAuth2User(oauth2User, user.getId(), user.getGithubId());
    }
    
    private User createUser(String githubId, String username, String email, 
                           String avatarUrl, String bio, String accessToken) {
        User newUser = User.builder()
                .githubId(githubId)
                .username(username)
                .email(email)
                .avatarUrl(avatarUrl)
                .bio(bio)
                .githubAccessToken(accessToken)
                .build();
        
        return userRepository.save(newUser);
    }
    
    private User updateUser(User user, String username, String email, 
                           String avatarUrl, String bio, String accessToken) {
        user.setUsername(username);
        user.setEmail(email);
        user.setAvatarUrl(avatarUrl);
        user.setBio(bio);
        user.setGithubAccessToken(accessToken);
        
        return userRepository.save(user);
    }
}