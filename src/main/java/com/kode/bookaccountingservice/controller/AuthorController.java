package com.kode.bookaccountingservice.controller;

import com.kode.bookaccountingservice.dto.AuthorRequest;
import com.kode.bookaccountingservice.entity.Author;
import com.kode.bookaccountingservice.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);

    @PostMapping
    public void addAuthor(@RequestBody AuthorRequest authorRequest) {
        authorService.addAuthor(authorRequest);
        ResponseEntity.ok("Автор добавлен");
    }

    @GetMapping
    public ResponseEntity<Page<Author>> getAuthors(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Page<Author> authors = authorService.getAuthors(page, size);
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = authorService.getAuthorById(id);
        return author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}