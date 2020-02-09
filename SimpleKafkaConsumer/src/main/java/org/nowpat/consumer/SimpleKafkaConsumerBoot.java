package org.nowpat.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SimpleKafkaConsumerBoot {
        public static void main(String[] args) {

            SpringApplication.run(SimpleKafkaConsumerBoot.class, args);
        }
    }