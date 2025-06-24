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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    private BookRequest bookRequest;
    private Book book;
    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author(1L, "Test guy", 1969);

        bookRequest = new BookRequest();
        bookRequest.setTitle("Test book");
        bookRequest.setAuthorId(1L);
        bookRequest.setYear(2000);
        bookRequest.setGenre("Test genre");

        book = new Book();
        book.setTitle("Test book");
        book.setAuthor(author);
        book.setYear(2000);
        book.setGenre("Test genre");
    }

    @Test
    void addBook_Success() {
        when(bookRepository.existsByTitle(bookRequest.getTitle())).thenReturn(false);
        when(authorRepository.findById(bookRequest.getAuthorId())).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        bookService.addBook(bookRequest);
        verify(bookRepository, times(1)).existsByTitle(bookRequest.getTitle());
        verify(authorRepository, times(1)).findById(bookRequest.getAuthorId());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void addBook_BookAlreadyExists_ThrowsException() {
        when(bookRepository.existsByTitle(bookRequest.getTitle())).thenReturn(true);
        BookAlreadyExistsException exception = assertThrows(BookAlreadyExistsException.class,
                () -> bookService.addBook(bookRequest));
        assertEquals("Книга с таким наименованием уже добавлена в базу", exception.getMessage());
        verify(bookRepository, times(1)).existsByTitle(bookRequest.getTitle());
        verify(authorRepository, never()).findById(anyLong());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void addBook_AuthorNotFound_ThrowsException() {
        when(bookRepository.existsByTitle(bookRequest.getTitle())).thenReturn(false);
        when(authorRepository.findById(bookRequest.getAuthorId())).thenReturn(Optional.empty());
        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class,
                () -> bookService.addBook(bookRequest));
        assertEquals("Автор с ID1 не найден: ", exception.getMessage());
        verify(bookRepository, times(1)).existsByTitle(bookRequest.getTitle());
        verify(authorRepository, times(1)).findById(bookRequest.getAuthorId());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void getAllBooks_Success() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
        List<BookResponse> result = bookService.getAllBooks();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(book.getTitle(), result.get(0).getTitle());
        assertEquals(book.getAuthor().getId(), result.get(0).getAuthorId());
        assertEquals(book.getYear(), result.get(0).getYear());
        assertEquals(book.getGenre(), result.get(0).getGenre());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_Success() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Optional<BookResponse> result = bookService.getBookById(1L);
        assertTrue(result.isPresent());
        assertEquals(book.getTitle(), result.get().getTitle());
        assertEquals(book.getAuthor().getId(), result.get().getAuthorId());
        assertEquals(book.getYear(), result.get().getYear());
        assertEquals(book.getGenre(), result.get().getGenre());
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_NotFound_ThrowsException() {
        when(bookRepository.existsById(1L)).thenReturn(false);
        BookNotFoundException exception = assertThrows(BookNotFoundException.class,
                () -> bookService.getBookById(1L));
        assertEquals("Книга с ID 1 не найдена", exception.getMessage());
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, never()).findById(1L);
    }

    @Test
    void updateBook_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        bookService.updateBook(1L, bookRequest);
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
        assertEquals(bookRequest.getTitle(), book.getTitle());
        assertEquals(bookRequest.getYear(), book.getYear());
        assertEquals(bookRequest.getGenre(), book.getGenre());
    }

    @Test
    void updateBook_NotFound_ThrowsException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        BookNotFoundException exception = assertThrows(BookNotFoundException.class,
                () -> bookService.updateBook(1L, bookRequest));
        assertEquals("Книга с ID 1 не найдена", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void deleteBook_Success() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBook_NotFound_ThrowsException() {
        when(bookRepository.existsById(1L)).thenReturn(false);
        BookNotFoundException exception = assertThrows(BookNotFoundException.class,
                () -> bookService.deleteBook(1L));
        assertEquals("Книга с ID 1 не найдена", exception.getMessage());
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, never()).deleteById(1L);
    }
}