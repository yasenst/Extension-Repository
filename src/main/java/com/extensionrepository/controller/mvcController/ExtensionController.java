package com.extensionrepository.controller.mvcController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExtensionController {

    @GetMapping("/extensions")
    public String showExtensions(Model model) {
        return "base-layout";
    }
}
