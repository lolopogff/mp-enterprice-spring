package ru.mentee.banking.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Retryable {
  int attempts() default 3;

  long delayMs() default 500;

  Class<? extends Throwable>[] retryOn() default {Exception.class};
}
