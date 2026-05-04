package org.example.java_web_dgnl2_004.controller;

import lombok.RequiredArgsConstructor;
import org.example.java_web_dgnl2_004.repository.ComponentCategoryRepository;
import org.example.java_web_dgnl2_004.service.ComponentCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/componentCategories")
@RequiredArgsConstructor
public class ComponentCategoryController {

    private  final ComponentCategoryRepository componentCategoryRepository;
    private final ComponentCategoryService componentCategoryService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("componentCategories", componentCategoryRepository.findAll());
        return "category-list";
    }

    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            int count = componentCategoryService.deleteCategory(id);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Đã xóa loại linh kiện và cập nhật cho " + count + " linh kiện");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Không thể xóa loại linh kiện: " + e.getMessage());
        }

        return "redirect:/componentCategories";
    }
}


