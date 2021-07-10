package com.example.energylevel.repository;

import com.example.energylevel.model.Elvl;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ElvlRepository
    extends JpaRepository<Elvl, String>, PagingAndSortingRepository<Elvl, String> {

}
