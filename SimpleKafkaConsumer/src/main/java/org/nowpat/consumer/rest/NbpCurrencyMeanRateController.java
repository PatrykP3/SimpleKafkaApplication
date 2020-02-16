package org.nowpat.consumer.rest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.nowpat.consumer.repository.NbpCurrencyRateRepository;
import org.nowpat.consumer.repository.NbpCurrencyRateWithDateRepository;
import org.nowpat.dto.NBPCurrencyRate;
import org.nowpat.dto.NBPCurrencyRateWithDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/rest")
public class NbpCurrencyMeanRateController {

    @Autowired
    NbpCurrencyRateWithDateRepository repository;

    @GetMapping(value = "/nbcurrencyprateswithdates", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<NBPCurrencyRateWithDate>> getBetween(@RequestParam String code, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {

        return ResponseEntity.status(HttpStatus.OK).body(repository.findByCodeAndDateBetween(code, dateFrom, dateTo));
    }

    @GetMapping(value = "/nbcurrencyprateswithdatesmeanvalue", produces = MediaType.TEXT_PLAIN_VALUE)
    ResponseEntity<String> getMeanValue(@RequestParam String code, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {

        ArrayList<NBPCurrencyRateWithDate> values = repository.findByCodeAndDateBetween(code, dateFrom, dateTo);
        Double sum = values.stream().mapToDouble(v -> v.getMid()).sum();
        Long count = values.stream().count();
        return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(sum/count));
    }
}
