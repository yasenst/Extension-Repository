package com.extensionrepository.controller.mvcController;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.service.base.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ExtensionController {

    private ExtensionService extensionService;

    @Autowired
    public ExtensionController(ExtensionService extensionService) {
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
