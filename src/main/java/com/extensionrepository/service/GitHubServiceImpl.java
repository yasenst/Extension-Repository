package com.extensionrepository.service;

import com.extensionrepository.service.base.ExtensionService;
import com.extensionrepository.service.base.GitHubService;
import com.extensionrepository.util.Constants;
import com.extensionrepository.entity.Extension;
import org.apache.tomcat.util.bcel.Const;
import org.kohsuke.github.GHFileNotFoundException;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class GitHubServiceImpl implements GitHubService{
    private  GitHub gitHub;
    private static Thread syncThread;
    private static Integer syncInterval = 3600000; // default sync is 1 hour
    private static Date lastSync;

    @Autowired
    private ExtensionService extensionService;

    // try to connect to github using oAuth key
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

    // sync repository by creating thread
    @Override
    public void synchronizeRepository() {
        if (syncThread != null) {
            try {
                if (!syncThread.isInterrupted()) {
                    syncThread.wait();
                }
                syncThread.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        syncThread = new Thread(() -> {
            while (true) {
                try {
                    List<Extension> extensions = extensionService.getAll();
                    for (Extension extension : extensions) {
                        extension.setPullRequests(fetchPullRequests(extension.getRepositoryLink()));
                        extension.setOpenIssues(fetchOpenIssues(extension.getRepositoryLink()));
                        extension.setLastCommit(fetchLastCommit(extension.getRepositoryLink()));
                        extension.setLastSync(new Date());
                        extensionService.update(extension);
                        lastSync = new Date();
                        System.out.println("Updated extension " + extension.getName() + " at " + new Date().toString());
                    }
                    Thread.sleep(syncInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        syncThread.setDaemon(true);
        syncThread.start();
    }

    // parameter comes from input fields in ui and is converted to corresponding values
    @Override
    public void setSyncInterval(String newSyncInterval) {
        switch (newSyncInterval) {
            case Constants.QUARTER_HOUR:
                syncInterval = 900000;
                break;
            case Constants.HALF_HOUR:
                syncInterval = 1800000;
                break;
            case Constants.ONE_HOUR:
                syncInterval = 3600000;
                break;
            case Constants.TWO_HOURS:
                syncInterval = 7200000;
                break;
            case Constants.FOUR_HOURS:
                syncInterval = 14400000;
                break;
            case Constants.EIGHT_HOURS:
                syncInterval = 28800000;
                break;
            case Constants.TWELVE_HOURS:
                syncInterval = 43200000;
                break;
        }
    }

    // syncInterval is returned as String to be displayed on the ui when function called from controller
    @Override
    public String getSyncInterval() {
        switch (syncInterval) {
            case  900000:
                return Constants.QUARTER_HOUR;
            case 1800000:
                return Constants.HALF_HOUR;
            case 3600000:
                return Constants.ONE_HOUR;
            case 7200000:
                return Constants.TWO_HOURS;
            case 14400000:
                return Constants.FOUR_HOURS;
            case 28800000:
                return Constants.EIGHT_HOURS;
            case 43200000:
                return Constants.TWELVE_HOURS;

        }
        return Constants.ONE_HOUR;
    }

    @Override
    public Date getLastSync() {
        return lastSync;
    }



    @PostConstruct
    public void initializeSync(){
        synchronizeRepository();
    }

}
