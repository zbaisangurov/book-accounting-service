package com.kode.bookaccountingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * DTO для добавления авторов
 */
@Schema(description = "Объект запроса для создания автора")
public class AuthorRequest {
    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 3, max = 100, message = "Имя не должно быть меньше 3 больше 100 символов")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s?!,.'Ёё]+$", message = "Имя содержит недопустимые символы")
    @Schema(description = "Имя автора", example = "Пушкин")
    private String name;

    @Min(value = 0, message = "Год рождения не должен быть отрицательным")
    @Max(value = 2025, message = "Год рождения не должен быть больше текущего") // TODO: поменять на динамический год
    @Digits(integer = 4, fraction = 0, message = "Можно использовать только цифры")
    @Schema(description = "Год рождения автора (опционально)", example = "1799", nullable = true)
    private Integer birthYear;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
}
