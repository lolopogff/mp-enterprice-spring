package ru.mentee.banking.service;

public interface SecurityContext {
  String getCurrentUserId();

  boolean hasRole(String role);
}
