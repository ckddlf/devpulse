package com.example.devpulse.repository;

import com.example.devpulse.entity.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface RepositoryRepository extends JpaRepository<Repository, Long> {
    
    List<Repository> findByUserId(Long userId);
    
    Optional<Repository> findByGithubRepoId(String githubRepoId);
    
    List<Repository> findByUserIdAndIsFork(Long userId, Boolean isFork);
    
    boolean existsByGithubRepoId(String githubRepoId);
}