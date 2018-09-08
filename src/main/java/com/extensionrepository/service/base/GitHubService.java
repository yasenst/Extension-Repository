package com.extensionrepository.service.base;

import java.io.IOException;
import java.util.Date;

public interface GitHubService {
    int fetchOpenIssues(String repositoryLink) throws IOException;
    int fetchPullRequests(String repositoryLink) throws IOException;
    Date fetchLastCommit(String repositoryLink) throws IOException;
    void synchronizeRepository();
    void setSyncInterval(String newSyncInterval);
    Date getLastSync();
    String getSyncInterval();
}
