package com.extensionrepository.service.base;

import java.util.Date;

public interface GitHubService {
    int fetchOpenIssues(String repositoryLink);
    int fetchPullRequests(String repositoryLink);
    Date fetchLastCommit(String repositoryLink);
}
