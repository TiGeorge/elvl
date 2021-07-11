package com.example.energylevel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuoteDto {

  @Size(min = 12, max = 12)
  private String isin;

  private BigDecimal bid;

  @NotNull
  private BigDecimal ask;

  @NotNull
  private LocalDateTime timestamp;

  @AssertTrue(message = "Bid must be less than ask")
  private boolean isBidLessThanAsk() {
    return bid == null || bid.compareTo(ask) < 0;
  }
}
