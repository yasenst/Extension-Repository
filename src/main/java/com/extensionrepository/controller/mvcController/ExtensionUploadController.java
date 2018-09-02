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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
public class ExtensionUploadController {

    private ExtensionService extensionService;

    private UserService userService;

    private FileStorageService fileStorageService;

    private TagService tagService;

    @Autowired
    public ExtensionUploadController(ExtensionService extensionService, UserService userService, FileStorageService fileStorageService, TagService tagService) {
        this.extensionService = extensionService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.tagService = tagService;
    }

    @GetMapping("/upload")
    public String showForm(Model model) {
        model.addAttribute("view", "extension/upload-form");

        return "base-layout";
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute ExtensionDto extensionDto, Model model, RedirectAttributes redirectAttributes) {
        try {
            // get currently logged user
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();

            // extract user from database
            User user = userService.findByUsername(principal.getUsername());

            // MvcUriComponentsBuilder prepares the URL based on the method which is going to actually serve the file for download
            String downloadLink =  MvcUriComponentsBuilder.fromMethodName(DownloadController.class,
                    "downloadFile", extensionDto.getFile().getOriginalFilename()).build().toString();

            Set<Tag> tags = tagService.getTagsFromString(extensionDto.getTags());

            Extension extension = new Extension(
                    extensionDto.getName(),
                    extensionDto.getDescription(),
                    extensionDto.getVersion(),
                    user,
                    downloadLink,
                    extensionDto.getFile().getOriginalFilename(),
                    extensionDto.getRepositoryLink(),
                    tags
            );

            fileStorageService.store(extensionDto.getFile());

            extension = GitHubService.fetchGithubInfo(extension);

            extensionService.save(extension);

            redirectAttributes.addFlashAttribute("successMessage", "Extension uploaded successfully!");
            //model.addAttribute("message", "File uploaded successfully! -> filename = " + extensionDto.getFile().getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            //model.addAttribute("message", "Fail! -> uploaded filename: " + extensionDto.getFile().getOriginalFilename());
        }

        model.addAttribute("view", "extension/upload-form");
        return "redirect:/upload";
    }

    // download

}
