package com.extensionrepository.controller.restController;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.service.base.ExtensionService;
import com.extensionrepository.service.base.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class DownloadRestController {

    private FileStorageService fileStorageService;

    private ExtensionService extensionService;

    @Autowired
    public DownloadRestController(FileStorageService fileStorageService, ExtensionService extensionService) {
        this.fileStorageService = fileStorageService;
        this.extensionService = extensionService;
    }

    @GetMapping("/api/extension/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int id, HttpServletRequest request) {
        // Load file as Resource
        Extension extension = extensionService.getById(id);
        extensionService.increaseDownloads(id);

        Resource resource = fileStorageService.loadFile(extension.getFileName());

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
