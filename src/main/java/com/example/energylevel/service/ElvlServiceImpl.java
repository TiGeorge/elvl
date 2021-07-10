package com.example.energylevel.service;

import com.example.energylevel.model.Elvl;
import com.example.energylevel.model.Quote;
import com.example.energylevel.repository.ElvlRepository;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ElvlServiceImpl implements ElvlService {

  private final ElvlRepository elvlRepository;

  @Override
  public Optional<Elvl> getElvl(String isin) {
    return elvlRepository.findById(isin);
  }

  @Override
  @Transactional
  public Elvl calculateElvl(Quote quote) {
    return elvlRepository
        .findById(quote.getIsin())
        .map(elvl -> calculateElvl(quote, elvl))
        .orElseGet(() -> createElvl(quote));
  }

  @Override
  public List<Elvl> getAllElvls(Integer pageNo, Integer pageSize, String sortBy) {
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    Page<Elvl> pagedResult = elvlRepository.findAll(paging);
    if (pagedResult.hasContent()) {
      return pagedResult.getContent();
    } else {
      return Collections.emptyList();
    }
  }

  private Elvl calculateElvl(Quote quote, Elvl elvl) {
    if (elvl.getTimestamp().isBefore(quote.getTimestamp())) {
      elvl.setTimestamp(quote.getTimestamp());
      if (quote.getBid() == null) {
        elvl.setElvl(quote.getAsk());
      } else {
        elvl.setElvl(calculateElvlByBidAndAsk(elvl.getElvl(), quote.getBid(), quote.getAsk()));
      }
    }
    return elvl;
  }

  private BigDecimal calculateElvlByBidAndAsk(
      BigDecimal currentElvl, BigDecimal bid, BigDecimal ask) {

    if (bid.compareTo(currentElvl) > 0) {
      return bid;
    } else if (ask.compareTo(currentElvl) < 0) {
      return ask;
    }
    return currentElvl;
  }

  private Elvl createElvl(Quote quote) {
    BigDecimal elvl = quote.getBid() != null ? quote.getBid() : quote.getAsk();
    Elvl newElvl = new Elvl(quote.getIsin(), elvl, quote.getTimestamp());
    return elvlRepository.save(newElvl);
  }
}
