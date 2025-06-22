package com.kode.bookaccountingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kode.bookaccountingservice.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
