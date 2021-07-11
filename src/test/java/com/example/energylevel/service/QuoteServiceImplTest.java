package com.example.energylevel.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import com.example.energylevel.dto.QuoteDto;
import com.example.energylevel.model.Quote;
import com.example.energylevel.repository.QuoteRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("QuoteService test")
@ExtendWith(MockitoExtension.class)
class QuoteServiceImplTest {

  @Mock private QuoteRepository quoteRepository;
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  private QuoteService sut;

  @BeforeEach
  void setup() {
    sut = new QuoteServiceImpl(quoteRepository, validator);
  }

  @Test
  @DisplayName("Should save Quote correctly")
  void should_saveQuoteCorrectly() {
    // Arrange
    when(quoteRepository.save(isA(Quote.class)))
        .thenAnswer(invocation -> invocation.getArgument(0, Quote.class));
    QuoteDto quoteDto =
        new QuoteDto(
            "RU0000000001", BigDecimal.ONE, BigDecimal.TEN, LocalDateTime.of(2021, 10, 10, 10, 10));
    // Act
    Quote resultQuote = sut.saveQuote(quoteDto);
    // Assert
    verify(quoteRepository, times(1)).save(any(Quote.class));
    assertThat(resultQuote).isNotNull();
    assertThat(resultQuote.getIsin()).isEqualTo("RU0000000001");
    assertThat(resultQuote.getBid()).isEqualTo(BigDecimal.ONE);
    assertThat(resultQuote.getAsk()).isEqualTo(BigDecimal.TEN);
    assertThat(resultQuote.getTimestamp()).isEqualTo(LocalDateTime.of(2021, 10, 10, 10, 10));
  }

  @Test
  @DisplayName("Should be a constraint violation exception when Quote incorrect")
  void should_beConstraintViolation_whenQuoteIncorrect() {
    // Arrange
    QuoteDto quoteDto =
      new QuoteDto(
        "RU0000000001", BigDecimal.TEN, BigDecimal.ONE, LocalDateTime.of(2021, 10, 10, 10, 10));
    // Act
    Throwable thrown = catchThrowable(() -> sut.saveQuote(quoteDto));
    // Assert
    assertThat(thrown)
        .isInstanceOf(ConstraintViolationException.class)
        .hasMessageContaining("Bid must be less than ask");
  }
}
