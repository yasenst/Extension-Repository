package com.extensionrepository.service;

import com.extensionrepository.util.Constants;
import com.extensionrepository.entity.Extension;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GitHub;

import java.io.IOException;

public class GitHubService {
    private static final String AUTH_KEY = "55ec50c31ca927df3a79dd665c2928718fa69056";

    public static Extension fetchGithubInfo(Extension extension) {
        GitHub gitHub = null;
        try {
                gitHub = GitHub.connectUsingOAuth(AUTH_KEY);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String repositoryName = extension.getRepositoryLink().substring(Constants.GITHUB_URL.length());
        try {

            extension.setOpenIssues(gitHub.getRepository(repositoryName).getOpenIssueCount());
            extension.setPullRequests(gitHub.getRepository(repositoryName).getPullRequests(GHIssueState.OPEN).size());
            extension.setLastCommit(gitHub.getRepository(repositoryName).listCommits().asList().get(0).getCommitDate());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return extension;
    }
}
