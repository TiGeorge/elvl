package com.example.energylevel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.energylevel.model.Elvl;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("ElvlRepository test")
class ElvlRepositoryTest {

  @Autowired private ElvlRepository elvlRepository;

  @Test
  @DisplayName("Should save Elvls correctly")
  void insertElvl_slould_saveElvlsCorrectly() {
    // Act
    elvlRepository.insertElvl(
        "RU0000000001", BigDecimal.TEN, LocalDateTime.of(2021, 10, 10, 10, 10));
    // Assert
    Elvl result = elvlRepository.getById("RU0000000001");
    assertThat(result).isNotNull();
  }

  @AfterEach
  void teardown() {
    elvlRepository.deleteAll();
  }
}
