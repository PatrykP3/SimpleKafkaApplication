package org.nowpat.processor.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties("topics")
@Setter
@Getter
public class TopicsConfiguration {

    private String input;
    private String ttd;
    private String ttsd;
    private String nbp;
    private String output;
}
