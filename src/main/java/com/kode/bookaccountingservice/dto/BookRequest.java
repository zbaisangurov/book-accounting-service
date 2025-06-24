package com.kode.bookaccountingservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * DTO для добавления и обновления книг
 */
public class BookRequest {

    @NotBlank(message = "Поле не должно быть пустым")
    @Size(max = 255, message = "Наименование не должно превышать 255 символов")
    @Schema(description = "Название книги", example = "Капитанская дочка")
    private String title;

    @NotNull(message = "Поле не должно быть пустым")
    @Positive(message = "Значение должно быть положительным")
    @Schema(description = "ID автора книги", example = "1")
    private Long authorId;

    @NotNull(message = "Поле не должно быть пустым")
    @Min(value = 0, message = "Значение не должно быть отрицательным")
    @Max(value = 2025, message = "Год не должен быть больше текущего")
    @Schema(description = "Год публикации книги", example = "1836")
    private Integer year;

    @NotBlank(message = "Поле не должно быть пустым")
    @Size(min = 3, max = 255, message = "Длина поля должна быть от 3 до 255 символов")
    @Schema(description = "Жанр книги", example = "Исторический роман")
    private String genre;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
