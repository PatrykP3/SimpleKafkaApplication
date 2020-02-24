package org.nowpat.processor.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.nowpat.dto.CurrencyMeanData;
import org.nowpat.processor.util.DeserializationExceptionHandler;
import org.nowpat.processor.util.UniversalDataSerde;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ProcessorConfiguration {

    @Bean
    KafkaStreamsConfiguration defaultKafkaStreamsConfig(KafkaProperties kafkaProperties) {

        Map<String, Object> serdeProps = new HashMap<>();

        Map<String, Object> config = new HashMap<>();
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "processor");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, UniversalDataSerde.class);
        config.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, DeserializationExceptionHandler.class);
        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "org.nowpat.dto,org.apache.commons.lang3.tuple");
        config.put(JsonDeserializer.KEY_DEFAULT_TYPE, String.class);
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, CurrencyMeanData.class);

        return new KafkaStreamsConfiguration(config);
    }
}
