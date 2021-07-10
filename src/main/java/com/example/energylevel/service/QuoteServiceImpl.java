package com.example.energylevel.service;

import com.example.energylevel.dto.QuoteDto;
import com.example.energylevel.model.Quote;
import com.example.energylevel.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QuoteServiceImpl implements QuoteService {

  private final QuoteRepository quoteRepository;

  @Override
  @Transactional
  public Quote saveQuote(QuoteDto quoteDto) {
    return quoteRepository.save(
      new Quote(null, quoteDto.getIsin(), quoteDto.getBid(), quoteDto.getAsk(), quoteDto.getTimestamp()));
  }
}
