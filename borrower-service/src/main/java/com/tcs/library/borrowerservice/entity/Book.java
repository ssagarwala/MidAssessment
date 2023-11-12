package com.tcs.library.borrowerservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Book {
    @Id
    String id;
    String title;
    String ISBN;
    List<String> authorIds;
    // @Enumerated(EnumType.STRING)
    BookStatus status;
}

