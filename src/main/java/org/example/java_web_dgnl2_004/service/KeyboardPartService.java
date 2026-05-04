package org.example.java_web_dgnl2_004.service;

import lombok.RequiredArgsConstructor;
import org.example.java_web_dgnl2_004.model.KeyboardPart;
import org.example.java_web_dgnl2_004.repository.KeyboardPartRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KeyboardPartService {

    private final KeyboardPartRepository keyboardPartRepository;

    public Page<KeyboardPart> search(
            String keyword,
            Long categoryId,
            int page
    ) {

        Pageable pageable = PageRequest.of(page, 5);

        String kw = (keyword == null || keyword.trim().isEmpty()) ? null : keyword.trim();

        return keyboardPartRepository.searchKeyboardPart(kw, categoryId, pageable);
    }
}
