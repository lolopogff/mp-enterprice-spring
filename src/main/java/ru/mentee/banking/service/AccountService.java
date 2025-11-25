package ru.mentee.banking.service;

public interface AccountService {
  String getAccountId();

  boolean hasRole(String role);
}
