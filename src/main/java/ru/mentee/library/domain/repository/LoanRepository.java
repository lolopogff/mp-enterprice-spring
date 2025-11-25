package ru.mentee.library.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mentee.library.domain.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
  List<Loan> findActiveLoans();

  List<Loan> findBookLoansByBookTitle(String title);

  @Query("SELECT l.id FROM Loan l WHERE l.dueDate < CURRENT_TIMESTAMP AND l.returnDate IS NULL")
  List<Loan> findOverdueLoans();

  @Query(
      """
    SELECT
        COUNT(l),
        COALESCE(SUM(CASE WHEN l.returnDate IS NOT NULL THEN 1 ELSE 0 END), 0),
        COALESCE(SUM(CASE WHEN l.dueDate < CURRENT_TIMESTAMP AND l.returnDate IS NULL THEN 1 ELSE 0 END), 0),
        COALESCE(SUM(CASE WHEN l.returnDate IS NULL AND l.dueDate >= CURRENT_TIMESTAMP THEN 1 ELSE 0 END), 0)
    FROM Loan l
    WHERE l.loanDate BETWEEN :start AND :end
    """)
  Object[] getStatsForPeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
