package org.nowpat.producer.sender;

import org.nowpat.dto.NBPRates;
import org.nowpat.dto.TransportTestData;
import org.nowpat.dto.TransportTestSubData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SimpleKafkaProducerSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplateString;

    @Autowired
    private KafkaTemplate<String, TransportTestData> kafkaTemplateTTD;

    @Autowired
    private KafkaTemplate<String, TransportTestSubData> kafkaTemplateTTSD;

    @Autowired
    private KafkaTemplate<String, NBPRates[]> kafkaTemplateNbpR;

//    @Autowired
//    private SimpleKafkaProducerListener producerTTDListener;

    public void send(String s) {

        ListenableFuture<SendResult<String, String>> sendResult = kafkaTemplateString.send("test_topic", s);
        sendResult.addCallback(new SuccessCallbackString());
        log.info("Record: {}", s);
    }

    public void send(TransportTestData ttd) {

        ProducerListener<String, TransportTestData> x;
        ListenableFuture<SendResult<String, TransportTestData>> sendResult = kafkaTemplateTTD.send("test_topic", ttd);
        sendResult.addCallback(new SuccessCallbackTTD());
        log.info("Record: {}", ttd.toString());
    }

    public void send(TransportTestSubData ttsd) {

        ProducerListener<String, TransportTestSubData> x;
        ListenableFuture<SendResult<String, TransportTestSubData>> sendResult = kafkaTemplateTTSD.send("test_topic", ttsd);
        sendResult.addCallback(new SuccessCallbackTTD());
        log.info("Record: {}", ttsd.toString());
    }

    public ListenableFuture<SendResult<String, NBPRates[]>> send(NBPRates[] nbpRates) {

        ProducerListener<String, NBPRates[]> x;
//        kafkaTemplateNbpR.setProducerListener(producerTTDListener);
        log.info("Sending record: {}", nbpRates.toString());
        ListenableFuture<SendResult<String, NBPRates[]>> sendResult = kafkaTemplateNbpR.send("test_topic", nbpRates);
        return sendResult;
    }
}
