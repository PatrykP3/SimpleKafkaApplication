package org.nowpat.consumer.repository;

import java.util.ArrayList;
import java.util.List;

import org.nowpat.dto.NBPRates;
import org.springframework.stereotype.Component;

@Component
public class NbpRatesRepository {

    private List<NBPRates[]> repository = new ArrayList<>();

    public void add(NBPRates[] rates) {

        repository.add(rates);
    }

    public List<NBPRates[]> getAll() {

        return repository;
    }
}
