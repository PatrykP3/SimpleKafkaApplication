package org.nowpat.processor.util;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.nowpat.dto.CurrencyMeanData;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;


public class CurrencyMeanValueSerde implements Serde<CurrencyMeanData> {

        final private JsonSerializer serializer = new JsonSerializer<CurrencyMeanData>();
        final private JsonDeserializer deserializer = new JsonDeserializer<CurrencyMeanData>();

        @Override
        public void configure(Map<String, ?> configs, boolean isKey) {
            serializer.configure(configs, isKey);
            deserializer.configure(configs, isKey);
        }

        @Override
        public void close() {
            serializer.close();
            deserializer.close();
        }

        @Override
        public Serializer<CurrencyMeanData> serializer() {
            return serializer;
        }

        @Override
        public Deserializer<CurrencyMeanData> deserializer() {
            return deserializer;
        }
    }
