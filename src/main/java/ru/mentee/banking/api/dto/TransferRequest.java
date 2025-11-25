package ru.mentee.banking.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransferRequest {
  @NotBlank private String fromAccount;
  @NotBlank private String toAccount;

  @DecimalMin("0.01")
  private BigDecimal amount;

  private String description;
}
