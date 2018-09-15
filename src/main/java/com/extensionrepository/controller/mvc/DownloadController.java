package com.extensionrepository.controller.mvc;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.service.base.ExtensionService;
import com.extensionrepository.service.base.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DownloadController {

    private FileStorageService fileStorageService;

    private ExtensionService extensionService;

    @Autowired
    public DownloadController(FileStorageService fileStorageService, ExtensionService extensionService) {
        this.fileStorageService = fileStorageService;
        this.extensionService = extensionService;
    }

    /**
     * @param id of extension - get extension, increase download count, get file as resource
     * @return file packed for download
     */
    @GetMapping("/extension/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int id) {
        Extension extension = extensionService.getById(id);
        extensionService.increaseDownloads(id);

        Resource file = fileStorageService.loadFile(extension.getFileName());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/extension/image/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable int id) {
        Extension extension = extensionService.getById(id);

        Resource file = fileStorageService.loadFile(extension.getImageName());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
