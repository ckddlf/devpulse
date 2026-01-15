package com.example.devpulse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@Setter
public class GitHubRepoDto {
    
    private Long id;
    private String name;
    private String description;
    
    @JsonProperty("html_url")
    private String htmlUrl;
    
    @JsonProperty("stargazers_count")
    private Integer stargazersCount;
    
    @JsonProperty("forks_count")
    private Integer forksCount;
    
    private Boolean fork;
    
   @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;
    
    @JsonProperty("languages_url")
    private String languagesUrl;
    
    private Owner owner;
    
    // Constructors
    public GitHubRepoDto() {}
    
    
    // Inner class for owner
    public static class Owner {
        private String login;
        
        public Owner() {}
        
        public String getLogin() {
            return login;
        }
        
        public void setLogin(String login) {
            this.login = login;
        }
    }
}