package com.divyansh.github_analyzer.service;

import com.divyansh.github_analyzer.client.GitHubClient;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GitHubService {

    private final GitHubClient client;

    public GitHubService(GitHubClient client) {
        this.client = client;
    }

    public Map<String, List<String>> generateAccessReport(String username) {

        // Use Set to remove duplicates
        Map<String, Set<String>> userRepoMap = new HashMap<>();

        List<Map<String, Object>> repos = client.getRepos(username);

        if (repos == null || repos.isEmpty()) {
            return new HashMap<>();
        }

        for (Map<String, Object> repo : repos) {

            Object repoNameObj = repo.get("name");
            if (repoNameObj == null) continue;

            String repoName = repoNameObj.toString();

            List<Map<String, Object>> commits = client.getCommits(username, repoName);

            // fallback if commits fail
            if (commits == null || commits.isEmpty()) {
                userRepoMap
                        .computeIfAbsent(username, k -> new HashSet<>())
                        .add(repoName);
                continue;
            }

            for (Map<String, Object> commitObj : commits) {
                try {
                    Map commit = (Map) commitObj.get("commit");
                    if (commit == null) continue;

                    Map author = (Map) commit.get("author");
                    if (author == null) continue;

                    Object nameObj = author.get("name");
                    if (nameObj == null) continue;

                    String authorName = nameObj.toString();

                    userRepoMap
                            .computeIfAbsent(authorName, k -> new HashSet<>())
                            .add(repoName);

                } catch (Exception e) {
                    continue;
                }
            }
        }

        // Convert Set → List (for clean JSON output)
        Map<String, List<String>> finalResult = new HashMap<>();

        for (Map.Entry<String, Set<String>> entry : userRepoMap.entrySet()) {
            finalResult.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }

        return finalResult;
    }
}