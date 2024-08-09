package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
