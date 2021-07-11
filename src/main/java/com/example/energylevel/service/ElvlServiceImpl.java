package com.example.energylevel.service;

import com.example.energylevel.model.Elvl;
import com.example.energylevel.model.Quote;
import com.example.energylevel.repository.ElvlRepository;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Реализация сервиса для роботы с elvl */
@Slf4j
@RequiredArgsConstructor
@Service
public class ElvlServiceImpl implements ElvlService {

  private final ElvlRepository elvlRepository;
  private final QuoteService quoteService;

  /** {@inheritDoc} */
  @Override
  @Transactional(readOnly = true)
  public Optional<Elvl> getElvl(String isin) {
    return elvlRepository.findById(isin);
  }

  /** {@inheritDoc} */
  @Override
  @Transactional(readOnly = true)
  public List<Elvl> getAllElvls(Integer pageNo, Integer pageSize) {
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("isin"));
    Page<Elvl> pagedElvls = elvlRepository.findAll(paging);
    if (pagedElvls.hasContent()) {
      return pagedElvls.getContent();
    } else {
      return Collections.emptyList();
    }
  }

  /** {@inheritDoc} */
  @Async
  @Override
  @Transactional
  public CompletableFuture<Elvl> processQuote(Quote quote) {
    quoteService.saveQuote(quote);
    Elvl result =
        elvlRepository
            .findById(quote.getIsin())
            .map(
                elvl -> {
                  log.info("Elvl changed: " + elvl);
                  return calculateElvl(quote, elvl);
                })
            .orElseGet(
                () -> {
                  Elvl elvl = createElvl(quote);
                  log.info("Elvl created: " + elvl);
                  return elvl;
                });
    return CompletableFuture.completedFuture(result);
  }

  private Elvl calculateElvl(Quote quote, Elvl elvl) {
    if (elvl.getTimestamp().isBefore(quote.getTimestamp())) {
      BigDecimal bid = quote.getBid();
      BigDecimal ask = quote.getAsk();
      if (bid == null) {
        elvl.setElvl(ask);
      } else {
        elvl.setElvl(calculateElvlByBidAndAsk(elvl.getElvl(), bid, ask));
      }
      elvl.setTimestamp(quote.getTimestamp());
    }
    return elvl;
  }

  private BigDecimal calculateElvlByBidAndAsk(BigDecimal elvl, BigDecimal bid, BigDecimal ask) {
    if (bid.compareTo(elvl) > 0) {
      return bid;
    } else if (ask.compareTo(elvl) < 0) {
      return ask;
    }
    return elvl;
  }

  private Elvl createElvl(Quote quote) {
    BigDecimal bid = quote.getBid();
    BigDecimal ask = quote.getAsk();
    BigDecimal elvl = bid != null ? bid : ask;

    elvlRepository.insertElvl(quote.getIsin(), elvl, quote.getTimestamp());
    return new Elvl(quote.getIsin(), elvl, quote.getTimestamp());
  }
}
