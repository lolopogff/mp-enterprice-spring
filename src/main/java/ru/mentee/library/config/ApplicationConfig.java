package ru.mentee.library.config;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class ApplicationConfig {

  @Bean
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public RequestScopedBean requestScopedBean() {
    return new RequestScopedBean();
  }

  @Getter
  public static class RequestScopedBean {
    private final String value = "Request-scoped bean created at: " + System.currentTimeMillis();
  }
}
