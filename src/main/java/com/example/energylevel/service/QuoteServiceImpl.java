package com.example.energylevel.service;

import com.example.energylevel.model.Quote;
import com.example.energylevel.repository.QuoteRepository;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QuoteServiceImpl implements QuoteService {

  private final QuoteRepository quoteRepository;
  private final Validator validator;

  /** {@inheritDoc} */
  @Override
  @Transactional
  public Quote saveQuote(Quote quoteDto) {
    validateQuoteDto(quoteDto);
    return quoteRepository.save(
      new Quote(null, quoteDto.getIsin(), quoteDto.getBid(), quoteDto.getAsk(), quoteDto.getTimestamp()));
  }

  private void validateQuoteDto(Quote quote) {
    Set<ConstraintViolation<Quote>> violations = validator.validate(quote);
    if (!violations.isEmpty()) {
      StringBuilder sb = new StringBuilder();
      for (ConstraintViolation<Quote> constraintViolation : violations) {
        sb.append(constraintViolation.getMessage());
      }
      throw new ConstraintViolationException("Error occurred: " + sb, violations);
    }
  }
}
