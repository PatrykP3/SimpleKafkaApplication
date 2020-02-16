package org.nowpat.consumer.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.nowpat.dto.NBPCurrencyRateWithDate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface NbpCurrencyRateWithDateRepository extends CrudRepository<NBPCurrencyRateWithDate, Long> {

    ArrayList<NBPCurrencyRateWithDate> findAll();
    NBPCurrencyRateWithDate findByCodeAndDate(String code, LocalDate date);
    ArrayList<NBPCurrencyRateWithDate> findByCodeAndDateBetween(String code, LocalDate dateFrom, LocalDate dateTo);
}
