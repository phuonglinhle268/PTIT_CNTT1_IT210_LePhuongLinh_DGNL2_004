package org.example.java_web_dgnl2_004.controller;

import lombok.RequiredArgsConstructor;
import org.example.java_web_dgnl2_004.model.ComponentCategory;
import org.example.java_web_dgnl2_004.model.KeyboardPart;
import org.example.java_web_dgnl2_004.repository.ComponentCategoryRepository;
import org.example.java_web_dgnl2_004.repository.KeyboardPartRepository;
import org.example.java_web_dgnl2_004.service.KeyboardPartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Controller
@RequestMapping("/keyboardParts")
@RequiredArgsConstructor
public class KeyboardPartController {

    private final ComponentCategoryRepository componentCategoryRepository;
    private final KeyboardPartRepository keyboardPartRepository;
    private final KeyboardPartService keyboardPartService;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        if (page < 0) page = 0;

        Page<KeyboardPart> keyboardPartPage = keyboardPartService.search(
                keyword, categoryId, page);

        if (page >= keyboardPartPage.getTotalPages() && keyboardPartPage.getTotalPages() > 0) {
            keyboardPartPage = keyboardPartService.search(
                    keyword, categoryId, keyboardPartPage.getTotalPages() - 1);
        }

        model.addAttribute("keyboards", keyboardPartPage.getContent());
        model.addAttribute("keyboardPartPage", keyboardPartPage);
        model.addAttribute("currentPage", keyboardPartPage.getNumber());
        model.addAttribute("totalPages", keyboardPartPage.getTotalPages());
        model.addAttribute("totalItems", keyboardPartPage.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", componentCategoryRepository.findAll());

        return "keyboard-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("keyboard", new KeyboardPart());
        model.addAttribute("categories", componentCategoryRepository.findAll());
        return "keyboard-create";
    }

    @PostMapping("/save")
    public String save(
            @ModelAttribute KeyboardPart keyboardPart,
            @RequestParam("avatarFile") MultipartFile avatarFile,
            @RequestParam(value = "categoryId", required = false) Long categoryId
    ) throws IOException {

        if (categoryId != null) {
            ComponentCategory componentCategory = componentCategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("không tồn tại"));
            keyboardPart.setComponentCategory(componentCategory);
        }

        if (avatarFile != null && !avatarFile.isEmpty()) {
            String originalFilename = avatarFile.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFilename = UUID.randomUUID().toString() + extension;

            Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
            Files.createDirectories(uploadPath);
            avatarFile.transferTo(uploadPath.resolve(newFilename));
            keyboardPart.setPartImage(newFilename);
        }

        keyboardPartRepository.save(keyboardPart);
        return "redirect:/keyboardParts";
    }
}







