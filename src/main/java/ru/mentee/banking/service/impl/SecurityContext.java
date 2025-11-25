package ru.mentee.banking.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class SecurityContext implements ru.mentee.banking.service.SecurityContext {

  private String currentUserId = "user";
  private Set<String> roles = new HashSet<>(List.of("USER"));

  @Override
  public String getCurrentUserId() {
    return currentUserId;
  }

  @Override
  public boolean hasRole(String role) {
    return roles.contains(role);
  }

  public void setTestUser(String userId, Set<String> testRoles) {
    this.currentUserId = userId;
    this.roles = new HashSet<>(testRoles);
  }
}
