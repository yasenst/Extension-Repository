/*
package com.extensionrepository.util;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.service.base.ExtensionService;
import com.extensionrepository.service.base.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class DataSynchronization {
    private GitHubService gitHubService;
    private ExtensionService extensionService;

    @Autowired
    public DataSynchronization(GitHubService gitHubService, ExtensionService extensionService) {
        this.gitHubService = gitHubService;
        this.extensionService = extensionService;
    }

    @Scheduled(fixedDelay = 600000, initialDelay = 300000)
    public void synchronizeExtensions() {
        List<Extension> extensions = extensionService.getAll();

        for (Extension extension : extensions) {
            try {
                extension.setPullRequests(gitHubService.fetchPullRequests(extension.getRepositoryLink()));
                extension.setOpenIssues(gitHubService.fetchOpenIssues(extension.getRepositoryLink()));
                extension.setLastCommit(gitHubService.fetchLastCommit(extension.getRepositoryLink()));
                extensionService.update(extension);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
*/