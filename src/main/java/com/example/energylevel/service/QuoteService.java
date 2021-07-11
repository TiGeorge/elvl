package com.example.energylevel.service;

import com.example.energylevel.model.Quote;

/** Сервис для роботы с котировками */
public interface QuoteService {

  /**
   * Сохраняет котировку в базу данных
   *
   * @param quote Котировка для обработки
   * @return Обработанная котировка
   */
  Quote saveQuote(Quote quote);
}
