package org.nowpat.consumer.repository;

import org.nowpat.dto.NBPCurrencyRate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NbpCurrencyRateRepository {

    private List<NBPCurrencyRate> repository = new ArrayList<>();

    public void add(NBPCurrencyRate rate) {
        repository.add(rate);
    }

    public List<NBPCurrencyRate> getAll() {

        return repository;
    }
}
