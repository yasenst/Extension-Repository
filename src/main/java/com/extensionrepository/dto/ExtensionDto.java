package com.extensionrepository.dto;

import com.extensionrepository.validation.IsContentTypeImage;
import com.extensionrepository.validation.IsFieldUnique;
import com.extensionrepository.service.ExtensionServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;


@IsContentTypeImage
public class ExtensionDto {
    @IsFieldUnique(service = ExtensionServiceImpl.class, fieldName = "name", message = "Extension name already exists")
    private String name;

    private String description;

    private String version;

    @NotEmpty
    private String repositoryLink;

    private MultipartFile file;

    private MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    private String tags;

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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return name;
    }
}