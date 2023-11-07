package com.tcs.library.authorservice.service;

import com.tcs.library.authorservice.entity.Author;
import com.tcs.library.authorservice.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Optional<Author> getAuthorById(String id) {
        return authorRepository.findById(id);
    }

    public void deleteAuthor(String id) {
         authorRepository.deleteById(id);
    }

}
