package com.example.energylevel.service;

import com.example.energylevel.dto.QuoteDto;
import com.example.energylevel.model.Quote;

public interface QuoteService {

  Quote saveQuote(QuoteDto quoteDto);

}
