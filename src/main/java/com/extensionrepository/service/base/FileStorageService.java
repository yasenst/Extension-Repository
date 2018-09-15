package com.extensionrepository.service.base;

import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.FileSystemException;

public interface FileStorageService {
    public void init();
    public void store(MultipartFile multipartFile, String fileName);
    public Resource loadFile(String filename);
    void delete(String filename);
}
