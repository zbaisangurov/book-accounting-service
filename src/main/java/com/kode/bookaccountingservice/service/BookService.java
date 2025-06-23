package com.kode.bookaccountingservice.service;

import com.kode.bookaccountingservice.dto.BookRequest;
import com.kode.bookaccountingservice.dto.BookResponse;
import com.kode.bookaccountingservice.entity.Author;
import com.kode.bookaccountingservice.entity.Book;
import com.kode.bookaccountingservice.exception.AuthorNotFoundException;
import com.kode.bookaccountingservice.exception.BookAlreadyExistsException;
import com.kode.bookaccountingservice.exception.BookNotFoundException;
import com.kode.bookaccountingservice.repository.AuthorRepository;
import com.kode.bookaccountingservice.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для работы с книгами
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    /**
     * Добавляет новую книгу.
     * @param bookRequest данные книги
     * @throws IllegalArgumentException если автор не найден
     */
    @Transactional
    public void addBook(BookRequest bookRequest) {
        if (bookRepository.existsByTitle(bookRequest.getTitle())){
            String message = "Книга с таким наименованием уже добавлена в базу";
            log.error(message);
            throw new BookAlreadyExistsException(message);
        }
        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() -> {
                   return new AuthorNotFoundException("Автор с ID" + bookRequest.getAuthorId() + " не найден: ");
                });
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(author);
        book.setYear(bookRequest.getYear());
        book.setGenre(bookRequest.getGenre());
        log.info("Добавление новой книги " + book.getTitle());
        bookRepository.save(book);
    }

    /**
     * Получает список всех книг.
     * @return список книг
     */
    @Transactional
    public List<BookResponse> getAllBooks() {
        log.info("Получение информации обо всех книгах");
        List<Book> books = bookRepository.findAll();
        log.info("Найдено {} книг", books.size());
        return books.stream().map(this::toBookResponse).collect(Collectors.toList());
    }

    /**
     * Получает книгу по ID.
     * @param id идентификатор книги
     * @return Optional с книгой или пустой, если книга не найдена
     */
    @Transactional
    public Optional<BookResponse> getBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            String message = "Книга с ID " + id + " не найдена";
            log.error(message);
            throw new BookNotFoundException(message);
        }
        log.info("Получение книги по идентификатору " + id);
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            log.info("Книга найдена");
        } else {
            log.warn("Книги с таким идентификатором не найдено");
        }
        return book.map(this::toBookResponse);
    }

    /**
     * Обновляет информацию о книге.
     * @param id идентификатор книги
     * @param bookRequest новые данные книги
     * @throws IllegalArgumentException если книга или автор не найдены
     */
    @Transactional
    public void updateBook(Long id, BookRequest bookRequest) {
        log.info("Обновляются данные о книге #" + id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Книга с ID " + id + " не найдена";
                    log.error(message);
                    return new BookNotFoundException(message);
                });
        book.setTitle(bookRequest.getTitle());
        book.setYear(bookRequest.getYear());
        book.setGenre(bookRequest.getGenre());
        bookRepository.save(book);
        log.info("Данные о книге обновлены");
    }

    /**
     * Удаляет книгу по ID.
     * @param id идентификатор книги
     * @throws IllegalArgumentException если книга не найдена
     */
    @Transactional
    public void deleteBook(Long id) {
        log.info("Удаление данных о книге #" + id);
        if (!bookRepository.existsById(id)) {
            String message = "Книга с ID " + id + " не найдена";
            log.error(message);
            throw new BookNotFoundException(message);
        }
        bookRepository.deleteById(id);
        log.info("Данные о книге удалены");
    }

    /**
     * Преобразует сущность Book в DTO BookResponse.
     * @param book сущность книги
     * @return объект BookResponse
     */
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
