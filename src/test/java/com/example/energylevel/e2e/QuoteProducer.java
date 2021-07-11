package com.example.energylevel.e2e;

import com.example.energylevel.model.Quote;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

/** Вспомогательный класс для генерации тестовых котировок */
public class QuoteProducer {

  public Quote getQuote() {
    return getRandomQuote();
  }

  private Quote getRandomQuote() {
    BigDecimal bid =
        new BigDecimal(BigInteger.valueOf(ThreadLocalRandom.current().nextInt(10000)), 2);

    Quote quote = new Quote();
    quote.setIsin("RU" + String.format("%010d", ThreadLocalRandom.current().nextInt(20)));
    quote.setTimestamp(LocalDateTime.now());
    quote.setBid(bid);
    quote.setAsk(bid.add(BigDecimal.TEN));

    return quote;
  }
}
