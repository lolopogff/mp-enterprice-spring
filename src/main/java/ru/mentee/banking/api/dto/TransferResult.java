package ru.mentee.banking.api.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TransferResult {
  private String transactionId;
  private String status;
  private LocalDateTime timestamp;
}
