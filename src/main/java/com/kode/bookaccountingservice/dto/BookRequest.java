package com.kode.bookaccountingservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO для добавления и обновления книг
 */
public class BookRequest {
    @Schema(description = "Название книги", example = "Капитанская дочка")
    private String title;
    @Schema(description = "ID автора книги", example = "1")
    private Long authorId;
    @Schema(description = "Год публикации книги", example = "1836")
    private Integer year;
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
