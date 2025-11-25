package ru.mentee.banking.service.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mentee.banking.api.dto.AuditEntry;

@Component
@RequiredArgsConstructor
public class AuditService {
  private final List<AuditEntry> logs = new CopyOnWriteArrayList<>();

  public void log(AuditEntry entry) {
    entry.setId(UUID.randomUUID().toString());
    entry.setTimestamp(LocalDateTime.now());
    logs.add(entry);
  }

  public List<AuditEntry> getLogs(String userId, LocalDateTime from, LocalDateTime to) {
    return logs.stream()
        .filter(e -> userId == null || userId.equals(e.getUserId()))
        .filter(e -> from == null || !e.getTimestamp().isBefore(from))
        .filter(e -> to == null || !e.getTimestamp().isAfter(to))
        .sorted(Comparator.comparing(AuditEntry::getTimestamp).reversed())
        .toList();
  }
}
