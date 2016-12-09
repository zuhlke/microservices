package com.zuhlke.microservices.demo.processing;

import java.util.Date;

public class EventCommandProcessed implements Event {
    private final WidgetTransaction command;
    private final Date timestamp;

    public EventCommandProcessed(WidgetTransaction command) {
        this.command = command;
        timestamp = new Date();
    }

    public WidgetTransaction getCommand() {
        return command;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
