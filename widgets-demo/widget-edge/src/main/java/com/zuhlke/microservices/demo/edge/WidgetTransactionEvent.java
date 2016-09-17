package com.zuhlke.microservices.demo.edge;

public class WidgetTransactionEvent {
    public WidgetTransaction command;

    public WidgetTransactionEvent() {};

    @Override
    public String toString() {
        return "EdgeEvent{" +
                "  command=" + command +
                '}';
    }
}

