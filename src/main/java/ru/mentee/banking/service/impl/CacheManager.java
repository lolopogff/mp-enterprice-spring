package ru.mentee.banking.service.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Component;

@Component
public class CacheManager {
  private final ConcurrentMap<String, Object> cache = new ConcurrentHashMap<>();

  public Object get(String key) {
    return cache.get(key);
  }

  public void put(String key, Object value) {
    cache.put(key, value);
  }
}
