package org.nowpat.consumer.repository;

import java.time.LocalDate;
import java.util.ArrayList;

import org.nowpat.dto.NbpCurrencyRateWithDate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface NbpCurrencyRateWithDateRepository extends CrudRepository<NbpCurrencyRateWithDate, Long> {

    ArrayList<NbpCurrencyRateWithDate> findAll();
    NbpCurrencyRateWithDate findByCodeAndDate(String code, LocalDate date);
    ArrayList<NbpCurrencyRateWithDate> findByCodeAndDateBetween(String code, LocalDate dateFrom, LocalDate dateTo);
}
