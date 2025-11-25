package ru.mentee.library.domain.model;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mentee.library.domain.model.enums.LoanStatus;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  private Book book;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "loan_date", nullable = false)
  private Instant loanDate;

  @Column(name = "due_date", nullable = false)
  private Instant dueDate;

  @Column(name = "return_date")
  private Instant returnDate;

  @Column(name = "status", nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private LoanStatus status = LoanStatus.ACTIVE;

  @Column(name = "created_by", length = 50)
  private String createdBy;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();

  @PrePersist
  protected void onCreate() {
    createdAt = Instant.now();
  }
}
