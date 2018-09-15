package com.extensionrepository.controller.mvc;

import com.extensionrepository.dto.ExtensionDto;
import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.Tag;
import com.extensionrepository.entity.User;
import com.extensionrepository.service.base.*;
import com.extensionrepository.util.Constants;
import org.kohsuke.github.GHFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Controller
public class UploadController {

    private ExtensionService extensionService;

    private UserService userService;

    private FileStorageService fileStorageService;

    private TagService tagService;

    private GitHubService gitHubService;

    @Autowired
    public UploadController(ExtensionService extensionService, UserService userService, FileStorageService fileStorageService, TagService tagService, GitHubService gitHubService) {
        this.extensionService = extensionService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.tagService = tagService;
        this.gitHubService = gitHubService;
    }

    @GetMapping("/upload")
    public String upload(Model model, @ModelAttribute ExtensionDto extensionDto) {
        model.addAttribute("view", "extension/upload-form");
        return "base-layout";
    }

    @PostMapping("/upload")
    public String uploadProcess(@Valid @ModelAttribute ExtensionDto extensionDto, BindingResult bindingResult, Model model,  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("view", "extension/upload-form");
            return upload(model, extensionDto);
        }

        try {
            // get currently logged user
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();

            // extract user from database
            User user = userService.findByUsername(principal.getUsername());

            // Collect tags
            Set<Tag> tags = tagService.getTagsFromString(extensionDto.getTags());

            // Create extension and fill by dto
            Extension extension = new Extension();

            extension.setName(extensionDto.getName());
            extension.setDescription(extensionDto.getDescription());
            extension.setVersion(extensionDto.getVersion());
            extension.setUser(user);
            extension.setRepositoryLink(extensionDto.getRepositoryLink());
            extension.setTags(tags);

            // fetch github info
            extension.setPullRequests(gitHubService.fetchPullRequests(extension.getRepositoryLink()));
            extension.setOpenIssues(gitHubService.fetchOpenIssues(extension.getRepositoryLink()));
            extension.setLastCommit(gitHubService.fetchLastCommit(extension.getRepositoryLink()));
            extension.setLastSync(new Date());

            // Generate unique filename
            UUID uniquePrefix = UUID.randomUUID();
            String fileName = uniquePrefix.toString() + extensionDto.getFile().getOriginalFilename();
            extension.setFileName(fileName);
            fileStorageService.store(extensionDto.getFile(), fileName);

            // image
            if (extensionDto.getImage().getOriginalFilename().equals("")) {
                extension.setImageName(Constants.DEFAULT_IMAGE);
            } else {
                extension.setImageName(extensionDto.getImage().getOriginalFilename());
                fileStorageService.store(extensionDto.getImage(), extensionDto.getImage().getOriginalFilename());
            }


            extensionService.save(extension);

            redirectAttributes.addFlashAttribute("successMessage", "Extension uploaded successfully!");
        } catch (GHFileNotFoundException gh) {
            model.addAttribute("failMessage", gh.getMessage());
            return upload(model, extensionDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("failMessage", "Faulty github repository");
            return upload(model, extensionDto);
        }

        model.addAttribute("view", "extension/upload-form");
        return "redirect:/upload";
    }

}
