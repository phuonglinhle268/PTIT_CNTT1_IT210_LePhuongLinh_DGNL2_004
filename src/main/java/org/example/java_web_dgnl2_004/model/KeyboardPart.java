package org.example.java_web_dgnl2_004.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "keyboard_parts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KeyboardPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "partName")
    @NotBlank(message = "PartName không được để trống")
    @Size(min = 5, max = 150, message = "Chỉ từ 5-150 kí tự")
    private String partName;

    @Column(name = "manufacturer")
    @NotBlank(message = "Không được để trống")
    private String manufacturer;

    @Column(name = "price")
    private Double price;

    @Column(name = "releaseDate")
    @PastOrPresent(message = "Không được là ngày trong tương lai")
    private LocalDate releaseDate;

    @Column(name = "partImage")
    private String partImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "componentCategory_id")
    private ComponentCategory componentCategory;

    @Column(name = "inStock")
    private boolean inStock;
}
