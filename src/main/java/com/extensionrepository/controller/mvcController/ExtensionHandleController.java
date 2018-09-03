package com.extensionrepository.controller.mvcController;

import com.extensionrepository.dto.ExtensionDto;
import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.Tag;
import com.extensionrepository.entity.User;
import com.extensionrepository.service.GitHubService;
import com.extensionrepository.service.base.ExtensionService;
import com.extensionrepository.service.base.FileStorageService;
import com.extensionrepository.service.base.TagService;
import com.extensionrepository.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.Set;

@Controller
public class ExtensionHandleController {

    private ExtensionService extensionService;

    private UserService userService;

    private FileStorageService fileStorageService;

    private TagService tagService;

    @Autowired
    public ExtensionHandleController(ExtensionService extensionService, UserService userService, FileStorageService fileStorageService, TagService tagService) {
        this.extensionService = extensionService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.tagService = tagService;
    }

    @GetMapping("/extension/browse")
    public String browseExtensions(@RequestParam(value="name", required = false) String name,
                                   @RequestParam(value="sortBy", required = false) String sortBy,
                                   Model model) {
        List<Extension> extensions = extensionService.filter(name, sortBy);

        model.addAttribute("extensions", extensions);
        model.addAttribute("view", "extension/browse");
        return "base-layout";
    }

    @GetMapping("/extension/{id}")
    public String extensionDetail(Model model, @PathVariable int id){

        Extension extension = extensionService.getById(id);
        model.addAttribute("extension", extension);
        model.addAttribute("view", "extension/details");
        return "base-layout";
    }

    @GetMapping("/extension/update/{id}")
    public String updateExtension(Model model, @PathVariable int id){
        if (!extensionService.exists(id)) {
            return "redirect:/";
        }

        Extension extension = extensionService.getById(id);

        if (!isUserOwnerOrAdmin(extension)) {
            return "redirect:/extension/" + id;
        }

        // Stringify tags
        StringBuilder tagString = new StringBuilder();
        for (Tag tag : extension.getTags()) {
            tagString.append(tag.getName() + " ");
        }


        model.addAttribute("extension", extension);
        model.addAttribute("tagString", tagString.toString());
        model.addAttribute("view", "extension/update");
        return "base-layout";
    }

    @PostMapping("/extension/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updateProcess(@PathVariable int id, @ModelAttribute ExtensionDto extensionDto){
        if (!extensionService.exists(id)) {
            return "redirect:/";
        }

        Extension extension = extensionService.getById(id);

        if (!isUserOwnerOrAdmin(extension)) {
            return "redirect:/extension/" + id;
        }

        Set<Tag> tags = tagService.getTagsFromString(extensionDto.getTags());

        extension.setName(extensionDto.getName());
        extension.setDescription((extensionDto.getDescription()));
        extension.setVersion(extensionDto.getVersion());
        extension.setRepositoryLink(extensionDto.getRepositoryLink());
        extension.setTags(tags);

        if (!extensionDto.getFile().getOriginalFilename().equals("")){
            fileStorageService.store(extensionDto.getFile());
        }

        extension = GitHubService.fetchGithubInfo(extension);
        extensionService.update(extension);

        return "redirect:/extension/" + extension.getId();
    }

    @GetMapping("/extension/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteExtension(Model model, @PathVariable int id){
        if (!extensionService.exists(id)) {
            return "redirect:/";
        }

        Extension extension = extensionService.getById(id);

        if (!isUserOwnerOrAdmin(extension)) {
            return "redirect:/extension/" + id;
        }

        model.addAttribute("extension", extension);
        model.addAttribute("view", "extension/delete");
        return "base-layout";
    }

    @PostMapping("/extension/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable int id){
        if (!extensionService.exists(id)) {
            return "redirect:/";
        }

        Extension extension = extensionService.getById(id);

        if (!isUserOwnerOrAdmin(extension)) {
            return "redirect:/extension/" + id;
        }

        fileStorageService.delete(extension.getFileName());
        extensionService.delete(extension);

        return "redirect:/";
    }

    private boolean isUserOwnerOrAdmin(Extension extension) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        User user = userService.findByUsername(principal.getUsername());

        return user.isAdmin() || user.isOwner(extension);
    }
}