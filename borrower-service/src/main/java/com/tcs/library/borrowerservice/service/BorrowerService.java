package com.tcs.library.borrowerservice.service;

import com.tcs.library.borrowerservice.entity.Book;
import com.tcs.library.borrowerservice.entity.Borrower;
import com.tcs.library.borrowerservice.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowerService {

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
}
