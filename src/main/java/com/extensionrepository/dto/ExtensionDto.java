package com.extensionrepository.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class ExtensionDto {
    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String version;

    @NotNull
    private String repositoryLink;

    @NotNull
    private MultipartFile file;

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

    public String getRepositoryLink() {
        return repositoryLink;
    }

    public void setRepositoryLink(String repositoryLink) {
        this.repositoryLink = repositoryLink;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
