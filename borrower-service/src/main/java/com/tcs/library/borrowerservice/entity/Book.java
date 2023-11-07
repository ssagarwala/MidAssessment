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
    private String id;
    private String title;
    private String ISBN;
    private List<String> authorIds;
    // @Enumerated(EnumType.STRING)
    private BookStatus status;
}

enum BookStatus {
    AVAILABLE,
    BORROWED,

    OVERDUE
}