package com.example.devpulse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "repository_tech_stacks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepositoryTechStack {
    
    @EmbeddedId
    private RepositoryTechStackId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("repositoryId")
    @JoinColumn(name = "repository_id")
    private Repository repository;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("techStackId")
    @JoinColumn(name = "tech_stack_id")
    private TechStack techStack;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal percentage;
    
    private Long bytes;
}