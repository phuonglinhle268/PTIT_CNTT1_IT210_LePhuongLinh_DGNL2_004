package org.example.java_web_dgnl2_004.config;

import jakarta.persistence.*;
import lombok.*;
import org.example.java_web_dgnl2_004.model.ComponentCategory;
import org.example.java_web_dgnl2_004.model.KeyboardPart;
import org.example.java_web_dgnl2_004.repository.ComponentCategoryRepository;
import org.example.java_web_dgnl2_004.repository.KeyboardPartRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final ComponentCategoryRepository componentCategoryRepository;
    private final KeyboardPartRepository keyboardPartRepository;

    @Override
    public void run(String... args) throws Exception {

        if (componentCategoryRepository.count() > 0 && keyboardPartRepository.count() > 0) {
            System.out.println("Data đã tồn tại");
            return;
        }

        ComponentCategory linearSwitch;
        ComponentCategory tactileSwitch;
        ComponentCategory aluminumSwitch;

        if (componentCategoryRepository.count() == 0) {
            linearSwitch = new ComponentCategory();
            linearSwitch.setName("Linear");
            linearSwitch.setDescription("Loại linh kiện phù hợp với các loại máy");
            linearSwitch = componentCategoryRepository.save(linearSwitch);

            tactileSwitch = new ComponentCategory();
            tactileSwitch.setName("Tactile");
            tactileSwitch.setDescription("Linh kiện mới nhất");
            tactileSwitch = componentCategoryRepository.save(tactileSwitch);

            aluminumSwitch = new ComponentCategory();
            aluminumSwitch.setName("Aluminum");
            aluminumSwitch.setDescription("Linh kiện chính hãng");
            aluminumSwitch = componentCategoryRepository.save(aluminumSwitch);
        } else {
            linearSwitch  = componentCategoryRepository.findAll().get(0);
            tactileSwitch = componentCategoryRepository.findAll().get(1);
            aluminumSwitch = componentCategoryRepository.findAll().get(2);
        }

        if (keyboardPartRepository.count() == 0) {
            KeyboardPart k1 = new KeyboardPart();
            k1.setPartName("MMD Holy Panda V3");
            k1.setManufacturer("Apple");
            k1.setPrice(1000000.0);
            k1.setReleaseDate(LocalDate.of(2023, 12, 12));
            k1.setPartImage("");
            k1.setComponentCategory(linearSwitch);
            k1.setInStock(false);

            KeyboardPart k2 = new KeyboardPart();
            k2.setPartName("Neo Ergo Anodized Green");
            k2.setManufacturer("Samsung");
            k2.setPrice(500000.0);
            k2.setReleaseDate(LocalDate.of(2012, 10, 11));
            k2.setPartImage("");
            k2.setComponentCategory(tactileSwitch);
            k2.setInStock(false);

            KeyboardPart k3 = new KeyboardPart();
            k3.setPartName("ABCDEF");
            k3.setManufacturer("Celmi");
            k3.setPrice(13000000.0);
            k3.setReleaseDate(LocalDate.of(2020, 9, 8));
            k3.setPartImage("");
            k3.setComponentCategory(aluminumSwitch);
            k3.setInStock(true);

            KeyboardPart k4 = new KeyboardPart();
            k4.setPartName("Admin");
            k4.setManufacturer("Celmi");
            k4.setPrice(3000000.0);
            k4.setReleaseDate(LocalDate.of(2019, 8, 10));
            k4.setPartImage("");
            k4.setComponentCategory(aluminumSwitch);
            k4.setInStock(true);

            keyboardPartRepository.saveAll(List.of(k1, k2, k3, k4));
        }
    }
}











