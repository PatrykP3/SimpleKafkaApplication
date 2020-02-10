package org.nowpat.producer.rest;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.nowpat.dto.NBPRates;
import org.nowpat.producer.nbpapi.NbpApiRatesReader;
import org.nowpat.producer.sender.SimpleKafkaProducerSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class TestController {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Autowired
    NbpApiRatesReader nbpApiRatesReader;

    @Autowired
    SimpleKafkaProducerSender simpleKafkaProducerSender;

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> getCurrencyTables(String currencyTable, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
//    public ResponseEntity<String> getCurrencyTables(String currencyTable, LocalDate dateFrom, LocalDate dateTo) {
    public ResponseEntity<String> getCurrencyTables(String currencyTable, String dateFrom, String dateTo) {

//        Optional<NBPRates> optionalRates = nbpApiRatesReader.getData(currencyTable, dateFrom.format(dateTimeFormatter), dateTo.format(dateTimeFormatter));
        Optional<NBPRates[]> optionalRates = nbpApiRatesReader.getData(currencyTable, dateFrom, dateTo);

        if(optionalRates.isPresent()) {

            ListenableFuture<SendResult<String, NBPRates[]>> result = simpleKafkaProducerSender.send(optionalRates.get());
            try {
                SendResult<String, NBPRates[]> sendResult = result.get(5, TimeUnit.SECONDS);
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
