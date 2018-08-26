package com.extensionrepository.controller.mvcController;

import com.extensionrepository.dto.ExtensionDto;
import com.extensionrepository.entity.Extension;
import com.extensionrepository.service.base.ExtensionService;
import com.extensionrepository.service.base.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ExtensionController {

    private ExtensionService extensionService;


    @Autowired
    public ExtensionController(ExtensionService extensionService, FileStorage fileStorage) {
        this.extensionService = extensionService;

    }

    @GetMapping("/extensions")
    public String showExtensions(Model model) {
        List<Extension> allExtensions = extensionService.getAll();
        model.addAttribute("extensions", allExtensions);
        model.addAttribute("view", "extension/display-extensions");
        return "base-layout";
    }




}
