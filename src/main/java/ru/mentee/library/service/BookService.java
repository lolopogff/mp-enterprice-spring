package ru.mentee.library.service;

import java.util.List;
import java.util.Set;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.api.dto.CreateBookRequest;
import ru.mentee.library.domain.model.Author;

public interface BookService {
  List<BookDto> getAllBooks(Set<Author> authors);

  BookDto getBookById(Long id);

  List<BookDto> getBooksByAuthors(String author);

  BookDto createBook(CreateBookRequest request);

  void validateIsbn(String isbn);
}
