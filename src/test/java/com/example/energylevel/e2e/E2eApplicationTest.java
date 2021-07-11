package com.example.energylevel.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.energylevel.model.Elvl;
import com.example.energylevel.repository.QuoteRepository;
import com.example.energylevel.service.ElvlService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@DisplayName("End to end application test")
class E2eApplicationTest {

  private final QuoteProducer quoteProducer = new QuoteProducer();

  @Autowired private ElvlService elvlService;
  @Autowired private QuoteRepository quoteRepository;

  @Test
  @DisplayName("Should process data asynchronous way")
  void should_processDataCorrectly() {
    Long limit = 1000L;
    long start = System.nanoTime();

    List<CompletableFuture<Elvl>> futures =
        Stream.generate(quoteProducer::getQuote)
            .limit(limit)
            .map(quote -> elvlService.processQuote(quote))
            .collect(Collectors.toList());

    List<Elvl> elvls = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());

    long duration = (System.nanoTime() - start) / 1_000_000;
    System.out.printf("Processed %d elvls in %10.3f seconds\n", elvls.size(), duration / 1000.0);

    assertThat(quoteRepository.count()).isEqualTo(limit);
  }
}
