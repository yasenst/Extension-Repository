package com.extensionrepository.controller.mvcController;

import com.extensionrepository.dto.ExtensionDto;
import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.User;
import com.extensionrepository.service.base.ExtensionService;
import com.extensionrepository.service.base.FileStorageService;
import com.extensionrepository.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@Controller
public class AdminController {

    private UserService userService;

    private ExtensionService extensionService;

    private FileStorageService fileStorageService;

    @Autowired
    public AdminController(UserService userService, ExtensionService extensionService, FileStorageService fileStorageService) {
        this.userService = userService;
        this.extensionService = extensionService;
        this.fileStorageService = fileStorageService;
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

    @GetMapping("/admin/pending")
    public String listPending(Model model){
        List<Extension> extensions = extensionService.getPending();
        model.addAttribute("extensions", extensions);
        model.addAttribute("view", "admin/pending");

        return "base-layout";
    }

    @GetMapping("/admin/approve/{id}")
    public String updateExtension(Model model, @PathVariable int id){
        if (!extensionService.exists(id)) {
            return "redirect:/";
        }

        Extension extension = extensionService.getById(id);

        model.addAttribute("extension", extension);
        model.addAttribute("view", "admin/approve");
        return "base-layout";
    }

    @PostMapping("/admin/approve/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updateProcess(@PathVariable int id, @ModelAttribute ExtensionDto extensionDto){
        if (!extensionService.exists(id)) {
            return "redirect:/";
        }

        Extension extension = extensionService.getById(id);

        extension.setName(extensionDto.getName());
        extension.setDescription((extensionDto.getDescription()));
        extension.setVersion(extensionDto.getVersion());
        extension.setRepositoryLink(extensionDto.getRepositoryLink());
        extension.setPending(false);


        if (!extensionDto.getFile().getOriginalFilename().equals("")){
            String downloadLink =  MvcUriComponentsBuilder.fromMethodName(DownloadController.class,
                    "downloadFile", extensionDto.getFile().getOriginalFilename()).build().toString();

            extension.setDownloadLink(downloadLink);
            fileStorageService.store(extensionDto.getFile());
        }

        extensionService.update(extension);

        System.out.println("FORM SUBMITTED");
        return "redirect:/extension/" + extension.getId();
    }

    @GetMapping("/admin/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable int id){
        if (!extensionService.exists(id)) {
            return "redirect:/";
        }

        Extension extension = extensionService.getById(id);


        fileStorageService.delete(extension.getFileName());
        extensionService.delete(extension);

        return "redirect:/";
    }

    @GetMapping("/admin/featured")
    public String featured(Model model) {
        List<Extension> extensions = extensionService.getFeatured();
        model.addAttribute("extensions", extensions);
        model.addAttribute("view", "admin/featured");
        return "base-layout";
    }

    @GetMapping("/admin/featured/toggle-status/{id}")
    public String toggleFeaturedStatus(@PathVariable int id) {
        extensionService.changeStatus(id);
        return "redirect:/admin/featured";
    }
}
