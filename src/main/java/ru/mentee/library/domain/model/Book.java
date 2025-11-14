package ru.mentee.library.domain.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "title", length = 200, nullable = false)
  private String title;

  @Column(name = "author", length = 255, nullable = false)
  private String author;

  @Column(name = "isbn", length = 20, unique = true, nullable = false)
  private String isbn;

  @Column(name = "publishedDate")
  private LocalDate publishedDate;

  @Column(nullable = false)
  private Boolean available = true;
}
