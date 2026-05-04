package org.example.java_web_dgnl2_004.service;

import lombok.RequiredArgsConstructor;
import org.example.java_web_dgnl2_004.model.ComponentCategory;
import org.example.java_web_dgnl2_004.model.KeyboardPart;
import org.example.java_web_dgnl2_004.repository.ComponentCategoryRepository;
import org.example.java_web_dgnl2_004.repository.KeyboardPartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ComponentCategoryService {

    private final ComponentCategoryRepository componentCategoryRepository;
    private final KeyboardPartRepository keyboardPartRepository;

    @Transactional
    public int deleteCategory(Long categoryId) {
        ComponentCategory componentCategory = componentCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Loại linh kiện không tồn tại: " + categoryId));

        List<KeyboardPart> keyboardPart = keyboardPartRepository.findByComponentCategory_Id(categoryId);
        int affectedCount = keyboardPart.size();

        for (KeyboardPart kb : keyboardPart) {
            kb.setComponentCategory(null);
        }
        keyboardPartRepository.saveAll(keyboardPart);

        componentCategory.getKeyboardParts().clear();
        componentCategoryRepository.delete(componentCategory);

        return affectedCount;
    }
}





