package com.kode.bookaccountingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO для выдачи авторов
 */
public class AuthorResponse {
    @Schema(description = "Идентификатор", example = "1")
    private Long id;
    @Schema(description = "Имя автора", example = "Пушкин")
    private String name;
    @Schema(description = "Год рождения", example = "1799")
    private int birthYear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
