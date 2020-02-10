package org.nowpat.producer.sender;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.nowpat.dto.NBPRates;
import org.springframework.kafka.support.ProducerListener;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class SimpleKafkaProducerListener implements ProducerListener<String, NBPRates> {

    @Override
    public void onSuccess(String topic, Integer partition, String key, NBPRates value, RecordMetadata recordMetadata) {
        log.info("sucessfull send operation in topic {}, key {}, data {}", topic, key, value);
    }

    @Override
    public void onError(String topic, Integer partition, String key, NBPRates value, Exception exception) {
        log.info("error sending in topic {}, exception {}", topic, exception);
    }
}
