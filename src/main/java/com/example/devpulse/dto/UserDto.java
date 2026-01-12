package com.example.devpulse.dto;

import com.example.devpulse.entity.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String githubId;
    private String username;
    private String email;
    private String avatarUrl;
    private String bio;
    private SubscriptionType subscriptionType;
    private LocalDateTime createdAt;
}