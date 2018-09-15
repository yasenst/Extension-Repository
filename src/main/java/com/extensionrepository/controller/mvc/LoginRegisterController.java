package com.extensionrepository.controller.mvc;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class LoginRegisterController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public LoginRegisterController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("view", "login");
        return "base-layout";
    }

    @GetMapping("/register")
    public String register(Model model, @ModelAttribute UserDto userDto) {
        model.addAttribute("view", "register");

        return "base-layout";
    }

    @PostMapping("/register")
    public String registerProcess(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // input validations, if error - return register with current input and error fields
        if (bindingResult.hasErrors()) {

            model.addAttribute("view", "register");

            return register(model,userDto);
        }


        // encode password
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        // create user instance
        User user = new User(
                userDto.getUsername(),
                bCryptPasswordEncoder.encode(userDto.getPassword()),
                userDto.getFullName()
        );

        // assign role
        Role userRole = this.roleService.findByName("ROLE_USER");
        user.addRole(userRole);

        userService.register(user);

        redirectAttributes.addFlashAttribute("registrationMessage", "Registration successful! You may login now.");
        return "redirect:/login";
    }
}