package com.kode.bookaccountingservice.controller;

import com.kode.bookaccountingservice.dto.AuthorRequest;
import com.kode.bookaccountingservice.entity.Author;
import com.kode.bookaccountingservice.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST-контроллер для обработки запросов на авторов.
 */
@RestController
@RequestMapping("/authors")
@Tag(name = "Authors", description = "API для управления авторами")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);

    /**
     * Получает POST запрос на добавление автора.
     * @param authorRequest
     */
    @PostMapping
    @Operation(summary = "Добавить нового автора", description = "Добавляет автора на основе предоставленных данных")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Автор успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные"),
            @ApiResponse(responseCode = "409", description = "Автор с таким именем уже добавлен в базу")
    })
    public void addAuthor(@RequestBody AuthorRequest authorRequest) {
        log.info("Получен запрос на добавление данных о новом авторе");
        authorService.addAuthor(authorRequest);
        ResponseEntity.ok("Автор добавлен");
    }

    /**
     * Получает GET запрос на постраничную выдачу списка авторов.
     * @param page номер страницы
     * @param size количество записей на странице
     * @return страница авторов
     */
    @GetMapping
    @Operation(summary = "Получить список авторов", description = "Возвращает список авторов с пагинацией")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список авторов успешно получен")
    })
    public ResponseEntity<Page<Author>> getAuthors(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        log.info("Получен запрос на выдачу страницы авторов");
        Page<Author> authors = authorService.getAuthors(page, size);
        return ResponseEntity.ok(authors);
    }

    /**
     * Получает GET запрос на выдачу автора по идентификатору.
     * @param id идентификатор автора
     * @return автор
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получить автора по ID", description = "Возвращает автора по указанному ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Автор найден"),
            @ApiResponse(responseCode = "404", description = "Автор не найден")
    })
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        log.info("Получен запрос на выдачу данных об авторе по идентификатору");
        Optional<Author> author = authorService.getAuthorById(id);
        return author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}