package com.sertifikasi.learn.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Kolom username tidak boleh kosong")
    @Size(max = 255, message = "Format username tidak sesuai. (Tidak menggunakan special character dan maksimal 255 charackter)")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Format username belum sesuai.")
    private String username;

    @NotBlank(message = "Kolom nama lengkap tidak boleh kosong")
    @Size(max = 255, message = "Format nama lengkap belum sesuai. (Tidak menggunakan special character dan maksimal 255 charackter)")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Format nama lengkap belum sesuai. (Tidak menggunakan special character dan maksimal 255 charackter)")
    private String fullname;

    @NotBlank(message = "Kolom kata sandi tidak boleh kosong")
    @Size(max = 50, min = 6, message = "Kata sandi tidak boleh kurang dari 6 karakter")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", message = "Kata sandi harus memiliki minimal 6 karakter kombinasi angka dan huruf.")
    private String password;

    @NotBlank(message = "Kolom konfirmasi kata sandi tidak boleh kosong")
    @Size(max = 50, min = 6, message = "Kata sandi tidak boleh kurang dari 6 karakter")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", message = "Kata sandi harus memiliki minimal 6 karakter kombinasi angka dan huruf.")
    private String retypePassword;
}
