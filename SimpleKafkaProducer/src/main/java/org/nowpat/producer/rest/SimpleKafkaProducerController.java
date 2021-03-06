package org.nowpat.producer.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.nowpat.dto.NbpRates;
import org.nowpat.producer.nbpapi.NbpApiRatesReader;
import org.nowpat.producer.sender.SimpleKafkaProducerSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimpleKafkaProducerController {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Autowired
    NbpApiRatesReader nbpApiRatesReader;

    @Autowired
    SimpleKafkaProducerSender simpleKafkaProducerSender;

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @PostMapping(value = "${paths.collect}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getCurrencyTables(@RequestParam Character currencyTable, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {

        Optional<NbpRates[]> optionalRates = nbpApiRatesReader.getData(currencyTable, dateFrom.format(dateTimeFormatter), dateTo.format(dateTimeFormatter));

        if(optionalRates.isPresent()) {

            ListenableFuture<SendResult<String, NbpRates[]>> result = simpleKafkaProducerSender.send(optionalRates.get());
            try {
                SendResult<String, NbpRates[]> sendResult = result.get(5, TimeUnit.SECONDS);
            }
            catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Data received, but send operation failed.");
            }

            return ResponseEntity.ok("Success");
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No data received.");
        }
    }
}
