package com.example.energylevel.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.*;

/** Модель elvl */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "elvls", schema = "public")
public class Elvl {

  @Id
  @NotNull
  private String isin;

  @NotNull
  private BigDecimal elvl;

  @NotNull
  @Column(name = "time_stamp", columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private LocalDateTime timestamp;
}
