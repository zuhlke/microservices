package com.zuhlke.microservices.demo.edge;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.MessageEndpoint;

@MessageEndpoint
class ServiceEventProcessor {
    private static Log logger = LogFactory.getLog(ServiceEventProcessor.class);

    @StreamListener(Processor.INPUT)
    public void accept(Event event) {
        logger.info(event);
    }
}
