package org.nowpat.producer;

import org.nowpat.dto.TransportTestData;
import org.nowpat.dto.TransportTestSubData;
import org.nowpat.producer.sender.SimpleKafkaProducerSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SimpleKafkaProducerRun implements CommandLineRunner {

    @Autowired
    SimpleKafkaProducerSender simpleKafkaProducerSender;

    public void run(String... args) throws Exception {

        log.info("Application is running.");
        simpleKafkaProducerSender.send(new TransportTestData(10, "string2", new TransportTestSubData(1L, "name 1")));
        simpleKafkaProducerSender.send(new TransportTestData(0, "string2", new TransportTestSubData(2L, "name 2")));
        simpleKafkaProducerSender.send(new TransportTestSubData(3L, "subname 3"));
    }
}
