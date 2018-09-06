package com.extensionrepository.controller.mvc;

import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.User;
import com.extensionrepository.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/my-extensions")
    public String showUserPredictions(Model model) {
        // get currently logged in user
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        // load user
        User user = this.userService.findByUsername(principal.getUsername());

        // get predictions
        List<Extension> extensions = user.getExtensions();

        // add predictions to model
        model.addAttribute("extensions", extensions);

        // return the view
        model.addAttribute("view", "user/my-extensions");
        return "base-layout";
    }
}