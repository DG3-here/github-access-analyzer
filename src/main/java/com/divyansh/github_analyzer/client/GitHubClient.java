package com.divyansh.github_analyzer.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GitHubClient {

    @Value("${github.token}")
    private String token;

    @Value("${github.org}")
    private String username;

    private final RestTemplate restTemplate = new RestTemplate();

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Accept", "application/vnd.github+json");
        return headers;
    }

    public List<Map<String, Object>> getRepos() {
        try {
            String url = "https://api.github.com/users/" + username + "/repos";
            HttpEntity<String> entity = new HttpEntity<>(getHeaders());

            ResponseEntity<List> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, List.class);

            return response.getBody();

        } catch (Exception e) {
            System.out.println("Error fetching repos");
            return new ArrayList<>();
        }
    }

    public List<Map<String, Object>> getCollaborators(String repo) {
        try {
            String url = "https://api.github.com/repos/" + username + "/" + repo + "/collaborators";
            HttpEntity<String> entity = new HttpEntity<>(getHeaders());

            ResponseEntity<List> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, List.class);

            return response.getBody();

        } catch (HttpClientErrorException.Forbidden e) {
            // GitHub restricts collaborator access
            System.out.println("Access denied for repo: " + repo);
            return new ArrayList<>();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}