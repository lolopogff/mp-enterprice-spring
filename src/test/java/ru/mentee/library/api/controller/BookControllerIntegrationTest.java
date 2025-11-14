package ru.mentee.library.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class BookControllerIntegrationTest {

  @Container static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
    registry.add(
        "spring.liquibase.change-log", () -> "classpath:db/changelog/db.changelog-master.yaml");
  }

  @Autowired private MockMvc mockMvc;

  @Test
  @DisplayName("Should создать книгу с валидными данными")
  void shouldCreateBookWithValidData() throws Exception {
    // Given
    String requestJson =
        """
            {
                "title": "Test Book",
                "author": "Test Author",
                "isbn": "9783161484100"
            }
        """;

    // When & Then - тестируем реальную работу с БД
    mockMvc
        .perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"));
  }

  @Test
  @DisplayName("Should вернуть ошибку при невалидном ISBN")
  void shouldReturnErrorWithInvalidIsbn() throws Exception {
    // Given
    String requestJson =
        """
            {
                "title": "Test Book",
                "author": "Test Author",
                "isbn": "invalid-isbn"
            }
        """;

    // When & Then
    mockMvc
        .perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should вернуть ошибку при пустом title")
  void shouldReturnErrorWithEmptyTitle() throws Exception {
    // Given
    String requestJson =
        """
            {
                "title": "",
                "author": "Test Author",
                "isbn": "9783161484100"
            }
        """;

    // When & Then
    mockMvc
        .perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isBadRequest());
  }
}
