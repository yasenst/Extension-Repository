package com.extensionrepository.controller.mvcController;

import com.extensionrepository.entity.User;
import com.extensionrepository.service.base.ExtensionService;
import com.extensionrepository.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminController {

    private UserService userService;

    private ExtensionService extensionService;

    @Autowired
    public AdminController(UserService userService, ExtensionService extensionService) {
        this.userService = userService;
        this.extensionService = extensionService;
    }

    @GetMapping("/admin/accounts")
    public String accounts(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("view", "admin/accounts");
        return "base-layout";
    }

    @GetMapping("/admin/accounts/toggle-status/{id}")
    public String toggleAccountStatus(@PathVariable int id) {
        userService.changeStatus(id);
        return "redirect:/admin/accounts";
    }



}
