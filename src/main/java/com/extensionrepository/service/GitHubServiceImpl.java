package com.extensionrepository.service;

import com.extensionrepository.service.base.GitHubService;
import com.extensionrepository.util.Constants;
import com.extensionrepository.entity.Extension;
import org.kohsuke.github.GHFileNotFoundException;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class GitHubServiceImpl implements GitHubService{
    private  GitHub gitHub;

    public GitHubServiceImpl() {
        try {
            gitHub = GitHub.connectUsingOAuth(Constants.GITHUB_AUTH_KEY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int fetchPullRequests(String repositoryLink) throws GHFileNotFoundException, IOException {
        String repositoryName = repositoryLink.substring(Constants.GITHUB_URL.length());

        int pullRequestsCount = 0;
        try {
            pullRequestsCount = gitHub.getRepository(repositoryName).getPullRequests(GHIssueState.OPEN).size();
        } catch (GHFileNotFoundException gh) {
            throw new GHFileNotFoundException(Constants.GITHUB_NOT_FOUND);
        } catch (IOException e) {
            throw new IOException();
        }

        return pullRequestsCount;
    }

    @Override
    public int fetchOpenIssues(String repositoryLink) throws GHFileNotFoundException, IOException {
        String repositoryName = repositoryLink.substring(Constants.GITHUB_URL.length());

        int openIssuesCount = 0;
        try {
            openIssuesCount = gitHub.getRepository(repositoryName).getOpenIssueCount() - fetchPullRequests(repositoryLink);
        } catch (GHFileNotFoundException gh) {
            throw new GHFileNotFoundException(Constants.GITHUB_NOT_FOUND);
        } catch (IOException e) {
            throw new IOException();
        }

        return openIssuesCount;
    }

    @Override
    public Date fetchLastCommit(String repositoryLink) throws GHFileNotFoundException, IOException {
        String repositoryName = repositoryLink.substring(Constants.GITHUB_URL.length());

        Date lastCommit = null;
        try {
            lastCommit = gitHub.getRepository(repositoryName).listCommits().asList().get(0).getCommitDate();
        } catch (GHFileNotFoundException gh) {
            throw new GHFileNotFoundException(Constants.GITHUB_NOT_FOUND);
        } catch (IOException e) {
            throw new IOException();
        }

        return lastCommit;
    }
}
