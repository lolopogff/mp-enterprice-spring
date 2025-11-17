package ru.mentee.library.api.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mentee.library.api.dto.BookDto;
import ru.mentee.library.api.dto.CreateBookRequest;
import ru.mentee.library.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

  private final BookService bookService;

  @Autowired
  public BookController(@Qualifier("bookService") BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public ResponseEntity<List<BookDto>> getBooks(@RequestParam(required = false) String author) {
    List<BookDto> books = bookService.getAllBooks(author);
    return ResponseEntity.ok(books);
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
    BookDto book = bookService.getBookById(id);
    return ResponseEntity.ok(book);
  }

  @PostMapping
  public ResponseEntity<Void> createBook(@Valid @RequestBody CreateBookRequest request) {
    BookDto createdBook = bookService.createBook(request);
    return ResponseEntity.created(URI.create("/api/books/" + createdBook.getId())).build();
  }
}
