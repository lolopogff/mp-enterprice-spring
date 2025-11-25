package ru.mentee.banking.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {
  String key() default ""; // если пусто — генерируем автоматически
}
