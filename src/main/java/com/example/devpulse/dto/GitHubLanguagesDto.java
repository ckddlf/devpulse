package com.example.devpulse.dto;

import java.util.Map;

public class GitHubLanguagesDto {
    
    private Map<String, Long> languages;
    
    public GitHubLanguagesDto() {}
    
    public GitHubLanguagesDto(Map<String, Long> languages) {
        this.languages = languages;
    }
    
    public Map<String, Long> getLanguages() {
        return languages;
    }
    
    public void setLanguages(Map<String, Long> languages) {
        this.languages = languages;
    }
}