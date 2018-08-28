package com.extensionrepository.controller.mvcController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/accounts")
    public String accounts() {
        
        return "base-layout";
    }
}
