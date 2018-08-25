package com.extensionrepository.controller.mvcController;

import com.extensionrepository.dto.UserDto;
import com.extensionrepository.entity.Role;
import com.extensionrepository.entity.User;
import com.extensionrepository.service.base.RoleService;
import com.extensionrepository.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public RegisterController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("view", "user/register");

        return "base-layout";
    }

    @PostMapping("/register")
    public String registerProcess(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult, Model model) {
        // check if password matches
        if (bindingResult.hasErrors()) {

            model.addAttribute("view", "user/register");

            return "base-layout";
        }


        // encode password
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        // create user instance
        User user = new User(
                userDto.getUsername(),
                bCryptPasswordEncoder.encode(userDto.getPassword()),
                userDto.getFullname()
        );

        // assign role
        Role userRole = this.roleService.findByName("ROLE_USER");
        user.addRole(userRole);

        userService.registerUser(user);

        return "redirect:/login";
    }
}
