package com.cjcaram.transactions.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    @NotNull
    private Long accountId;

    @Positive(message = "The amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Transaction type is required.")
    // This approach is not scalable. It is better create a new annotation where we can specify the enum itself
    @Pattern(regexp = "^(CASA|VIVIENDA)$", message = "Valid values = DEPOSIT|WITHDRAW")
    private String type;
}
