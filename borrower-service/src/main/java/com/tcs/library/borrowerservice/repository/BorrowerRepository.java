package com.tcs.library.borrowerservice.repository;

import com.tcs.library.borrowerservice.entity.Borrower;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerRepository extends MongoRepository<Borrower, String> {}
