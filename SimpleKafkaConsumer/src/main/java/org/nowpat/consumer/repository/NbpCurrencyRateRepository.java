package org.nowpat.consumer.repository;

import org.nowpat.dto.NbpCurrencyRate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NbpCurrencyRateRepository {

    private List<NbpCurrencyRate> repository = new ArrayList<>();

    public void add(NbpCurrencyRate rate) {
        repository.add(rate);
    }

    public List<NbpCurrencyRate> getAll() {

        return repository;
    }
}
