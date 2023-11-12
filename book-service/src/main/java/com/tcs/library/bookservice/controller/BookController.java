package com.tcs.library.bookservice.controller;

import com.tcs.library.bookservice.entity.Author;
import com.tcs.library.bookservice.entity.Book;
import com.tcs.library.bookservice.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private BookService bookService;

    @GetMapping(value = "/author/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Author getAuthorById(@PathVariable("id") String authorId) {
        log.info("Retrieve Author by Book Id");
        return bookService.getAuthorById(authorId);
    }

    @GetMapping
    public List<Book> getAllBook() {
        log.info("Retrieve All Authors");
        return bookService.getAllBooks();
    }

    //Get a book by ID: GET /api/books/{id}
    @GetMapping("/{id}")
    Optional<Book> getBookById(@PathVariable("id") String id) {
        return bookService.getBookById(id);
    }

    //Add a new book: POST /api/books
    @PostMapping
    ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book newbook =  bookService.addBook(book);
        if(newbook != null){
            return  new ResponseEntity<>(newbook, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    //Update a book: PUT /api/books/{id}

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    Book updateBook(@RequestBody Book book, @PathVariable String id) {
        return bookService.updateBook(book, id);
    }

    //Delete a book: DELETE /api/books/{id}
    @DeleteMapping("/{id}")
    void deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
    }

    @GetMapping(value = "/overDue", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Book>> getAllOverDueBooks() {
        List<Book> books =  bookService.getAllOverDueBooks();
        if(books != null){
            return  new ResponseEntity<>(books, HttpStatus.FOUND);
        } else
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    //Get a book by ID: GET /api/books/{id}
    @GetMapping(value = "/title/{title}" , produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Book> getBookByTitle(@PathVariable("title") String  title) {
        try{
            Book book = bookService.getBookByTitle(title);
            if(book != null){
                return new ResponseEntity<>(book, HttpStatus.OK);
            }
        } catch(Exception e){
            log.error("Exception in finding book by title");
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
