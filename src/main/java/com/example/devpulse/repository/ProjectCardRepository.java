package com.example.devpulse.repository;

import com.example.devpulse.entity.ProjectCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectCardRepository extends JpaRepository<ProjectCard, Long> {
    
    Optional<ProjectCard> findByRepositoryId(Long repositoryId);
    
    void deleteByRepositoryId(Long repositoryId);
}