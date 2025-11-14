package ru.mentee.library.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.api.dto.CreateBookRequest;
import ru.mentee.library.domain.model.Book;
import ru.mentee.library.domain.repository.BookRepository;
import ru.mentee.library.service.BookService;
import ru.mentee.library.service.validation.IsbnValidator;

@Service
@Qualifier("bookServiceImpl")
public class BookServiceImpl implements BookService {
  private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

  private final BookRepository bookRepository;
  private final IsbnValidator isbnValidator;

  @Autowired
  public BookServiceImpl(BookRepository bookRepository, IsbnValidator isbnValidator) {
    this.bookRepository = bookRepository;
    this.isbnValidator = isbnValidator;
  }

  @PostConstruct
  public void init() {
    logger.info("BookServiceImpl bean is initialized");
  }

  @PreDestroy
  public void destroy() {
    logger.info("BookServiceImpl bean is being destroyed");
  }

  @Override
  @Transactional(readOnly = true)
  public List<BookDto> getAllBooks(String author) {
    logger.debug("Fetching books with author filter: {}", author);
    List<Book> books = bookRepository.findByAuthorOptional(author);
    return books.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Override
  public BookDto getBookById(Long id) {
    logger.debug("Fetching book with id: {}", id);
    Book book =
        bookRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Нет книги с id = " + id));
    return convertToDto(book);
  }

  @Override
  public BookDto createBook(CreateBookRequest request) {
    logger.debug("Creating new book: {}", request.getTitle());

    validateIsbn(request.getIsbn());

    if (bookRepository.existsByIsbn(request.getIsbn())) {
      throw new RuntimeException("Book with ISBN " + request.getIsbn() + " already exists");
    }

    Book book =
        Book.builder()
            .title(request.getTitle())
            .author(request.getAuthor())
            .isbn(request.getIsbn())
            .available(true)
            .publishedDate(request.getPublishedDate())
            .build();
    return convertToDto(bookRepository.save(book));
  }

  @Override
  public void validateIsbn(String isbn) {
    if (!isbnValidator.isValid(isbn)) {
      throw new RuntimeException("Invalid ISBN format: " + isbn);
    }
  }

  private BookDto convertToDto(Book book) {
    return new BookDto(
        book.getId(),
        book.getTitle(),
        book.getAuthor(),
        book.getIsbn(),
        book.getPublishedDate(),
        book.getAvailable());
  }
}
