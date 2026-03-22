# GitHub Access Analyzer

A Spring Boot backend application that analyzes GitHub repository access using REST APIs.

---

## Overview

This project integrates with the GitHub API to analyze repository access.

It fetches repositories of a given user and identifies contributors for each repository, creating a mapping of users to repositories they have contributed to.

---

## Features

- Fetch GitHub repositories of a user  
- Retrieve contributors for each repository  
- Generate user-to-repository mapping  
- REST API endpoint for access report  
- Handles authentication errors and API failures  
- Removes duplicate entries for clean output  

---

## API Endpoint

GET http://localhost:8080/api/github/access-report?org={username}

Example:  
http://localhost:8080/api/github/access-report?org=DG3-here

---

## Setup Instructions

1. Clone the repository  
2. Open in IntelliJ IDEA  
3. Add your GitHub token in:  
   src/main/resources/application.properties  

   github.token=YOUR_TOKEN  

4. Run the application  

---

## Sample Output

{
  "Divyansh Garg": ["DG3-here", "github-access-analyzer"],
  "DG3-here": ["Early-Diabetes-Detection", "Law-Connect", "Career-Craft", "github-access-analyzer", "Share-N-Care"]
}

---

## Important Notes

- GitHub restricts collaborator access for personal repositories  
- Contributors API is used instead to fetch access data  
- Invalid or expired tokens may result in authentication errors (401 Unauthorized)  

---

## Tech Stack

- Java  
- Spring Boot  
- GitHub REST API  
- Maven  

---

## Future Improvements

- Pagination support for large repositories  
- Parallel API calls for better performance  
- Caching for faster response  
- Handling GitHub API rate limits  

---

## Author

Divyansh Garg
