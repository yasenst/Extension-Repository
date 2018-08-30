package com.extensionrepository.controller.mvcController;

import com.extensionrepository.dto.ExtensionDto;
import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.User;
import com.extensionrepository.service.base.ExtensionService;
import com.extensionrepository.service.base.FileStorageService;
import com.extensionrepository.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@Controller
public class ExtensionHandleController {

    private ExtensionService extensionService;

    private UserService userService;

    private FileStorageService fileStorageService;

    @Autowired
    public ExtensionHandleController(ExtensionService extensionService, UserService userService, FileStorageService fileStorageService) {
        this.extensionService = extensionService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/extension/browse")
    public String browseExtensions(@RequestParam(value="search", required = false) String search,
                                   @RequestParam(value="criteria", required = false) String criteria,
                                   Model model) {
        List<Extension> extensions = extensionService.filter(search, criteria);

        model.addAttribute("extensions", extensions);
        model.addAttribute("view", "extension/browse");
        return "base-layout";
    }

    /*
    @PostMapping("/extension/browse")
    public String searchByName(@RequestParam(value="search", required = false) String search, Model model) {
        List<Extension> extensions = extensionService.searchByName(search);

        model.addAttribute("extensions", extensions);
        model.addAttribute("view", "extension/browse");
        return "base-layout";
    }
    */

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

        model.addAttribute("extension", extension);
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

        extension.setName(extensionDto.getName());
        extension.setDescription((extensionDto.getDescription()));
        extension.setVersion(extensionDto.getVersion());
        extension.setRepositoryLink(extensionDto.getRepositoryLink());

        if (!extensionDto.getFile().getOriginalFilename().equals("")){
            String downloadLink =  MvcUriComponentsBuilder.fromMethodName(DownloadController.class,
                    "downloadFile", extensionDto.getFile().getOriginalFilename()).build().toString();

            extension.setDownloadLink(downloadLink);
            fileStorageService.store(extensionDto.getFile());
        }

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