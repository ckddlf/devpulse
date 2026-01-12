package com.example.devpulse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryTechStackId implements Serializable {
    
    @Column(name = "repository_id")
    private Long repositoryId;
    
    @Column(name = "tech_stack_id")
    private Long techStackId;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryTechStackId that = (RepositoryTechStackId) o;
        return Objects.equals(repositoryId, that.repositoryId) &&
               Objects.equals(techStackId, that.techStackId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(repositoryId, techStackId);
    }
}