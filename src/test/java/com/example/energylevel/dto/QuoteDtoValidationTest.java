package com.example.energylevel.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("QuoteDto validation")
class QuoteDtoValidationTest {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  @DisplayName("Should be OK when QuoteDto is correct (Bid isn't null)")
  void should_beOk_whenQuoteIsCorrect() {
    QuoteDto quote =
        new QuoteDto("RU000A0JX0J2", BigDecimal.ONE, BigDecimal.TEN, LocalDateTime.now());

    Set<ConstraintViolation<QuoteDto>> violations = validator.validate(quote);

    assertThat(violations).isEmpty();
  }

  @Test
  @DisplayName("Should be OK when QuoteDto is correct (Bid is null)")
  void should_beOk_whenQuoteIsCorrect_BidIsNull() {
    QuoteDto quote = new QuoteDto("RU000A0JX0J2", null, BigDecimal.TEN, LocalDateTime.now());

    Set<ConstraintViolation<QuoteDto>> violations = validator.validate(quote);

    assertThat(violations).isEmpty();
  }

  @Test
  @DisplayName("Should be a constraint violation when isin is shorter than 12 symbols")
  void should_beConstraintViolation_whenIsinIsTooShort() {
    QuoteDto quote =
        new QuoteDto("RU000A0JX0J", BigDecimal.ONE, BigDecimal.TEN, LocalDateTime.now());

    Set<ConstraintViolation<QuoteDto>> violations = validator.validate(quote);

    assertThat(violations).hasSize(1);
  }

  @Test
  @DisplayName("Should be a constraint violation when isin is longer than 12 symbols")
  void should_beConstraintViolation_whenIsinIsTooLarge() {
    QuoteDto quote =
        new QuoteDto("RU000A0JX0J123", BigDecimal.ONE, BigDecimal.TEN, LocalDateTime.now());

    Set<ConstraintViolation<QuoteDto>> violations = validator.validate(quote);

    assertThat(violations).hasSize(1);
  }

  @Test
  @DisplayName("Should be a constraint violation when ask is null")
  void should_beConstraintViolation_whenAskIsNull() {
    QuoteDto quote = new QuoteDto("RU000A0JX0J2", null, null, LocalDateTime.now());

    Set<ConstraintViolation<QuoteDto>> violations = validator.validate(quote);

    assertThat(violations).hasSize(1);
  }

  @Test
  @DisplayName("Should be a constraint violation when ask is larger than bid")
  void should_beConstraintViolation_whenAskIsLargerThanBid() {
    QuoteDto quote =
        new QuoteDto("RU000A0JX0J2", BigDecimal.TEN, BigDecimal.ONE, LocalDateTime.now());

    Set<ConstraintViolation<QuoteDto>> violations = validator.validate(quote);

    assertThat(violations).hasSize(1);
  }

  @Test
  @DisplayName("Should be a constraint violation when ask equals bid")
  void should_beConstraintViolation_whenAskEqualsBid() {
    QuoteDto quote =
        new QuoteDto("RU000A0JX0J2", BigDecimal.TEN, BigDecimal.TEN, LocalDateTime.now());

    Set<ConstraintViolation<QuoteDto>> violations = validator.validate(quote);

    assertThat(violations).hasSize(1);
  }
}
