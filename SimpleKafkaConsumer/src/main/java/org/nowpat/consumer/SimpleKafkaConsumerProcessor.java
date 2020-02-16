package org.nowpat.consumer;

import java.util.Arrays;

import org.nowpat.consumer.repository.*;
import org.nowpat.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@KafkaListener(topics = "test_topic_processed")
public class SimpleKafkaConsumerProcessor {

    @Autowired
    private TtsdRepository ttsdRepository;

    @Autowired
    private NbpRatesRepository nbpRatesRepository;

    @Autowired
    private NbpCurrencyRateRepository nbpCurrencyRateRepository;

    @Autowired
    private NbpCurrencyRateWithDateRepository nbpCurrencyRateWithDateRepository;

    @Autowired
    private CurrencyMeanValueRepository currencyMeanValueRepository;

    @KafkaHandler
    public void listen(String payload) {
        log.info("Record: {}", payload);
    }

    @KafkaHandler
    public void listen(TransportTestSubData ttsd) {
        log.info("Record (subdata): {}, {}", ttsd.getId(), ttsd.getName());
        ttsdRepository.add(ttsd);
    }

    @KafkaHandler
    public void listen(TransportTestData ttd) {
        log.info("Record: {}, {}, subdata: {}, {}", ttd.getNumericalValue(), ttd.getTextValue(), ttd.getSubData().getId(), ttd.getSubData().getName());
    }

    @KafkaHandler
    public void listen(NBPRates[] nbpRates) {
        log.info("Record number: {}", nbpRates.length);
        Arrays.stream(nbpRates).forEach(nbpRate ->  log.info("Record: {}", nbpRate.toString()));
        nbpRatesRepository.add(nbpRates);
    }

    @KafkaHandler
    public void listen(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Payload NBPCurrencyRate currencyRate) {
        log.info("Record: key {}, value {}", key, currencyRate);
        nbpCurrencyRateRepository.add(currencyRate);
    }

    @KafkaHandler
    public void listen(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Payload DateValue currencyRate) {
        log.info("Record: key {}, value {}", key, currencyRate);
        NBPCurrencyRateWithDate x = nbpCurrencyRateWithDateRepository.findByCodeAndDate(key, currencyRate.getDate());
        if(nbpCurrencyRateWithDateRepository.findByCodeAndDate(key, currencyRate.getDate()) == null) {
            nbpCurrencyRateWithDateRepository.save(new NBPCurrencyRateWithDate(0L, key, currencyRate.getDate(), currencyRate.getValue()));
        }
    }

    @KafkaHandler
    public void listen(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, @Payload CurrencyMeanData currencyMeanData) {
        log.info("Record: key {}, value {}", key, currencyMeanData);
        currencyMeanValueRepository.save(currencyMeanData);
    }
}
