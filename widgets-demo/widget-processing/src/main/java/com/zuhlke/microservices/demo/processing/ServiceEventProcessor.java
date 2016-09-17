package com.zuhlke.microservices.demo.processing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.support.MessageBuilder;

import java.io.IOException;

@MessageEndpoint
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ServiceEventProcessor {

    @Autowired
    private Processor messaging;

    @Autowired
    private WidgetRepository widgetRepository;

    @StreamListener(Processor.INPUT)
    public void accept(WidgetTransaction command) throws IOException {
        Widget fromWidget = this.widgetRepository.findByName(command.from);
        Widget toWidget = this.widgetRepository.findByName(command.to);
        toWidget.add(fromWidget.subtract(command.count));
        this.widgetRepository.save(fromWidget);
        this.widgetRepository.save(toWidget);
        messaging.output().send(MessageBuilder.withPayload(new WidgetTransactionEvent(command))
                .build());
    }
}

