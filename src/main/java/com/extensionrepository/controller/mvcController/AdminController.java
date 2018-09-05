package com.extensionrepository.controller.mvcController;

import com.extensionrepository.dto.ExtensionDto;
import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.Tag;
import com.extensionrepository.entity.User;
import com.extensionrepository.service.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private UserService userService;

    private ExtensionService extensionService;

    private FileStorageService fileStorageService;

    private TagService tagService;

    private GitHubService gitHubService;

    @Autowired
    public AdminController(UserService userService, ExtensionService extensionService, FileStorageService fileStorageService, TagService tagService, GitHubService gitHubService) {
        this.userService = userService;
        this.extensionService = extensionService;
        this.fileStorageService = fileStorageService;
        this.tagService = tagService;
        this.gitHubService = gitHubService;
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
    public String approve(Model model, @PathVariable int id){
        if (!extensionService.exists(id)) {
            return "redirect:/";
        }

        Extension extension = extensionService.getById(id);

        // Stringify tags
        StringBuilder tagString = new StringBuilder();
        for (Tag tag : extension.getTags()) {
            tagString.append(tag.getName() + " ");
        }

        model.addAttribute("extension", extension);
        model.addAttribute("tagString", tagString.toString());
        model.addAttribute("view", "admin/approve");
        return "base-layout";
    }

    @PostMapping("/admin/approve/{id}")
    @PreAuthorize("isAuthenticated()")
    public String approveProcess(@PathVariable int id, @ModelAttribute ExtensionDto extensionDto){

        if (!extensionService.exists(id)) {
            return "redirect:/";
        }

        Extension extension = extensionService.getById(id);

        extension.setName(extensionDto.getName());
        extension.setDescription((extensionDto.getDescription()));
        extension.setVersion(extensionDto.getVersion());
        extension.setRepositoryLink(extensionDto.getRepositoryLink());
        extension.setPending(false);

        Set<Tag> tags = tagService.getTagsFromString(extensionDto.getTags());
        extension.setTags(tags);

        extensionService.update(extension);

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
        return "redirect:/extension/" + id;
    }

    @GetMapping("/admin/sync/{id}")
    public String sync(@PathVariable int id) {
        Extension extension = extensionService.getById(id);

        extension.setPullRequests(gitHubService.fetchPullRequests(extension.getRepositoryLink()));
        extension.setOpenIssues(gitHubService.fetchOpenIssues(extension.getRepositoryLink()));
        extension.setLastCommit(gitHubService.fetchLastCommit(extension.getRepositoryLink()));

        extensionService.update(extension);

        return "redirect:/extension/" + extension.getId();
    }
}
