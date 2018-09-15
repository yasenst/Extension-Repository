package com.extensionrepository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "extensions")
public class Extension {

    @Id
    @Column(name = "extension_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "version")
    private String version;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "number_of_downloads")
    private int numberOfDownloads;

    @Column(name = "repository_link")
    private String repositoryLink;

    @Column(name = "pending")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @JsonIgnore
    private boolean pending;

    @Column(name = "featured")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean featured;

    @Column(name = "open_issues")
    private int openIssues;

    @Column(name = "pull_requests")
    private int pullRequests;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_commit")
    private Date lastCommit;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_sync")
    @JsonIgnore
    private Date lastSync;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "image_name")
    private String imageName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "extensions_tags",
            joinColumns = @JoinColumn(name = "extension_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    public Extension(){
        this.date = new Date();
        this.numberOfDownloads = 0;
        this.tags = new HashSet<>();
        this.date = new Date();
        this.pending = true;
        this.featured = false;
    }

    public Extension(String name, String description, String version, User user,String fileName, String repositoryLink, Set<Tag> tags) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.user = user;
        this.fileName = fileName;
        this.repositoryLink = repositoryLink;
        this.tags = tags;

        this.pending = true;
        this.featured = false;
        this.date = new Date();
        this.numberOfDownloads = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumberOfDownloads() {
        return numberOfDownloads;
    }

    public void setNumberOfDownloads(int numberOfDownloads) {
        this.numberOfDownloads = numberOfDownloads;
    }

    public String getRepositoryLink() {
        return repositoryLink;
    }

    public void setRepositoryLink(String repositoryLink) {
        this.repositoryLink = repositoryLink;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public int getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(int openIssues) {
        this.openIssues = openIssues;
    }

    public int getPullRequests() {
        return pullRequests;
    }

    public void setPullRequests(int pullRequests) {
        this.pullRequests = pullRequests;
    }

    public Date getLastCommit() {
        return lastCommit;
    }

    public void setLastCommit(Date lastCommit) {
        this.lastCommit = lastCommit;
    }

    public Date getLastSync() {
        return lastSync;
    }

    public void setLastSync(Date lastSync) {
        this.lastSync = lastSync;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}