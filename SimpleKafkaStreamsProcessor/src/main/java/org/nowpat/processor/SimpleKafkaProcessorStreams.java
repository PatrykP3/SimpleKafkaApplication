package org.nowpat.processor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Aggregator;
import org.apache.kafka.streams.kstream.Initializer;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.nowpat.dto.*;
import org.nowpat.processor.configuration.TopicsConfiguration;
import org.nowpat.processor.util.CurrencyMeanValueSerde;
import org.nowpat.processor.util.UniversalDataSerde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaStreamBrancher;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class SimpleKafkaProcessorStreams {

    @Autowired
    TopicsConfiguration topicsConfiguration;

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Bean
    public KStream<String, Object> kStreamBranches(StreamsBuilder streamsBuilder) {

        KStream<String, Object> kStreamsBuilder = streamsBuilder.stream(topicsConfiguration.getInput());

        KafkaStreamBrancher kafkaStreamBrancher = new KafkaStreamBrancher<String, Object>()
                .branch((key, value) -> value instanceof TransportTestData, ks -> ks.to(topicsConfiguration.getTtd()))
                .branch((key, value) -> value instanceof TransportTestSubData, ks -> ks.to(topicsConfiguration.getTtsd()))
                .branch((key, value) -> value instanceof NbpRates[], ks -> ks.to(topicsConfiguration.getNbp()));

        return kafkaStreamBrancher.onTopOf(kStreamsBuilder);
    }

    @Bean
    public KStream<String, TransportTestData> kStreamTTD(StreamsBuilder streamsBuilder) {

        KStream<String, TransportTestData> kStreamsBuilder = streamsBuilder.stream(topicsConfiguration.getTtd());
        kStreamsBuilder.
                map((key, value) -> KeyValue.pair(key, new TransportTestData( value.getNumericalValue() + 1, "new text value", new TransportTestSubData(value.getSubData().getId() + 1, value.getSubData().getName() + " processed")))).
                to(topicsConfiguration.getOutput());

        return kStreamsBuilder;
    }

    @Bean
    public KStream<String, TransportTestSubData> kStreamTTSD(StreamsBuilder streamsBuilder) {

        KStream<String, TransportTestSubData> kStreamsBuilder = streamsBuilder.stream(topicsConfiguration.getTtsd());
        kStreamsBuilder.
                peek((key, value ) -> log.info("value: {}", value.toString())).
                mapValues((value) -> new TransportTestSubData(value.getId() + 1, value.getName() + " processed")).
                peek((key, value ) -> log.info("processed value: {}", value.toString())).
                to(topicsConfiguration.getOutput());

        return kStreamsBuilder;
    }

    @Bean
    public KStream<String, NbpRates[]> kStreamNbp(StreamsBuilder streamsBuilder) {

        KStream<String, NbpRates[]> kStreamsBuilder = streamsBuilder.stream(topicsConfiguration.getNbp());

        kStreamsBuilder.    // output: not processed data - array of NbpRates
                peek((key, value ) -> log.info("value: {}", value.toString())).
                to(topicsConfiguration.getOutput());

        kStreamsBuilder.    //output: "currency code.date" as a key, NbpCurrencyRate as a value
                flatMapValues((value) -> Arrays.asList(value)).
                selectKey((key, value) -> value.getEffectiveDate().format(formatter)).
                flatMapValues((value) -> value.getRates()).
                selectKey((key, value) -> value.getCode() + "." + key).
                peek((key, value ) -> log.info("key: {}, value: {}", key, value.toString())).
                to(topicsConfiguration.getOutput());

        kStreamsBuilder.    // output: currency code as a key, DateValue of date and currency value as a value - not finished
                flatMapValues((value) -> Arrays.asList(value)).
                selectKey((key, value) -> value.getEffectiveDate().format(formatter)).
                peek((key, value ) -> log.info("step 1 key: {}, value: {}", key, value.toString())).
                flatMapValues(NbpRates::getRates).
                peek((key, value ) -> log.info("step 2 key: {}, value: {}", key, value.toString())).
                map((key, value) -> KeyValue.pair(value.getCode(), new DateValue(value.getTable(), LocalDate.parse(key, formatter), value.getMid()))). //key: currency code, value: date and currency value
                peek((key, value ) -> log.info("step 3 key : {}, value: {}", key, value.toString())).
                to(topicsConfiguration.getOutput());

//        kStreamsBuilder.  // output: currency code as a key, time range and calculated mean as a value
//                flatMapValues((value) -> Arrays.asList(value)).
//                selectKey((key, value) -> value.getEffectiveDate().format(formatter)).
//                peek((key, value ) -> log.info("1 key: {}, value: {}", key, value.toString())).
//                flatMapValues((value) -> value.getRates()).
//                peek((key, value ) -> log.info("2 key: {}, value: {}", key, value.toString())).
//                map((key, value) -> KeyValue.pair(value.getCode(), new DateValue(value.getTable(), LocalDate.parse(key, formatter), value.getMid()))). //key: currency code, value: date and currency value
//                peek((key, value ) -> log.info("3 key : {}, value: {}", key, value.toString())).
//                groupByKey().
//                aggregate(new CurrencyDataItializer(), new CurrencyDataAggregator(), Materialized.with(new Serdes.StringSerde(), new CurrencyMeanValueSerde())).
//                toStream().
//                peek((key, value ) -> log.info("4 key: {}, value: {}", key, value.toString())).
//                to(topicsConfiguration.getOutput());

        return kStreamsBuilder;
    }

//    private class CurrencyDataItializer implements Initializer {
//
//        @Override
//        public CurrencyMeanData apply() {
//            return new CurrencyMeanData(null, null, null, null, 0, 0);
//        }
//    }
//
//    private class CurrencyDataAggregator implements Aggregator<String, DateValue, CurrencyMeanData> {
//
//        @Override
//        public CurrencyMeanData apply(String key, DateValue value, CurrencyMeanData result) {
//
//            result.setCode(key);
//
//            LocalDate incomingDate = value.getDate();
//            if(result.getDateFrom() == null) {
//                result.setDateFrom(incomingDate);
//            }
//            else {
//                if (incomingDate.isBefore(result.getDateFrom())) {
//                    result.setDateFrom(incomingDate);
//                }
//            }
//
//            if(result.getDateTo() == null) {
//                result.setDateTo(incomingDate);
//            }
//            else {
//                if (incomingDate.isAfter(result.getDateTo())) {
//                    result.setDateTo(incomingDate);
//                }
//            }
//
//            result.setCount(result.getCount() + 1);
//            if(value.getValue() != null) {
//                result.setSum(result.getSum() + value.getValue());
//            }
//            return result;
//        }
//    }
}
