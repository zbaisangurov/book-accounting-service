package com.kode.bookaccountingservice.controller;

import com.kode.bookaccountingservice.dto.BookRequest;
import com.kode.bookaccountingservice.dto.BookResponse;
import com.kode.bookaccountingservice.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @PostMapping
    public void addBook(@RequestBody BookRequest bookRequest) {
        log.info("Получен запрос на добавление данных о новой книге");
        bookService.addBook(bookRequest);
        ResponseEntity.ok("Книга успешно добавлена");
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        log.info("Получен запрос на выдачу списка всех книг");
        List<BookResponse> books = bookService.getAllBooks();
        log.info("Получение списка книг");
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        log.info("Получен запрос на выдачу данных о книге #" + id);
        Optional<BookResponse> bookResponse = bookService.getBookById(id);
        if (bookResponse.isPresent()) {
            return ResponseEntity.ok(bookResponse.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        log.info("Получен запрос на обновление данных о книге #" + id);
        bookService.updateBook(id, bookRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        log.info("Получен запрос на удаление данных о книге #" + id);
        bookService.deleteBook(id);
    }
}
