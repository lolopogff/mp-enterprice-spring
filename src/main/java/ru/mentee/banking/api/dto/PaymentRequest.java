package ru.mentee.banking.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentRequest {
  @NotBlank private String accountId;
  @NotBlank private String paymentDetails;

  @DecimalMin("0.01")
  private BigDecimal amount;
}
