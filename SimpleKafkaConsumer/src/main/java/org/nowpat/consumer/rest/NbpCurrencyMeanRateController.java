package org.nowpat.consumer.rest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.nowpat.consumer.repository.NbpCurrencyRateWithDateRepository;
import org.nowpat.dto.NbpCurrencyRateWithDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NbpCurrencyMeanRateController {

    @Autowired
    NbpCurrencyRateWithDateRepository repository;

    @GetMapping(value = "/nbcurrencyprateswithdates", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<NbpCurrencyRateWithDate>> getBetween(@RequestParam String code, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {

        return ResponseEntity.status(HttpStatus.OK).body(repository.findByCodeAndDateBetween(code, dateFrom, dateTo));
    }

    @GetMapping(value = "/nbcurrencyprateswithdatesmeanvalue", produces = MediaType.TEXT_PLAIN_VALUE)
    ResponseEntity<String> getMeanValue(@RequestParam String code, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {

        ArrayList<NbpCurrencyRateWithDate> values = repository.findByCodeAndDateBetween(code, dateFrom, dateTo);
        double sum = values.stream().mapToDouble(v -> v.getMid()).sum();
        int count = values.size();
        return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(sum/count));
    }
}
