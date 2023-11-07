package com.tcs.library.authorservice.controller;

import com.tcs.library.authorservice.entity.Author;
import com.tcs.library.authorservice.repository.AuthorRepository;
import com.tcs.library.authorservice.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

        //Retrieve all authors: GET /api/authors
        @GetMapping
        public List<Author> getAllAuthors() {
            return authorService.getAllAuthors();
        }

        //Get an author by ID: GET /api/authors/{id}
        @GetMapping(value ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        Optional<Author> getAuthorById(@PathVariable("id") String id) {
            return authorService.getAuthorById(id);
        }

        //Add a new author: POST /api/authors
        @PostMapping(consumes = "application/json", produces = "application/json")
        public Author createAuthor(@RequestBody Author author) {
            return authorService.createAuthor(author);
        }

        //Update an author: PUT /api/authors/{id}
        @PutMapping(value="/{id}",consumes = "application/json", produces = "application/json")
        Author updateAuthor(@RequestBody Author newAuthor, @PathVariable String id) {

            return authorService.getAuthorById(id)
                    .map(author -> {
                        author.setName(newAuthor.getName());
                        author.setCountry(newAuthor.getCountry());
                        author.setBooks(newAuthor.getBooks());
                        return authorService.createAuthor(author);
                    })
                    .orElseGet(() -> {
                        Author author = Author.builder()
                                .name(newAuthor.getName())
                                .build();
                        return authorService.createAuthor(author);
                    });
        }

        @DeleteMapping("/{id}")
        void deleteAuthor(@PathVariable String id) {
            authorService.deleteAuthor(id);
        }

//        @GetMapping("/api/authors/{id}/books")
//        List<Book> getBooksByAuthorById(@PathVariable("id") String id) {
//            Optional<Author> authorOptional = authorRepository.findById(id);
//            if(authorOptional != null) {
//                Author author = authorOptional.get();
//                List<Book> books = author.getBooks();
//                return books;
//            }
//            return null;
//        }

}