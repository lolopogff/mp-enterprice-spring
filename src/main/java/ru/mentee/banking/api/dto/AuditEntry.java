package ru.mentee.banking.api.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AuditEntry {
  private String id;
  private String userId;
  private String operation;
  private LocalDateTime timestamp;
  private String status;
  private Long durationMs;
  private Object details;
}
