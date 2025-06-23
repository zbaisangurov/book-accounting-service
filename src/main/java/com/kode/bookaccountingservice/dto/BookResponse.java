package com.kode.bookaccountingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO для выдачи книг
 */
public class BookResponse {
    @Schema(description = "ID книги", example = "1")
    private Long id;
    @Schema(description = "Название книги", example = "Капитанская дочка")
    private String title;
    @Schema(description = "ID автора книги", example = "1")
    private Long authorId;
    @Schema(description = "Год публикации книги", example = "1836")
    private int year;
    @Schema(description = "Жанр книги", example = "Исторический роман")
    private String genre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
