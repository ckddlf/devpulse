package com.example.devpulse.repository;

import com.example.devpulse.entity.TechStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TechStackRepository extends JpaRepository<TechStack, Long> {
    
    Optional<TechStack> findByName(String name);
    
    boolean existsByName(String name);
}