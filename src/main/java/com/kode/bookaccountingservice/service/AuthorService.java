package com.kode.bookaccountingservice.service;

import com.kode.bookaccountingservice.dto.AuthorRequest;
import com.kode.bookaccountingservice.entity.Author;
import com.kode.bookaccountingservice.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

/**
 * Сервис для работы с авторами
 */
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    private final static Logger log = LoggerFactory.getLogger(AuthorService.class);

    /**
     * Добавляет нового автора.
     * @param authorRequest данные автора
     */
    @Transactional
    public void addAuthor(AuthorRequest authorRequest){
        Author author = new Author();
        author.setName(authorRequest.getName());
        author.setBirthYear(authorRequest.getBirthYear());
        log.info("Добавление нового автора " + author.getName());
        authorRepository.save(author);
    }

    /**
     * Получает список авторов с пагинацией.
     * @param page номер страницы (начинается с 0)
     * @param size количество записей на страницу
     * @return страница с авторами
     */
    @Transactional
    public Page<Author> getAuthors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("Поиск авторов постранично");
        return authorRepository.findAll(pageable);
    }

    /**
     * Получает автора по ID.
     * @param id идентификатор автора
     * @return Optional с автором или пустой, если автор не найден
     */
    @Transactional
    public Optional<Author> getAuthorById(@PathVariable Long id) {
        log.info("Поиск автора по указанному идентификатору");
        return authorRepository.findById(id);
    }
}