package com.tcs.library.bookservice.service;

import com.tcs.library.bookservice.controller.BookController;
import com.tcs.library.bookservice.entity.Author;
import com.tcs.library.bookservice.entity.Book;
import com.tcs.library.bookservice.entity.BookStatus;
import com.tcs.library.bookservice.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        if(book != null) {
            if (book.getAuthorIds() != null) {
                log.info("Add book to author");
                for (String id : book.getAuthorIds()) {
                    Author author = restTemplate.getForObject("http://localhost:8081/authorservice/authors/" + id, Author.class);
                    author.getBooks().add(book.getTitle());
                    restTemplate.put("http://localhost:8081/authorservice/authors/"+id,author,Author.class);
                }
            }
            log.info("Save book to repo");
            bookRepository.save(book);
        }
        return book;
    }

    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(Book book, String id) {

        return bookRepository.findById(id)
                .map(updatedBook -> {
                    updatedBook.setTitle(book.getTitle());
                    updatedBook.setISBN(book.getISBN());
                    updatedBook.setStatus(book.getStatus());
                    updatedBook.setAuthorIds(book.getAuthorIds());
                    return bookRepository.save(book);
                })
                .orElseGet(() -> {
                    Book updatedBook = Book.builder()
                            .title(book.getTitle())
                            .authorIds(book.getAuthorIds())
                            .status(book.getStatus())
                            .ISBN(book.getISBN())
                            .id(UUID.randomUUID().toString())
                            .build();
                    return bookRepository.save(book);
                });
    }

    public void deleteBook(String id) {
        if (id != null) {
            log.info("delete book from author");
            Optional<Book> bookOptional = bookRepository.findById(id);
            if (bookOptional != null) {
                Book book = bookOptional.get();
                if (book.getAuthorIds() != null) {
                    log.info("Add book to author");
                    for (String authorId : book.getAuthorIds()) {
                        Author author = restTemplate.getForObject("http://localhost:8081/authorservice/authors/" + authorId, Author.class);
                        author.getBooks().remove(book.getTitle());
                        restTemplate.put("http://localhost:8081/authorservice/authors/" + authorId, author, Author.class);
                    }
                }
                log.info("Save book to repo");
                bookRepository.save(book);
            }
            log.info("delete book from collection");
            bookRepository.deleteById(id);
        }
    }

    public Author getAuthorById(String authorId) {
        return restTemplate.getForObject("http://localhost:8081/authors/" + authorId, Author.class);
    }

    public List<Book> getBooksByAuthorById(String id) {
        List<Book> books = getAllBooks();
        List<Book> booksByAuthor = new ArrayList<>();
        if(books != null && books.size() > 0){
            books.stream().filter(book -> {
                for (int i = 0; i < book.getAuthorIds().size(); i++) {
                    if (id.equals(book.getAuthorIds().get(i))) {
                        booksByAuthor.add(book);
                    }
                }
                return false;
            });
        }
         return booksByAuthor;
        }

    public List<Book> getAllOverDueBooks(String status) {
        List<Book> books =  bookRepository.findByStatus(status);
      return books;
    }

    public Book getBookByTitle(String title) {
        Optional<Book> bookOptional = bookRepository.findByTitle(title);
        if(bookOptional != null){
            return bookOptional.get();
        }
        else return null;
    }
}
