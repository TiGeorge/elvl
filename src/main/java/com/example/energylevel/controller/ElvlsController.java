package com.example.energylevel.controller;

import com.example.energylevel.model.Elvl;
import com.example.energylevel.service.ElvlService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/elvls")
public class ElvlsController {

  private final ElvlService elvlService;

  @GetMapping("/{isin}")
  public Elvl getElvl(@PathVariable("isin") String isin) {
    return elvlService
        .getElvl(isin)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Isin Not Found", null));
  }

  @GetMapping
  public List<Elvl> getAllElvls(Integer pageNo, Integer pageSize, String sortBy) {
    return elvlService.getAllElvls(pageNo, pageSize, sortBy);
  }
}
