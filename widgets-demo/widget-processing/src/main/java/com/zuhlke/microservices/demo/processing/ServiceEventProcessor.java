package com.zuhlke.microservices.demo.processing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.support.MessageBuilder;

import java.io.IOException;
import java.util.Arrays;

@MessageEndpoint
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ServiceEventProcessor {

    @Autowired
    private Processor messaging;

    @Autowired
    private WidgetRepository widgetRepository;

    @StreamListener(Processor.INPUT)
    public void accept(WidgetTransaction command) throws IOException {

        process(command);
    }

    private void process(WidgetTransaction command) {
        Widget fromWidget = this.widgetRepository.findByName(command.getFrom());
        Widget toWidget = this.widgetRepository.findByName(command.getTo());
        toWidget.add(fromWidget.subtract(command.getCount()));
        this.widgetRepository.save(Arrays.asList(fromWidget, toWidget));
        Event txEvent = new EventCommandProcessed(command);
        Event en1Event = new EventWidgetChanged(fromWidget);
        Event en2Event = new EventWidgetChanged(toWidget);
        messaging.output().send(MessageBuilder.withPayload(txEvent).build());
        messaging.output().send(MessageBuilder.withPayload(en1Event).build());
        messaging.output().send(MessageBuilder.withPayload(en2Event).build());
    }

}

