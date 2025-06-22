package com.kode.bookaccountingservice.repository;

import com.kode.bookaccountingservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
