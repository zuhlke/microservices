package com.zuhlke.microservices.demo.edge;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class EventCommandProcessed implements Event {
    private final WidgetTransaction command;
    private final Date timestamp;

    @JsonCreator
    public EventCommandProcessed(@JsonProperty("command") WidgetTransaction command, @JsonProperty("timestamp") Date timestamp) {
        this.command = command;
        this.timestamp = timestamp;
    }

    public WidgetTransaction getCommand() {
        return command;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "EventCommandProcessed{" +
                "command=" + command +
                ", timestamp=" + timestamp +
                '}';
    }
}

