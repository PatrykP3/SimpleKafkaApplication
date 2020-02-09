package org.nowpat.processor;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.nowpat.dto.NBPRates;
import org.nowpat.dto.TransportTestData;
import org.nowpat.dto.TransportTestSubData;
import org.nowpat.processor.configuration.TopicsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaStreamBrancher;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class SimpleKafkaStream {

    @Autowired
    TopicsConfiguration topicsConfiguration;

    @Bean
    public KStream<String, Object> kStreamBranches(StreamsBuilder streamsBuilder) {

        KStream<String, Object> kStreamsBuilder = streamsBuilder.stream(topicsConfiguration.getInput());

        KafkaStreamBrancher kafkaStreamBrancher = new KafkaStreamBrancher<String, Object>()
                .branch((key, value) -> value instanceof TransportTestData, ks -> ks.to(topicsConfiguration.getTtd()))
                .branch((key, value) -> value instanceof TransportTestSubData, ks -> ks.to(topicsConfiguration.getTtsd()))
                .branch((key, value) -> value instanceof NBPRates[], ks -> ks.to(topicsConfiguration.getNbp()));

        return kafkaStreamBrancher.onTopOf(kStreamsBuilder);
    }

    @Bean
    public KStream<String, TransportTestData> kStreamTTD(StreamsBuilder streamsBuilder) {

        KStream<String, TransportTestData> kStreamsBuilder = streamsBuilder.stream(topicsConfiguration.getTtd());
        kStreamsBuilder.
                map((key, value) -> KeyValue.pair(key, new TransportTestData( value.getNumericalValue() + 1, "new text value", new TransportTestSubData(((TransportTestData) value).getSubData().getId() + 1, ((TransportTestData) value).getSubData().getName() + " processed")))).
                to(topicsConfiguration.getOutput());

        return kStreamsBuilder;
    }

    @Bean
    public KStream<String, TransportTestSubData> kStreamTTSD(StreamsBuilder streamsBuilder) {

        KStream<String, TransportTestSubData> kStreamsBuilder = streamsBuilder.stream(topicsConfiguration.getTtsd());
        kStreamsBuilder.
                peek((key, value ) -> log.info("value: {}", value.toString())).
                mapValues((value) -> new TransportTestSubData(value.getId() + 1, value.getName() + "processed")).
                peek((key, value ) -> log.info("processed value: {}", value.toString())).
                to(topicsConfiguration.getOutput());

        return kStreamsBuilder;
    }

    @Bean
    public KStream<String, NBPRates[]> kStreamNbp(StreamsBuilder streamsBuilder) {

        KStream<String, NBPRates[]> kStreamsBuilder = streamsBuilder.stream(topicsConfiguration.getNbp());
        kStreamsBuilder.
                peek((key, value ) -> log.info("value: {}", value.toString())).
                to(topicsConfiguration.getOutput());

        return kStreamsBuilder;
    }
}
