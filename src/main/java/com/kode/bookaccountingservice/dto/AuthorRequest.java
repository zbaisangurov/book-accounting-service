package com.kode.bookaccountingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO для добавления авторов
 */
@Schema(description = "Объект запроса для создания автора")
public class AuthorRequest {
    @Schema(description = "Имя автора", example = "Пушкин")
    private String name;
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
