package org.example.java_web_dgnl2_004.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "component_categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComponentCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Tên loại linh kiện không được để trống")
    @Size(min = 5, max = 150, message = "Tên từ 5-150 kí tự")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "componentCategory", cascade = CascadeType.ALL)
    private List<KeyboardPart> keyboardParts;
}
