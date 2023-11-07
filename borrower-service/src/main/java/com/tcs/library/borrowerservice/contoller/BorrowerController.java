package com.tcs.library.borrowerservice.contoller;

import com.tcs.library.borrowerservice.entity.Book;
import com.tcs.library.borrowerservice.entity.Borrower;
import com.tcs.library.borrowerservice.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrowers")
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    @Autowired
    private RestTemplate restTemplate;
    @GetMapping
    public List<Borrower> getAllBorrowers() {
        return borrowerService.getAllBorrowers();
    }

    //Get a borrower by ID: GET /api/borrowers/{id}
    @GetMapping("/{id}")
    Optional<Borrower> getBorrowerById(@PathVariable("id") String id) {
        return borrowerService.getBorrowerById(id);
    }

    //Add a new borrower: POST /api/borrowers
    @PostMapping
    public Borrower createBorrower(@RequestBody Borrower borrower) {
        return borrowerService.createBorrower(borrower);
    }

    //Update a borrower: PUT /api/borrowers/{id}
    @PutMapping("/{id}")
    Borrower updateBorrower(@RequestBody Borrower borrower, @PathVariable String id) {
        return borrowerService.updateBorrower(borrower, id);
    }

    //Delete a book: DELETE /api/books/{id}
    @DeleteMapping("/{id}")
    void deleteBorrower(@PathVariable String id) {

        borrowerService.deleteBorrower(id);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getOverDueBooks(
            @RequestParam(value = "status", required = false) String status) {
        List<Book> books =  borrowerService.getOverDueBooks(status);
        if(books != null){
            return  new ResponseEntity<>(books, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
}
