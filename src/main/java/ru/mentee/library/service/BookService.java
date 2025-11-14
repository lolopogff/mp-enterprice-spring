package ru.mentee.library.service;

import java.util.List;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.api.dto.CreateBookRequest;

public interface BookService {
  List<BookDto> getAllBooks(String author);

  BookDto getBookById(Long id);

  BookDto createBook(CreateBookRequest request);

  void validateIsbn(String isbn);
}
