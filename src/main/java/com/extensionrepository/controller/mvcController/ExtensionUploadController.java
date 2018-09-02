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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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

            Set<Tag> tags = tagService.getTagsFromString(extensionDto.getTags());

            Extension extension = new Extension(
                    extensionDto.getName(),
                    extensionDto.getDescription(),
                    extensionDto.getVersion(),
                    user,
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
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
            return upload(model, extensionDto);
        }

        model.addAttribute("view", "extension/upload-form");
        return "redirect:/upload";
    }

}
