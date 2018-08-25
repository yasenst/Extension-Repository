package com.extensionrepository.controller.mvcController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class HomeController {
    public String index(Model model) {
        model.addAttribute("view", "home/index");
        return "base-layout";
    }
}
