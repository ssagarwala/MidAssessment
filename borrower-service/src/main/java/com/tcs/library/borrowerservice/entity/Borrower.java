package com.tcs.library.borrowerservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Document("borrowers")
public class Borrower {

    @Id
    String id;

    String name;

    List<String> bookIds;

}
