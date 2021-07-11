package com.example.energylevel.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.energylevel.model.Elvl;
import com.example.energylevel.repository.ElvlRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Elvls controller test")
class ElvlsControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ElvlRepository elvlRepository;

  @SneakyThrows
  @Test
  @DisplayName("Should return Elvl by Isin")
  void should_returnElvlByIsin() {
    // Arrange
    elvlRepository.save(
        new Elvl(
            "RU0000000001", BigDecimal.valueOf(55.55), LocalDateTime.of(2021, 10, 10, 10, 10)));
    // Act
    ResultActions result =
        mockMvc.perform(get("/elvls/RU0000000001").accept(MediaType.APPLICATION_JSON));
    // Assert
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.isin").value("RU0000000001"))
        .andExpect(jsonPath("$.elvl").value("55.55"))
        .andExpect(jsonPath("$.timestamp").value("2021-10-10T10:10:00"));
  }

  @SneakyThrows
  @Test
  @DisplayName("Should return error when isin doesn't exist")
  void should_returnError_whenIsinDoesNotExist() {
    // Arrange
    elvlRepository.save(
        new Elvl(
            "RU0000000001", BigDecimal.valueOf(55.55), LocalDateTime.of(2021, 10, 10, 10, 10)));
    // Act
    ResultActions result =
        mockMvc.perform(get("/elvls/RU0000000555").accept(MediaType.APPLICATION_JSON));
    // Assert
    result.andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Should return all Elvls")
  void should_returnAllElvls() throws Exception {
    // Arrange
    elvlRepository.save(
        new Elvl(
            "RU0000000001", BigDecimal.valueOf(55.55), LocalDateTime.of(2021, 10, 10, 10, 10)));
    elvlRepository.save(
        new Elvl(
            "RU0000000002", BigDecimal.valueOf(66.66), LocalDateTime.of(2021, 11, 11, 11, 11)));
    // Act
    ResultActions result = mockMvc.perform(get("/elvls").accept(MediaType.APPLICATION_JSON));
    // Assert
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].isin").value("RU0000000001"))
        .andExpect(jsonPath("$[0].elvl").value("55.55"))
        .andExpect(jsonPath("$[0].timestamp").value("2021-10-10T10:10:00"))
        .andExpect(jsonPath("$[1].isin").value("RU0000000002"))
        .andExpect(jsonPath("$[1].elvl").value("66.66"))
        .andExpect(jsonPath("$[1].timestamp").value("2021-11-11T11:11:00"));
  }

  @AfterEach
  void teardown() {
    elvlRepository.deleteAll();
  }
}
