package ru.mentee.library.domain.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mentee.library.domain.model.Author;
import ru.mentee.library.domain.model.Book;
import ru.mentee.library.domain.model.Category;
import ru.mentee.library.domain.model.enums.BookStatus;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  Book findBookById(Long id);

  Book findByIsbn(String isbn, Pageable pageable);

  List<Book> findByAuthors(Set<Author> authors, Pageable pageable);

  List<Book> findByTitle(String title, Pageable pageable);

  List<Book> findByCategory(Category category, Pageable pageable);

  List<Book> findByStatus(BookStatus status, Pageable pageable);

  List<Book> findByPublicationYear(int publicationYear, Pageable pageable);

  @Query("SELECT b FROM Book b JOIN Loan l ON b.id = l.book.id WHERE l.status = 'OVERDUE'")
  List<Book> findOverdueBooks(Pageable pageable);

  @Query(
      """
          SELECT b.id, b.title, b.isbn, COUNT(l)
          FROM Book b
          LEFT JOIN Loan l ON b = l.book
          GROUP BY b.id, b.title
          ORDER BY COUNT(l) DESC
              LIMIT :limit
          """)
  List<Book> findTop10PopularBooks(@Param("limit") Integer limit, Pageable pageable);

  boolean isAvailable(Book book);

  List<Book> findByAuthors(Set<Author> authors);
}
