package org.nowpat.producer.nbpapi;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NbpApiResponseLoggingErrorHandler implements NbpApiResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {

        if (clientHttpResponse.getStatusCode() != HttpStatus.OK) {

            return true;
        }
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

        log.error("Error!");
        log.error(clientHttpResponse.getStatusText());
        if(clientHttpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            log.error("Today's data may not be available");
        }
    }
}
