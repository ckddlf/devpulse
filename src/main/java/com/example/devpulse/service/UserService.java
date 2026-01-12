package com.example.devpulse.service;

import com.example.devpulse.dto.UserDto;
import com.example.devpulse.entity.User;
import com.example.devpulse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserDto getUserByGithubId(String githubId) {
        User user = userRepository.findByGithubId(githubId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return convertToDto(user);
    }
    
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return convertToDto(user);
    }
    
    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .githubId(user.getGithubId())
                .username(user.getUsername())
                .email(user.getEmail())
                .avatarUrl(user.getAvatarUrl())
                .bio(user.getBio())
                .subscriptionType(user.getSubscriptionType())
                .createdAt(user.getCreatedAt())
                .build();
    }
}