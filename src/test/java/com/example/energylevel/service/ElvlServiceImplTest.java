package com.example.energylevel.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.energylevel.model.Elvl;
import com.example.energylevel.model.Quote;
import com.example.energylevel.repository.ElvlRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("ElvlService test")
@ExtendWith(MockitoExtension.class)
class ElvlServiceImplTest {

  @Mock private ElvlRepository elvlRepository;
  @Mock private QuoteService quoteService;

  private ElvlService sut;

  @BeforeEach
  void setup() {
    sut = new ElvlServiceImpl(elvlRepository, quoteService);
  }

  @SneakyThrows
  @Test
  @DisplayName("Should be elvl = bid when bid > elvl")
  void should_BeCorrectCalculation_whenBidLessThanElvl() {
    // Arrange
    when(elvlRepository.findById("RU0000000001"))
        .thenReturn(Optional.of(new Elvl("RU0000000001", getDecimal(5), getDateTime(9))));
    Quote quote = getQuote(getDecimal(10));
    // Act
    Elvl resultElvl = sut.processQuote(quote).get();
    // Assert
    verify(elvlRepository, times(1)).findById("RU0000000001");
    assertThat(resultElvl).isNotNull();
    assertThat(resultElvl.getIsin()).isEqualTo("RU0000000001");
    assertThat(resultElvl.getElvl()).isEqualTo(getDecimal(10));
    assertThat(resultElvl.getTimestamp()).isEqualTo(getDateTime(10));
  }

  @SneakyThrows
  @Test
  @DisplayName("Should be elvl = ask when ask < elvl")
  void should_BeCorrectCalculation_whenAskLessThanElvl() {
    // Arrange
    when(elvlRepository.findById("RU0000000001"))
        .thenReturn(Optional.of(new Elvl("RU0000000001", getDecimal(20), getDateTime(9))));
    Quote quote = getQuote(getDecimal(10));
    // Act
    Elvl resultElvl = sut.processQuote(quote).get();
    // Assert
    verify(elvlRepository, times(1)).findById("RU0000000001");
    assertThat(resultElvl).isNotNull();
    assertThat(resultElvl.getIsin()).isEqualTo("RU0000000001");
    assertThat(resultElvl.getElvl()).isEqualTo(getDecimal(15));
    assertThat(resultElvl.getTimestamp()).isEqualTo(getDateTime(10));
  }

  @SneakyThrows
  @Test
  @DisplayName("Should be elvl = bid when elvl is absent")
  void should_BeCorrectCalculation_whenElvlIsAbsent() {
    // Arrange
    when(elvlRepository.findById("RU0000000001")).thenReturn(Optional.empty());
    Quote quote = getQuote(getDecimal(10));
    // Act
    Elvl resultElvl = sut.processQuote(quote).get();
    // Assert
    verify(elvlRepository, times(1)).findById("RU0000000001");
    verify(elvlRepository, times(1))
        .insertElvl(anyString(), any(BigDecimal.class), any(LocalDateTime.class));
    assertThat(resultElvl).isNotNull();
    assertThat(resultElvl.getIsin()).isEqualTo("RU0000000001");
    assertThat(resultElvl.getElvl()).isEqualTo(getDecimal(10));
    assertThat(resultElvl.getTimestamp()).isEqualTo(getDateTime(10));
  }

  @SneakyThrows
  @Test
  @DisplayName("Should be elvl = bid when bid is absent")
  void should_BeCorrectCalculation_whenBidIsAbsent() {
    // Arrange
    when(elvlRepository.findById("RU0000000001"))
        .thenReturn(Optional.of(new Elvl("RU0000000001", getDecimal(5), getDateTime(9))));
    Quote quote = getQuote(null);
    // Act
    Elvl resultElvl = sut.processQuote(quote).get();
    // Assert
    verify(elvlRepository, times(1)).findById("RU0000000001");
    assertThat(resultElvl).isNotNull();
    assertThat(resultElvl.getIsin()).isEqualTo("RU0000000001");
    assertThat(resultElvl.getElvl()).isEqualTo(getDecimal(15));
    assertThat(resultElvl.getTimestamp()).isEqualTo(getDateTime(10));
  }

  @AfterEach
  void teardown() {
    elvlRepository.deleteAll();
  }

  private LocalDateTime getDateTime(int hour) {
    return LocalDateTime.of(2021, 1, 1, hour, 0);
  }

  private Quote getQuote(BigDecimal bid) {
    return new Quote(1L, "RU0000000001", bid, getDecimal(15), getDateTime(10));
  }

  private BigDecimal getDecimal(int value) {
    return BigDecimal.valueOf(value);
  }
}
