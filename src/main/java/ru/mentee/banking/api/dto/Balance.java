package ru.mentee.banking.api.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Balance {
  private String accountId;
  private BigDecimal amount;
  private String currency = "RUB";
}
