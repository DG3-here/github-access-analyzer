package com.divyansh.github_analyzer.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class GitHubClient {

    @Value("${github.token}")
    private String token;

    private final RestTemplate restTemplate = new RestTemplate();

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + token); // FIXED AUTH FORMAT
        headers.set("Accept", "application/vnd.github+json");
        return headers;
    }

    public List<Map<String, Object>> getRepos(String username) {
        String url = "https://api.github.com/users/" + username + "/repos";
        HttpEntity<String> entity = new HttpEntity<>(getHeaders());

        ResponseEntity<List> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, List.class);

        return response.getBody();
    }

    public List<Map<String, Object>> getCommits(String username, String repo) {
        String url = "https://api.github.com/repos/" + username + "/" + repo + "/commits";
        HttpEntity<String> entity = new HttpEntity<>(getHeaders());

        ResponseEntity<List> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, List.class);

        return response.getBody();
    }
}