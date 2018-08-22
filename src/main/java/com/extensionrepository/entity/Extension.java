package com.extensionrepository.entity;

import javax.persistence.*;

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
    private User owner;

    @Column(name = "numberOfDownloads")
    private int numberOfDownloads;

    @Column(name = "downloadLink")
    private String downloadLink;

    @Column(name = "repositoryLink")
    private String repositoryLink;

    public Extension(){
    }

    public Extension(String name, String description, String version, User owner, int numberOfDownloads, String downloadLink, String repositoryLink) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.owner = owner;
        this.numberOfDownloads = numberOfDownloads;
        this.downloadLink = downloadLink;
        this.repositoryLink = repositoryLink;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getNumberOfDownloads() {
        return numberOfDownloads;
    }

    public void setNumberOfDownloads(int numberOfDownloads) {
        this.numberOfDownloads = numberOfDownloads;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getRepositoryLink() {
        return repositoryLink;
    }

    public void setRepositoryLink(String repositoryLink) {
        this.repositoryLink = repositoryLink;
    }
}
