package ru.mentee.banking.aspect;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.mentee.banking.annotation.Auditable;
import ru.mentee.banking.api.dto.AuditEntry;
import ru.mentee.banking.service.impl.AuditService;
import ru.mentee.banking.service.impl.SecurityContext;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {
  private final AuditService auditService;
  private final SecurityContext securityContext;

  @Around("@annotation(auditable)")
  public Object audit(ProceedingJoinPoint pjp, Auditable auditable) throws Throwable {
    String operation =
        auditable.operation().isEmpty()
            ? pjp.getSignature().toShortString()
            : auditable.operation();

    String userId = securityContext.getCurrentUserId();
    long start = System.nanoTime();

    AuditEntry entry = new AuditEntry();
    entry.setUserId(userId);
    entry.setOperation(operation);
    entry.setDetails(Map.of("args", Arrays.toString(pjp.getArgs())));

    try {
      Object result = pjp.proceed();
      long durationMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
      entry.setStatus("SUCCESS");
      entry.setDurationMs(durationMs);
      entry.setDetails(Map.of("args", Arrays.toString(pjp.getArgs()), "result", result));
      auditService.log(entry);
      return result;
    } catch (Throwable t) {
      long durationMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
      entry.setStatus("FAILED");
      entry.setDurationMs(durationMs);
      entry.setDetails(Map.of("error", t.getMessage()));
      auditService.log(entry);
      throw t;
    }
  }
}
