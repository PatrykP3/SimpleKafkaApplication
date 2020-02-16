package org.nowpat.consumer.repository;

import org.nowpat.dto.CurrencyMeanData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface CurrencyMeanValueRepository extends CrudRepository<CurrencyMeanData, String> {
}
