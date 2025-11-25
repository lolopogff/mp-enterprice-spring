package ru.mentee.library.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBookRequest {
  @NotBlank(message = "Название является обязательным полем")
  @Size(min = 1, max = 200, message = "В названии должно быть от 1 до 200 символов")
  private String title;

  @NotBlank(message = "ISBN is required")
  @Pattern(
      regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$",
      message = "Invalid ISBN format")
  private String isbn;

  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate publishedDate;
}
