package com.tcs.library.bookservice.repository;

import com.tcs.library.bookservice.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findByTitle(String title);
    List<Book> findByStatus(String status);

}
