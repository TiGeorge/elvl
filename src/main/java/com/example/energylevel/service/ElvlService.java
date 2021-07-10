package com.example.energylevel.service;

import com.example.energylevel.model.Elvl;
import com.example.energylevel.model.Quote;

public interface ElvlService {

  Elvl calculateElvl(Quote quote);
}
