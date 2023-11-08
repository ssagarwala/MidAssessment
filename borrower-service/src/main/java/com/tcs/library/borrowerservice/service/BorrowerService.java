package com.tcs.library.borrowerservice.service;

import com.tcs.library.borrowerservice.contoller.BorrowerController;
import com.tcs.library.borrowerservice.entity.Book;
import com.tcs.library.borrowerservice.entity.BookStatus;
import com.tcs.library.borrowerservice.entity.Borrower;
import com.tcs.library.borrowerservice.repository.BorrowerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowerService {

    private static final Logger log = LoggerFactory.getLogger(BorrowerService.class);
    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }

    public Optional<Borrower> getBorrowerById(String id) {
        return borrowerRepository.findById(id);
    }

    public Borrower createBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    public Borrower updateBorrower(Borrower borrower, String id) {
        return borrowerRepository.findById(id)
                .map(updatedBorrower -> {
                    updatedBorrower.setName(borrower.getName());
                    //  book.setAuthors(newBook.getAuthors());
                    //   book.setGenres(newBook.getGenres());
                    return borrowerRepository.save(borrower);
                })
                .orElseGet(() -> {
                    Borrower updatedBorrower = Borrower.builder()
                            .name(borrower.getName())
                            .build();
                    return borrowerRepository.save(updatedBorrower);
                });
    }

    public void deleteBorrower(String id) {
        borrowerRepository.deleteById(id);
    }

    public List<Book> getOverDueBooks(String status) {
        return restTemplate.getForObject("http://localhost:8082/bookservice/books/search?status=" + status, List.class);
    }

    public Book borrowABook(String title) {
            Book book = restTemplate.getForObject("http://localhost:8082/bookservice/books/title/" + title, Book.class);
            if (book != null) {
                String status = book.getStatus().toString();
                if (status.equalsIgnoreCase("AVAILABLE")) {
                    log.info("Book present and available for borrowing");
                    book.setStatus(BookStatus.BORROWED);
                    restTemplate.put("http://localhost:8082/bookservice/books/"+book.getId(),book, Book.class);
                   return book;
                }
            }
            return null;
        }
}
