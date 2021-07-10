package com.example.energylevel.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Elvl validation")
class ElvlValidationTest {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  @DisplayName("Should be OK when Elvl is correct")
  void should_beOk_whenElvlIsCorrect() {
    Elvl elvl = new Elvl("RU000A0JX0J2", BigDecimal.TEN, LocalDateTime.now());

    Set<ConstraintViolation<Elvl>> violations = validator.validate(elvl);

    assertThat(violations).isEmpty();
  }

  @Test
  @DisplayName("Should be a constraint violation when isin is null")
  void should_beConstraintViolation_whenIsisnIsNull() {
    Elvl elvl = new Elvl(null, BigDecimal.TEN, LocalDateTime.now());

    Set<ConstraintViolation<Elvl>> violations = validator.validate(elvl);

    assertThat(violations).hasSize(1);
  }

  @Test
  @DisplayName("Should be a constraint violation when elvl is null")
  void should_beConstraintViolation_whenElvlIsNull() {
    Elvl elvl = new Elvl("RU000A0JX0J2", null, LocalDateTime.now());

    Set<ConstraintViolation<Elvl>> violations = validator.validate(elvl);

    assertThat(violations).hasSize(1);
  }

  @Test
  @DisplayName("Should be a constraint violation when timeStamp is null")
  void should_beConstraintViolation_whenTimestampIsNull() {
    Elvl elvl = new Elvl("RU000A0JX0J2", BigDecimal.TEN, null);

    Set<ConstraintViolation<Elvl>> violations = validator.validate(elvl);

    assertThat(violations).hasSize(1);
  }
}
