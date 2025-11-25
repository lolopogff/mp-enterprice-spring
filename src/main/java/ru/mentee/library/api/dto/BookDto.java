package ru.mentee.library.api.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mentee.library.domain.model.Category;
import ru.mentee.library.domain.model.enums.BookStatus;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookDto {
  private Long id;
  private String isbn;
  private String title;
  private String description;
  private Integer publicationYear;
  private Integer pages;
  private Category category;
  private BookStatus status = BookStatus.AVAILABLE;
  private Instant createdAt;
  private Instant updatedAt;
}
