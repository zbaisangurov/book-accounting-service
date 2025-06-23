package com.kode.bookaccountingservice.controller;

import com.kode.bookaccountingservice.dto.BookRequest;
import com.kode.bookaccountingservice.dto.BookResponse;
import com.kode.bookaccountingservice.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST-контроллер для обработки запросов на книги.
 */
@RestController
@RequestMapping("/books")
@Tag(name = "Books", description = "API для управления книгами")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    /**
     * Получает POST запрос на добавление книги.
     * @param bookRequest объект дто
     */
    @PostMapping
    @Operation(summary = "Добавляет новую книгу", description = "Добавляет книгу на основе предоставленных данных")
    public void addBook(@RequestBody BookRequest bookRequest) {
        log.info("Получен запрос на добавление данных о новой книге");
        bookService.addBook(bookRequest);
        ResponseEntity.ok("Книга успешно добавлена");
    }

    /**
     * Получает GET запрос на выдачу списка всех книг.
     * @return список книг
     */
    @GetMapping
    @Operation(summary = "Получить список всех книг", description = "Возвращает список всех книг")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        log.info("Получен запрос на выдачу списка всех книг");
        List<BookResponse> books = bookService.getAllBooks();
        log.info("Получение списка книг");
        return ResponseEntity.ok(books);
    }

    /**
     * Получает GET запрос на выдачу книги по идентификатору.
     * @param id идентификатор книги
     * @return книга
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получить книгу по ID", description = "Возвращает книгу по указанному ID")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        log.info("Получен запрос на выдачу данных о книге #" + id);
        Optional<BookResponse> bookResponse = bookService.getBookById(id);
        if (bookResponse.isPresent()) {
            return ResponseEntity.ok(bookResponse.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Получает PUT запрос на обновление данных о книге по идентификатору.
     * @param id идентификатор книги
     * @param bookRequest объект дто
     */
    @PutMapping("/{id}")
    @Operation(summary = "Обновить книгу", description = "Обновляет данные книги по указанному ID")
    public void updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        log.info("Получен запрос на обновление данных о книге #" + id);
        bookService.updateBook(id, bookRequest);
    }

    /**
     * Получает DELETE запрос на удаление данных о книге по идентификатору.
     * @param id идентификатор книги
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить книгу", description = "Удаляет книгу по указанному ID")
    public void deleteBook(@PathVariable Long id) {
        log.info("Получен запрос на удаление данных о книге #" + id);
        bookService.deleteBook(id);
    }
}
