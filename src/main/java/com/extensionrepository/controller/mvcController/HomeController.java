package com.extensionrepository.controller.mvcController;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.service.base.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ExtensionService extensionService;

    @GetMapping("/")
    public String index(Model model) {

        List<Extension> featured = this.extensionService.getFeatured();
        model.addAttribute("featured", featured);

        List<Extension> popular = this.extensionService.getPopular();
        model.addAttribute("popular", popular);

        List<Extension> newest = this.extensionService.getNewest();
        model.addAttribute("newest", newest);

        model.addAttribute("view", "home/index");
        return "base-layout";
    }

    @GetMapping("/error/403")
    public String accessDenied(Model model) {
        model.addAttribute("view", "error/403");

        return "base-layout";
    }
}
