package com.zuhlke.microservices.demo.edge;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class EventWidgetChanged implements Event {
    private final Widget entity;
    private final Date timestamp;

    @JsonCreator
    public EventWidgetChanged(@JsonProperty("entity") Widget entity, @JsonProperty("timestamp") Date timestamp) {
        this.entity = entity;
        this.timestamp = timestamp;
    }

    public Widget getEntity() {
        return entity;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "EventWidgetChanged{" +
                "entity=" + entity +
                ", timestamp=" + timestamp +
                '}';
    }
}

