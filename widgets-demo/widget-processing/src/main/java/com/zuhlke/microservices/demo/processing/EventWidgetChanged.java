package com.zuhlke.microservices.demo.processing;

import java.util.Date;

public class EventWidgetChanged implements Event {
    private final Widget entity;
    private final Date timestamp;

    public EventWidgetChanged(Widget entity) {this.entity = entity; timestamp = new Date();}

    public Widget getEntity() {
        return entity;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}

