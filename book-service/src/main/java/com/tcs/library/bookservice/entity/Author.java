package com.tcs.library.bookservice.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class Author {

    String id;
    String name;
    String country;
    List<String> books;
}