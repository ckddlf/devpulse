package com.example.devpulse.controller;

import com.example.devpulse.dto.GitRepositoryDto;
import com.example.devpulse.entity.Repository;
import com.example.devpulse.security.CustomOAuth2User;
import com.example.devpulse.service.GitRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/repositories")
@RequiredArgsConstructor
public class RepositoryController {
    
    private final GitRepositoryService gitRepositoryService;
    
    /**
     * 내 레포지토리 동기화
     */
    @PostMapping("/sync")
    public ResponseEntity<Map<String, String>> syncRepositories(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        
        Long userId = oAuth2User.getUserId();
        gitRepositoryService.syncUserRepositories(userId);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Repository sync completed successfully");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 내 레포지토리 목록 조회
     */
    @GetMapping("/my")
    public ResponseEntity<List<GitRepositoryDto>> getMyRepositories(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        
        Long userId = oAuth2User.getUserId();
        List<Repository> repositories = gitRepositoryService.getUserRepositories(userId);
        
        // Entity -> DTO 변환
        List<GitRepositoryDto> dtos = repositories.stream()
                .map(GitRepositoryDto::from)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * Fork가 아닌 내 레포지토리만 조회
     */
    @GetMapping("/my/own")
    public ResponseEntity<List<GitRepositoryDto>> getMyOwnRepositories(
            @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        
        Long userId = oAuth2User.getUserId();
        List<Repository> repositories = gitRepositoryService.getUserOwnRepositories(userId);
        
        // Entity -> DTO 변환
        List<GitRepositoryDto> dtos = repositories.stream()
                .map(GitRepositoryDto::from)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(dtos);
    }
}