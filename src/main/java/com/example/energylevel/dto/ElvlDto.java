package com.example.energylevel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ElvlDto {

  private String isin;
  private BigDecimal elvl;
  private LocalDateTime timestamp;
}
