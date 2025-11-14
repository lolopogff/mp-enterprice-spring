package ru.mentee.library.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookDto {
  private Long id;
  private String title;
  private String author;
  private String isbn;

  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate publishedDate;

  private Boolean available;

  public BookDto(
      long id,
      String testBook,
      String testAuthor,
      String s,
      Object o,
      boolean b,
      LocalDateTime now) {}
}
