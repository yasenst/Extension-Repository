package com.extensionrepository.controller.mvcController;

import com.extensionrepository.dto.ExtensionDto;
import com.extensionrepository.entity.Extension;
import com.extensionrepository.entity.User;
import com.extensionrepository.service.base.ExtensionService;
import com.extensionrepository.service.base.FileStorage;
import com.extensionrepository.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadExtensionController {

    private ExtensionService extensionService;

    private UserService userService;

    private FileStorage fileStorage;

    @Autowired
    public UploadExtensionController(ExtensionService extensionService, UserService userService, FileStorage fileStorage) {
        this.extensionService = extensionService;
        this.userService = userService;
        this.fileStorage = fileStorage;
    }

    @GetMapping("/upload")
    public String showForm() {
        return "extension/upload-form";
    }

    @PostMapping("/upload")
    public String uploadMultipartFile(@ModelAttribute ExtensionDto extensionDto, Model model) {
        try {
            // get currently logged user
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();

            // extract user from database
            User user = this.userService.findByUsername(principal.getUsername());

            Extension extension = new Extension(
                    extensionDto.getName(),
                    extensionDto.getDescription(),
                    extensionDto.getVersion(),
                    user,
                    extensionDto.getFile().getName(),
                    extensionDto.getRepositoryLink()
            );



            fileStorage.store(extensionDto.getFile());
            model.addAttribute("message", "File uploaded successfully! -> filename = " + extensionDto.getFile().getOriginalFilename());
        } catch (Exception e) {
            model.addAttribute("message", "Fail! -> uploaded filename: " + extensionDto.getFile().getOriginalFilename());
        }
        return "extension/upload-form";
    }
}
