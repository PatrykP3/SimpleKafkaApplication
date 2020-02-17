package org.nowpat.consumer.repository;

import java.util.ArrayList;
import java.util.List;

import org.nowpat.dto.NbpRates;
import org.springframework.stereotype.Component;

@Component
public class NbpRatesRepository {

    private List<NbpRates[]> repository = new ArrayList<>();

    public void add(NbpRates[] rates) {

        repository.add(rates);
    }

    public List<NbpRates[]> getAll() {

        return repository;
    }
}
