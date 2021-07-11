package com.example.energylevel.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quotes", schema = "public")
public class Quote {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Size(min = 12, max = 12)
  private String isin;

  private BigDecimal bid;

  @NotNull
  private BigDecimal ask;

  @NotNull
  @Column(name = "time_stamp", columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private LocalDateTime timestamp;

  @AssertTrue(message = "Bid must be less than ask")
  private boolean isBidLessThanAsk() {
    return bid == null || bid.compareTo(ask) < 0;
  }
}
