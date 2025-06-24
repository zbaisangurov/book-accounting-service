package com.kode.bookaccountingservice.service;

import com.kode.bookaccountingservice.dto.AuthorRequest;
import com.kode.bookaccountingservice.entity.Author;
import com.kode.bookaccountingservice.exception.AuthorAlreadyExistsException;
import com.kode.bookaccountingservice.exception.AuthorNotFoundException;
import com.kode.bookaccountingservice.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private AuthorRequest authorRequest;
    private Author author;

    @BeforeEach
    void setUp() {
        authorRequest = new AuthorRequest();
        authorRequest.setName("Test guy");
        authorRequest.setBirthYear(1969);

        author = new Author();
        author.setName("Test guy");
        author.setBirthYear(1969);
    }

    @Test
    void addAuthorSuccess() {
        when(authorRepository.existsByName(authorRequest.getName())).thenReturn(false);
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        authorService.addAuthor(authorRequest);
        verify(authorRepository, times(1)).existsByName(authorRequest.getName());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void addAuthorAlreadyExistsException() {
        when(authorRepository.existsByName(authorRequest.getName())).thenReturn(true);
        AuthorAlreadyExistsException exception = assertThrows(AuthorAlreadyExistsException.class,
                () -> authorService.addAuthor(authorRequest));
        assertEquals("Автор с таким именем уже добавлен в базу", exception.getMessage());
        verify(authorRepository, times(1)).existsByName(authorRequest.getName());
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void getAuthorsSuccess() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Author> authorPage = new PageImpl<>(Arrays.asList(author));
        when(authorRepository.findAll(pageable)).thenReturn(authorPage);
        Page<Author> result = authorService.getAuthors(0, 5);
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(author.getName(), result.getContent().get(0).getName());
        verify(authorRepository, times(1)).findAll(pageable);
    }

    @Test
    void getAuthorByIdSuccess() {
        when(authorRepository.existsById(1L)).thenReturn(true);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Optional<Author> result = authorService.getAuthorById(1L);
        assertTrue(result.isPresent());
        assertEquals(author.getName(), result.get().getName());
        assertEquals(author.getBirthYear(), result.get().getBirthYear());
        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void getAuthorByIdNotFoundException() {
        when(authorRepository.existsById(1L)).thenReturn(false);
        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class,
                () -> authorService.getAuthorById(1L));
        assertEquals("Автор с ID 1 не найден", exception.getMessage());
        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, never()).findById(1L);
    }
}
