package com.sertifikasi.learn.dto.request.recipes;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import com.sertifikasi.learn.dto.data.CategoryDTO;
import com.sertifikasi.learn.dto.data.LevelDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRecipeRequest {
    @NotNull
    private CategoryDTO categories;

    @NotNull
    private LevelDTO levels;

    private int userId;

    @NotBlank(message = "Kolom recipeName tidak boleh kosong")
    @Size(min = 1, max = 255, message = "Panjang kolom tidak boleh melebihi 255 karakter")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Kolom tidak boleh berisi special character/angka")
    private String recipeName;

    private String imageFilename;

    @NotNull(message = "Kolom timeCook tidak boleh kosong")
    @Min(value = 1, message = "Kolom hanya boleh berisi angka 1-999")
    @Max(value = 999, message = "Kolom hanya boleh berisi angka 1-999")
    private Integer timeCook;

    @NotBlank(message = "Kolom ingridient tidak boleh kosong")
    @Size(min = 1)
    private String ingredient;

    @NotBlank(message = "Kolom howToCook tidak boleh kosong")
    @Size(min = 1)
    private String howToCook;

    @Builder.Default
    private Boolean isDeleted = false;

    private String createdBy;

    @Builder.Default
    private Timestamp createdTime = new Timestamp(System.currentTimeMillis());

    private String modifiedBy;

    @Builder.Default
    private Timestamp modifiedTime = new Timestamp(System.currentTimeMillis());
}
