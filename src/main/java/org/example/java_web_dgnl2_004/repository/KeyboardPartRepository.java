package org.example.java_web_dgnl2_004.repository;

import org.example.java_web_dgnl2_004.model.KeyboardPart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KeyboardPartRepository extends JpaRepository<KeyboardPart, Long> {

    Page<KeyboardPart> findByPartNameContainingIgnoreCase(String partName, Pageable pageable);

    List<KeyboardPart> findByComponentCategory_Id(Long categoryId);


    @Query("""
        SELECT k FROM KeyboardPart k
        WHERE
            (:keyword IS NULL OR LOWER(k.partName) LIKE LOWER(CONCAT('%', :keyword, '%')))
            AND (:categoryId IS NULL OR k.componentCategory.id = :categoryId)
        """)
    Page<KeyboardPart> searchKeyboardPart(
            @Param("keyword")      String keyword,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );
}
