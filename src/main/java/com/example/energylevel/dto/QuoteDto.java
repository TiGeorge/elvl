package com.example.energylevel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuoteDto {

  private String isin;
  private BigDecimal bid;
  private BigDecimal ask;
  private LocalDateTime timestamp;
}
