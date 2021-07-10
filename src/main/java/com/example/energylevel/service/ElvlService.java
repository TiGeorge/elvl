package com.example.energylevel.service;

import com.example.energylevel.model.Elvl;
import com.example.energylevel.model.Quote;
import java.util.List;
import java.util.Optional;

public interface ElvlService {

  Optional<Elvl> getElvl(String isin);

  List<Elvl> getAllElvls(Integer pageNo, Integer pageSize);

  Elvl calculateElvl(Quote quote);
}
