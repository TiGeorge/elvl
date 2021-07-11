package com.example.energylevel.repository;

import com.example.energylevel.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

/** Репозиторий для роботы с котировками */
public interface QuoteRepository extends JpaRepository<Quote, Long> {

}
