package com.divyansh.github_analyzer.controller;

import com.divyansh.github_analyzer.service.GitHubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    private final GitHubService service;

    public GitHubController(GitHubService service) {
        this.service = service;
    }

    @GetMapping("/access-report")
    public Map<String, List<String>> getAccessReport(@RequestParam String org) {
        return service.generateAccessReport(org);
    }
}