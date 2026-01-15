package com.example.devpulse.client;

import com.example.devpulse.dto.GitHubRepoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class GitHubApiClient {
    
    private static final Logger log = LoggerFactory.getLogger(GitHubApiClient.class);
    private static final String GITHUB_API_BASE_URL = "https://api.github.com";
    
    private final WebClient webClient;
    
    public GitHubApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(GITHUB_API_BASE_URL)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    
    /**
     * 사용자의 모든 레포지토리 가져오기
     */
    public List<GitHubRepoDto> getUserRepositories(String accessToken, String username) {
        log.info("Fetching repositories for user: {}", username);
        
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/users/{username}/repos")
                        .queryParam("type", "owner")  // owner만 (fork 제외하려면)
                        .queryParam("sort", "updated")
                        .queryParam("per_page", 100)
                        .build(username))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToFlux(GitHubRepoDto.class)
                .collectList()
                .doOnSuccess(repos -> log.info("Successfully fetched {} repositories", repos.size()))
                .doOnError(error -> log.error("Error fetching repositories: {}", error.getMessage()))
                .block();
    }
    
    /**
     * 특정 레포지토리의 언어 통계 가져오기
     */
    public Map<String, Long> getRepositoryLanguages(String accessToken, String owner, String repoName) {
        log.info("Fetching languages for repository: {}/{}", owner, repoName);
        
        return webClient.get()
                .uri("/repos/{owner}/{repo}/languages", owner, repoName)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Long>>() {})
                .doOnSuccess(languages -> log.info("Successfully fetched languages: {}", languages.keySet()))
                .doOnError(error -> log.error("Error fetching languages: {}", error.getMessage()))
                .block();
    }
    
    /**
     * 레포지토리의 README 내용 가져오기 (나중에 사용)
     */
    public String getRepositoryReadme(String accessToken, String owner, String repoName) {
        log.info("Fetching README for repository: {}/{}", owner, repoName);
        
        try {
            Map<String, Object> readmeData = webClient.get()
                    .uri("/repos/{owner}/{repo}/readme", owner, repoName)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();
            
            if (readmeData != null && readmeData.containsKey("content")) {
                // GitHub API는 base64로 인코딩된 내용을 반환
                String base64Content = (String) readmeData.get("content");
                return new String(java.util.Base64.getDecoder().decode(base64Content.replaceAll("\\s", "")));
            }
        } catch (Exception e) {
            log.warn("No README found for {}/{}", owner, repoName);
        }
        
        return null;
    }
}