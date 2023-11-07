package com.tcs.library.authorservice.repository;

import com.tcs.library.authorservice.entity.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {
}
