package org.nowpat.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EntityScan("org.nowpat.dto")
public class SimpleKafkaConsumerBoot {
        public static void main(String[] args) {

            SpringApplication.run(SimpleKafkaConsumerBoot.class, args);
        }
    }
