package com.example.energylevel.repository;

import com.example.energylevel.model.Elvl;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/** Репозиторий для роботы с elvls */
public interface ElvlRepository
    extends JpaRepository<Elvl, String>, PagingAndSortingRepository<Elvl, String> {

  /**
   * Сохраняет elvl в базу данных. В случае конфликта обновляет данные в БЗ
   *
   * @param isin isin инструмента
   * @param elvl Сохраняемый elvl
   * @param timeStamp Временная отметка котировки, на основании которой выполнен расчет
   */
  @Modifying
  @Query(
      value =
          "INSERT INTO elvls (isin, elvl, time_stamp)\n"
              + "VALUES (:isin, :elvl, :timeStamp)\n"
              + "ON CONFLICT (isin)\n"
              + "    DO UPDATE SET elvl       = EXCLUDED.elvl,\n"
              + "                  time_stamp = EXCLUDED.time_stamp\n"
              + "WHERE elvls.time_stamp = :timeStamp",
      nativeQuery = true)
  void insertElvl(String isin, BigDecimal elvl, LocalDateTime timeStamp);
}
