package com.extensionrepository.controller.mvcController;

import com.extensionrepository.entity.User;
import com.extensionrepository.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/accounts")
    public String accounts(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("view", "admin/accounts");
        return "base-layout";
    }
}
