package ru.mentee.library.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mentee.library.domain.model.Author;
import ru.mentee.library.domain.model.Category;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
  Author findByFirstNameAndLastName(String name, String lastName);

  @Query(
      """
            SELECT a
            FROM Author a
            LEFT JOIN a.books b
            GROUP BY a
            HAVING COUNT(b) = :count
            """)
  List<Author> findByCountBooks(@Param("count") Integer count);

  @Query(
      """
            SELECT a
            FROM Author a
            INNER JOIN a.books b
            WHERE b.category = :category
            """)
  List<Author> findAuthorsByCategory(@Param("category") Category category);
}
