package com.divyansh.github_analyzer.service;

import com.divyansh.github_analyzer.client.GitHubClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GitHubService {

    private final GitHubClient client;

    public GitHubService(GitHubClient client) {
        this.client = client;
    }

    public Map<String, List<String>> generateAccessReport() {

        Map<String, List<String>> userRepoMap = new HashMap<>();

        try {
            List<Map<String, Object>> repos = client.getRepos();

            if (repos == null) return userRepoMap;

            for (Map<String, Object> repo : repos) {

                String repoName = (String) repo.get("name");

                try {
                    List<Map<String, Object>> collaborators = client.getCollaborators(repoName);

                    if (collaborators == null) continue;

                    for (Map<String, Object> user : collaborators) {
                        String username = (String) user.get("login");

                        userRepoMap
                                .computeIfAbsent(username, k -> new ArrayList<>())
                                .add(repoName);
                    }

                } catch (Exception e) {
                    // Skip repos where collaborators API fails
                    System.out.println("Skipping repo: " + repoName);
                }
            }

        } catch (Exception e) {
            System.out.println("Error fetching repos");
        }

        return userRepoMap;
    }
}