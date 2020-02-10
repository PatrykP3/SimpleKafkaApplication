package org.nowpat.producer.nbpapi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("nbpapi")
@Setter
@Getter
public class NbpApiConfiguration {

    private String protocol;
    private String host;
    private String path;
}
