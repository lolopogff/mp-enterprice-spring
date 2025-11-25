package ru.mentee.library.service.impl;

import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.api.dto.CreateBookRequest;
import ru.mentee.library.domain.model.Author;
import ru.mentee.library.domain.model.Book;
import ru.mentee.library.domain.repository.BookRepository;

@Slf4j
@Service
@Qualifier("bookServiceImpl")
public class BookService implements ru.mentee.library.service.BookService {

  private final BookRepository bookRepository;

  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public List<BookDto> getAllBooks(Set<Author> authors) {
    List<Book> books;
    if (authors == null || authors.isEmpty()) {
      books = bookRepository.findAll();
    } else {
      books = bookRepository.findByAuthors(authors);
    }
    return books.stream().map(this::convertToDto).toList();
  }

  @Override
  public BookDto getBookById(Long id) {
    if (id == null || id <= 0) {
      throw new RuntimeException("Invalid book id");
    }
    Book res = bookRepository.findBookById(id);
    if (res == null) {
      throw new RuntimeException("Book not found");
    }
    return convertToDto(res);
  }

  @Override
  public List<BookDto> getBooksByAuthors(String author) {
    return List.of();
  }

  @Override
  public BookDto createBook(CreateBookRequest request) {
    return null;
  }

  @Override
  public void validateIsbn(String isbn) {}

  private BookDto convertToDto(Book book) {
    return BookDto.builder()
        .id(book.getId())
        .isbn(book.getIsbn())
        .title(book.getTitle())
        .description(book.getDescription())
        .publicationYear(book.getPublicationYear())
        .pages(book.getPages())
        .category(book.getCategory())
        .status(book.getStatus())
        .createdAt(book.getCreatedAt())
        .updatedAt(book.getUpdatedAt())
        .build();
  }
}
