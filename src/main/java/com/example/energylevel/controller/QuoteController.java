package com.example.energylevel.controller;

import com.example.energylevel.service.ElvlService;
import com.example.energylevel.service.QuoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class QuoteController {

  private final QuoteService quoteService;
  private final ElvlService elvlService;


}
