package com.extensionrepository.controller.mvc;

import com.extensionrepository.dto.ExtensionDto;
import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.Tag;
import com.extensionrepository.entity.User;
import com.extensionrepository.service.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
public class ExtensionController {

    private ExtensionService extensionService;

    private UserService userService;

    private FileStorageService fileStorageService;

    private TagService tagService;

    private GitHubService gitHubService;

    @Autowired
    public ExtensionController(ExtensionService extensionService, UserService userService, FileStorageService fileStorageService, TagService tagService, GitHubService gitHubService) {
        this.extensionService = extensionService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.tagService = tagService;
        this.gitHubService = gitHubService;
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
    public String extensionDetail(Model model, @PathVariable int id, RedirectAttributes redirectAttributes){
        /*
        if (!extensionService.exists(id)) {
            return "redirect:/error/not-found";
        }
        */
        Extension extension = extensionService.getById(id);

        if (extension.isPending()) {
            if (!isUserOwnerOrAdmin(extension)) {
                return "redirect:/error/403";
            }
        }

        model.addAttribute("extension", extension);
        model.addAttribute("view", "extension/details");
        return "base-layout";
    }

    @GetMapping("/extension/update/{id}")
    public String update(Model model, @PathVariable int id, @ModelAttribute ExtensionDto extensionDto){
        if (!extensionService.exists(id)) {
            return "redirect:/error/not-found";
        }

        Extension extension = extensionService.getById(id);

        if (!isUserOwnerOrAdmin(extension)) {
            return "redirect:/error/403";
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
    public String updateProcess(@PathVariable int id,@Valid @ModelAttribute ExtensionDto extensionDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        if (!extensionService.exists(id)) {
            return "redirect:/error/not-found";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("view", "extension/update");
            return update(model, id, extensionDto);
        }

        Extension extension = extensionService.getById(id);

        if (!isUserOwnerOrAdmin(extension)) {
            return "redirect:/error/403";
        }

        if (!extensionDto.getName().equals("")) {
            extension.setName(extensionDto.getName());
        }
        extension.setDescription((extensionDto.getDescription()));
        extension.setVersion(extensionDto.getVersion());
        extension.setRepositoryLink(extensionDto.getRepositoryLink());

        Set<Tag> tags = tagService.getTagsFromString(extensionDto.getTags());
        extension.setTags(tags);

        // check for new file, delete old
        if (!extensionDto.getFile().getOriginalFilename().equals("")){
            String previousFile = extension.getFileName();
            UUID uniquePrefix = UUID.randomUUID();
            String fileName = uniquePrefix.toString() + extensionDto.getFile().getOriginalFilename();
            extension.setFileName(fileName);
            fileStorageService.store(extensionDto.getFile(), fileName);
            fileStorageService.delete(previousFile);
        }

        // check for image
        if (!extensionDto.getImage().getOriginalFilename().equals("")) {
            extension.setImageName(extensionDto.getImage().getOriginalFilename());
            fileStorageService.store(extensionDto.getImage(), extensionDto.getImage().getOriginalFilename());
        }

        extensionService.update(extension);

        redirectAttributes.addFlashAttribute("updateMessage", "Extension updated successfully!");
        return "redirect:/extension/" + extension.getId();
    }

    @GetMapping("/extension/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteExtension(Model model, @PathVariable int id){
        if (!extensionService.exists(id)) {
            return "redirect:/error/not-found";
        }

        Extension extension = extensionService.getById(id);

        if (!isUserOwnerOrAdmin(extension)) {
            return "redirect:/error/403";
        }

        model.addAttribute("extension", extension);
        model.addAttribute("view", "extension/delete");
        return "base-layout";
    }

    @PostMapping("/extension/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable int id){
        if (!extensionService.exists(id)) {
            return "redirect:/error/not-found";
        }

        Extension extension = extensionService.getById(id);

        if (!isUserOwnerOrAdmin(extension)) {
            return "redirect:/error/403";
        }

        fileStorageService.delete(extension.getFileName());
        extensionService.delete(extension);

        return "redirect:/user/my-extensions";
    }

    private boolean isUserOwnerOrAdmin(Extension extension) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();

        User user = userService.findByUsername(principal.getUsername());

        return user.isAdmin() || user.isOwner(extension);
    }
}