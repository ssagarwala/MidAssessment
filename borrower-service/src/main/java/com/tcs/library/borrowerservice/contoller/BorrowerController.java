package com.tcs.library.borrowerservice.contoller;

import com.tcs.library.borrowerservice.entity.Book;
import com.tcs.library.borrowerservice.entity.Borrower;
import com.tcs.library.borrowerservice.exception.BadRequestException;
import com.tcs.library.borrowerservice.exception.BookNotFoundException;
import com.tcs.library.borrowerservice.exception.InternalServerErrorException;
import com.tcs.library.borrowerservice.service.BorrowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrowers")
public class BorrowerController {

    private static final Logger log = LoggerFactory.getLogger(BorrowerController.class);
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

    @GetMapping(value = "/overDue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getOverDueBooks() {
        List<Book> books =  borrowerService.getOverDueBooks();
        if(books != null){
            return  new ResponseEntity<>(books, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }


    //    Borrow a book (mark it as borrowed).

    @GetMapping("/book/{title}")
    ResponseEntity<String> borrowABook(@PathVariable("title") String title) {
        try{
           Book book =  borrowerService.borrowABook(title);
           String bookId = book.getId();
            return new ResponseEntity<>(bookId, HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        } catch (InternalServerErrorException e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
