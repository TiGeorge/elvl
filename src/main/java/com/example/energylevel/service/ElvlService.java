package com.example.energylevel.service;

import com.example.energylevel.model.Elvl;
import com.example.energylevel.model.Quote;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/** Сервис для роботы с elvl */
public interface ElvlService {

  /**
   * Предоставляет elvl по isin
   *
   * @param isin isin инструмента
   * @return Информация об elvl по данному инструменту
   */
  Optional<Elvl> getElvl(String isin);

  /**
   * Предоставляет перечень всех elvls в виде страниц
   *
   * @param pageNo Номер страницы
   * @param pageSize Количество элементов на странице
   * @return Список elvls
   */
  List<Elvl> getAllElvls(Integer pageNo, Integer pageSize);

  /**
   * Выполняет расчет elvl на основании котировки, работает асинхронно. рассчитывает и сохраняет
   * котировку и elvl в базу данных
   *
   * @param quote Котировка
   * @return Информация об elvl, завернутая в CompletableFuture
   */
  CompletableFuture<Elvl> processQuote(Quote quote);
}
