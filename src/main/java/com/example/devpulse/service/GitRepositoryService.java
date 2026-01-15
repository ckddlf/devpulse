package com.example.devpulse.service;

import com.example.devpulse.client.GitHubApiClient;
import com.example.devpulse.dto.GitHubRepoDto;
import com.example.devpulse.entity.*;
import com.example.devpulse.repository.RepositoryRepository;
import com.example.devpulse.repository.TechStackRepository;
import com.example.devpulse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GitRepositoryService {
    
    private final RepositoryRepository gitRepositoryRepository;
    private final UserRepository userRepository;
    private final TechStackRepository techStackRepository;
    private final GitHubApiClient gitHubApiClient;
    
    @Transactional
    public void syncUserRepositories(Long userId) {
        log.info("Starting repository sync for user ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        
        String accessToken = user.getGithubAccessToken();
        String username = user.getUsername();
        
        List<GitHubRepoDto> githubRepos = gitHubApiClient.getUserRepositories(accessToken, username);
        
        log.info("Found {} repositories for user {}", githubRepos.size(), username);
        
        for (GitHubRepoDto repoDto : githubRepos) {
            try {
                syncRepository(user, repoDto, accessToken);
            } catch (Exception e) {
                log.error("Error syncing repository {}: {}", repoDto.getName(), e.getMessage(), e);
            }
        }
        
        log.info("Repository sync completed for user ID: {}", userId);
    }
    
    @Transactional
    public void syncRepository(User user, GitHubRepoDto repoDto, String accessToken) {
        String githubRepoId = String.valueOf(repoDto.getId());
        
        Repository repository = gitRepositoryRepository.findByGithubRepoId(githubRepoId)
                .orElseGet(() -> Repository.builder()
                        .user(user)
                        .githubRepoId(githubRepoId)
                        .build());
        
        repository.setName(repoDto.getName());
        repository.setDescription(repoDto.getDescription());
        repository.setUrl(repoDto.getHtmlUrl());
        repository.setStars(repoDto.getStargazersCount());
        repository.setForks(repoDto.getForksCount());
        repository.setIsFork(repoDto.getFork());
        repository.setLastSyncedAt(LocalDateTime.now());
        
        repository = gitRepositoryRepository.save(repository);
        
        log.info("Saved repository: {}", repository.getName());
        
        syncRepositoryLanguages(repository, accessToken, user.getUsername(), repoDto.getName());
    }
    
    @Transactional
    public void syncRepositoryLanguages(Repository repository, String accessToken, 
                                       String owner, String repoName) {
        try {
            Map<String, Long> languages = gitHubApiClient.getRepositoryLanguages(accessToken, owner, repoName);
            
            if (languages == null || languages.isEmpty()) {
                log.info("No languages found for repository: {}", repoName);
                return;
            }
            
            long totalBytes = languages.values().stream().mapToLong(Long::longValue).sum();
            
            // 방법 1: 기존 데이터 삭제 - Iterator 사용
            // repository.getTechStacks().clear()가 아닌 새로운 리스트로 교체
            List<RepositoryTechStack> newTechStacks = new ArrayList<>();
            
            for (Map.Entry<String, Long> entry : languages.entrySet()) {
                String languageName = entry.getKey();
                Long bytes = entry.getValue();
                
                BigDecimal percentage = BigDecimal.valueOf(bytes)
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(totalBytes), 2, RoundingMode.HALF_UP);
                
                TechStack techStack = techStackRepository.findByName(languageName)
                        .orElseGet(() -> techStackRepository.save(
                                TechStack.builder()
                                        .name(languageName)
                                        .category("LANGUAGE")
                                        .build()
                        ));
                
                RepositoryTechStackId id = new RepositoryTechStackId(
                        repository.getId(), 
                        techStack.getId()
                );
                
                RepositoryTechStack repoTechStack = RepositoryTechStack.builder()
                        .id(id)
                        .repository(repository)
                        .techStack(techStack)
                        .bytes(bytes)
                        .percentage(percentage)
                        .build();
                
                newTechStacks.add(repoTechStack);
                
                log.info("Added language {} ({}%) to repository {}", 
                        languageName, percentage, repoName);
            }
            
            // 기존 컬렉션을 비우고 새로운 데이터로 교체
            repository.getTechStacks().clear();
            repository.getTechStacks().addAll(newTechStacks);
            
            gitRepositoryRepository.save(repository);
            
        } catch (Exception e) {
            log.error("Error syncing languages for repository {}: {}", repoName, e.getMessage(), e);
            throw new RuntimeException("Failed to sync languages for " + repoName, e);
        }
    }
    
    @Transactional(readOnly = true)
    public List<Repository> getUserRepositories(Long userId) {
        return gitRepositoryRepository.findByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public List<Repository> getUserOwnRepositories(Long userId) {
        return gitRepositoryRepository.findByUserIdAndIsFork(userId, false);
    }
}