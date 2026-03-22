# GitHub Access Analyzer

## Overview
This project is a Spring Boot application that integrates with the GitHub API to analyze repository access.

It fetches repositories of a user and maps which users have access to which repositories.

## Features
- Fetch GitHub repositories
- Retrieve collaborators for each repository
- Generate user-to-repository mapping
- REST API endpoint for access report
- Error handling for API failures and permission issues

## API Endpoint
GET http://localhost:8080/access-report

## Setup Instructions

1. Clone the repository
2. Open in IntelliJ IDEA
3. Add your GitHub token in:
   src/main/resources/application.properties

   github.token=YOUR_TOKEN
   github.org=YOUR_USERNAME

4. Run the application

## Sample Output
{}

## Important Note
GitHub restricts collaborator access for personal repositories.  
Due to this, the API may return an empty result.

This has been handled gracefully using exception handling to ensure system stability.

## Tech Stack
- Java
- Spring Boot
- GitHub REST API

## Future Improvements
- Pagination support for large repositories
- Parallel API calls for performance optimization
- Caching for faster response
- Handling GitHub rate limits
