package org.nowpat.producer.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
//@ConfigurationProperties(prefix = "kafka")
@Setter
@Getter
public class SimpleKafkaProducerHostConfiguration {

    private String host;
    private int port;
}
