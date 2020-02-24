package org.nowpat.producer.nbpapi;

import java.net.URI;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class NbpApiUriFactory {

    @Autowired
    private NbpApiConfiguration nbpApiConfiguration;

    public URI getUri(String... pathParts) {

        UriComponents uc = UriComponentsBuilder.newInstance()
                .scheme(nbpApiConfiguration.getProtocol())
                .host(nbpApiConfiguration.getHost())
                .path(nbpApiConfiguration.getPath())
                .queryParam("format", "json")
                .build()
                .expand(pathParts)
                .encode();
        return uc.toUri();
    }
}
