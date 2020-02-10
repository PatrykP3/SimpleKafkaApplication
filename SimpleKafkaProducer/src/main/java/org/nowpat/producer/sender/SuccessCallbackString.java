package org.nowpat.producer.sender;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SuccessCallbackString implements ListenableFutureCallback<SendResult> {

    @Override
    public void onSuccess(SendResult sendResult) {
        log.info("success: {}" ,sendResult.getProducerRecord().value().toString());
    }

    @Override
    public void onFailure(Throwable throwable) {
        log.info("failure: {}", throwable.getLocalizedMessage());
    }
}
