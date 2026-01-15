package com.example.devpulse.dto;

import com.example.devpulse.entity.Repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
public class GitRepositoryDto {
    
    private Long id;
    private String githubRepoId;
    private String name;
    private String description;
    private String url;
    private Integer stars;
    private Integer forks;
    private Boolean isFork;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastSyncedAt;
    private List<TechStackDto> techStacks;
    
    // Constructors
    public GitRepositoryDto() {}
    
    // Entity -> DTO 변환
    public static GitRepositoryDto from(Repository repository) {
        GitRepositoryDto dto = new GitRepositoryDto();
        dto.setId(repository.getId());
        dto.setGithubRepoId(repository.getGithubRepoId());
        dto.setName(repository.getName());
        dto.setDescription(repository.getDescription());
        dto.setUrl(repository.getUrl());
        dto.setStars(repository.getStars());
        dto.setForks(repository.getForks());
        dto.setIsFork(repository.getIsFork());
        dto.setCreatedAt(repository.getCreatedAt());
        dto.setUpdatedAt(repository.getUpdatedAt());
        dto.setLastSyncedAt(repository.getLastSyncedAt());
        
        // TechStack 변환
        if (repository.getTechStacks() != null) {
            dto.setTechStacks(
                repository.getTechStacks().stream()
                    .map(TechStackDto::from)
                    .collect(Collectors.toList())
            );
        }
        
        return dto;
    }
    
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TechStackDto {
        private Long techStackId;
        private String name;
        private String category;
        private String percentage;
        private Long bytes;
        
        public static TechStackDto from(com.example.devpulse.entity.RepositoryTechStack repoTechStack) {
            TechStackDto dto = new TechStackDto();
            dto.setTechStackId(repoTechStack.getTechStack().getId());
            dto.setName(repoTechStack.getTechStack().getName());
            dto.setCategory(repoTechStack.getTechStack().getCategory());
            dto.setPercentage(repoTechStack.getPercentage() != null ? 
                repoTechStack.getPercentage().toString() : "0");
            dto.setBytes(repoTechStack.getBytes());
            return dto;
        }
        
       
    }
}