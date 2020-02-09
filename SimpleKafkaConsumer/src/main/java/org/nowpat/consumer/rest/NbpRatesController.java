package org.nowpat.consumer.rest;

import java.util.List;

import org.nowpat.consumer.repository.NbpRatesRepository;
import org.nowpat.dto.NBPRates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class NbpRatesController {

    @Autowired
    NbpRatesRepository repository;

    @GetMapping(value = "/nbprates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NBPRates[]>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(repository.getAll());
    }
}
