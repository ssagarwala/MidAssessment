package com.tcs.library.bookservice.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("books")
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Data
public class Book {
    @Id
    String id;
    String title;
    String ISBN;
    List<String> authorIds;
   // @Enumerated(EnumType.STRING)
    BookStatus status;
}

