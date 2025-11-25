package ru.mentee.banking.aspect;

import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.mentee.banking.annotation.RequiresRole;
import ru.mentee.banking.service.impl.SecurityContext;

@Aspect
@Component
@RequiredArgsConstructor
public class SecurityAspect {
  private final SecurityContext securityContext;

  @Around("@annotation(requiresRole)")
  public Object checkRole(ProceedingJoinPoint pjp, RequiresRole requiresRole) throws Throwable {
    if (!securityContext.hasRole(requiresRole.value())) {
      throw new AccessDeniedException("Требуется роль: " + requiresRole.value());
    }
    return pjp.proceed();
  }
}
