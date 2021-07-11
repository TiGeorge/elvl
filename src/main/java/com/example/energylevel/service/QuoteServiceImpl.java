package com.example.energylevel.service;

import com.example.energylevel.dto.QuoteDto;
import com.example.energylevel.model.Quote;
import com.example.energylevel.repository.QuoteRepository;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.Validator;

@RequiredArgsConstructor
@Service
public class QuoteServiceImpl implements QuoteService {

  private final QuoteRepository quoteRepository;
  private final Validator validator;

  @Override
  @Transactional
  public Quote saveQuote(QuoteDto quoteDto) {
    validateQuoteDto(quoteDto);
    return quoteRepository.save(
      new Quote(null, quoteDto.getIsin(), quoteDto.getBid(), quoteDto.getAsk(), quoteDto.getTimestamp()));
  }

  private void validateQuoteDto(QuoteDto quoteDto) {
    Set<ConstraintViolation<QuoteDto>> violations = validator.validate(quoteDto);
    if (!violations.isEmpty()) {
      StringBuilder sb = new StringBuilder();
      for (ConstraintViolation<QuoteDto> constraintViolation : violations) {
        sb.append(constraintViolation.getMessage());
      }
      throw new ConstraintViolationException("Error occurred: " + sb, violations);
    }
  }
}
