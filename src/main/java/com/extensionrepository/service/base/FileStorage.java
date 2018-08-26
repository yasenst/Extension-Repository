package com.extensionrepository.service.base;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
    public void init();
    public void store(MultipartFile file);

}
