package com.example.energylevel.controller;

import com.example.energylevel.model.Elvl;
import com.example.energylevel.service.ElvlService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/** Контроллер для работы с elvls */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/elvls")
public class ElvlsController {

  private final ElvlService elvlService;

  /**
   * Предоставляет elvl по isin
   *
   * @param isin isin инструмента
   * @return Информация об elvl по данному инструменту
   */
  @GetMapping("/{isin}")
  public Elvl getElvl(@PathVariable("isin") String isin) {
    return elvlService
        .getElvl(isin)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Isin Not Found", null));
  }

  /**
   * Предоставляет перечень всех elvls в виде страниц
   *
   * @param pageNo Номер страницы
   * @param pageSize Количество элементов на странице
   * @return Список elvls
   */
  @GetMapping
  public List<Elvl> getAllElvls(
      @RequestParam(defaultValue = "0") int pageNo,
      @RequestParam(defaultValue = "10") int pageSize) {

    return elvlService.getAllElvls(pageNo, pageSize);
  }
}
