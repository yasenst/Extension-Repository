package com.extensionrepository.controller.mvcController;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.service.base.ExtensionService;
import com.extensionrepository.service.base.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ExtensionController {

    /*
    private ExtensionService extensionService;


    @Autowired
    public ExtensionController(ExtensionService extensionService, FileStorageService fileStorageService) {
        this.extensionService = extensionService;

    }

    @GetMapping("/extensions")
    public String showExtensions(Model model) {
        List<Extension> allExtensions = extensionService.getAll();
        model.addAttribute("extensions", allExtensions);
        model.addAttribute("view", "extension/display-extensions");
        return "base-layout";
    }

    */


}
