package com.kode.bookaccountingservice.service;

import com.kode.bookaccountingservice.dto.BookRequest;
import com.kode.bookaccountingservice.dto.BookResponse;
import com.kode.bookaccountingservice.entity.Author;
import com.kode.bookaccountingservice.entity.Book;
import com.kode.bookaccountingservice.repository.AuthorRepository;
import com.kode.bookaccountingservice.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    @Transactional
    public void addBook(BookRequest bookRequest) {
        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() -> {
                    return new IllegalArgumentException("Автор с указанным айди не найден: " + bookRequest.getAuthorId());
                });
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(author);
        book.setYear(bookRequest.getYear());
        book.setGenre(bookRequest.getGenre());
        log.info("Добавление новой книги " + book.getTitle());
        bookRepository.save(book);
    }

    @Transactional
    public List<BookResponse> getAllBooks() {
        log.info("Получение информации обо всех книгах");
        List<Book> books = bookRepository.findAll();
        log.info("Найдено {} книг", books.size());
        return books.stream().map(this::toBookResponse).collect(Collectors.toList());
    }


    @Transactional
    public Optional<BookResponse> getBookById(Long id) {
        log.info("Получение книги по айди " + id);
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            log.info("Книга найдена");
        } else {
            log.warn("Книги с таким айди не найдено");
        }
        return book.map(this::toBookResponse);
    }

    @Transactional
    public void updateBook(Long id, BookRequest bookRequest) {
        log.info("Обновляются данные о книге #" + id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("Книги с таким айди не найдено");
                });
        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() -> {
                    log.error("Автора с таким айди не найдено");
                    return new IllegalArgumentException("Автора с таким айди не найдено");
                });
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(author);
        book.setYear(bookRequest.getYear());
        book.setGenre(bookRequest.getGenre());
        bookRepository.save(book);
        log.info("Данные о книге обновлены");
    }

    @Transactional
    public void deleteBook(Long id) {
        log.info("Удаление данных о книге #" + id);
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Книги с таким айди не найдено");
        }
        bookRepository.deleteById(id);
        log.info("Данные о книге удалены");
    }

    private BookResponse toBookResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthorId(book.getAuthor().getId());
        response.setYear(book.getYear());
        response.setGenre(book.getGenre());
        return response;
    }
}
