package org.nowpat.producer.nbpapi;

import java.net.URI;
import java.util.Optional;

import org.nowpat.dto.NbpRates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class NbpApiRatesReader {

    private final RestTemplate restTemplate;

    @Autowired
    private NbpApiUriFactory nbpApiUriFactory;

    @Autowired
    public NbpApiRatesReader(RestTemplateBuilder rtb, NbpApiResponseErrorHandler errorHandler) {
        this.restTemplate = rtb.build();
        this.restTemplate.setErrorHandler(errorHandler);
    }


    public Optional<NbpRates[]> getData(Character table, String dateFrom, String dateTo) {

        URI uri = nbpApiUriFactory.getUri(String.valueOf(table), dateFrom, dateTo);
        ResponseEntity<NbpRates[]> entity;

        log.info("query: {}", uri);

        try {
            entity = restTemplate.getForEntity(uri, NbpRates[].class);

        } catch (RestClientException ex) {

            return Optional.empty();
        }

        if (entity.getStatusCode() != HttpStatus.OK) {

            return Optional.empty();
        }

        return Optional.ofNullable(entity.getBody());
    }
}