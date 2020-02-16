package org.nowpat.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableKafka
@EnableKafkaStreams
@EnableSwagger2
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class SimpleKafkaProcessorBoot {
        public static void main(String[] args) {

            SpringApplication.run(SimpleKafkaProcessorBoot.class, args);
        }
    }