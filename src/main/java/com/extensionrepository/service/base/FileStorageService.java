package com.extensionrepository.service.base;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileStorageService {
    public void init();
    public void store(MultipartFile multipartFile, String fileName);
    public Resource loadFile(String filename);
    Resource loadAsResource(String filename);
    void delete(String filename);
    String getImageLocation();
}
