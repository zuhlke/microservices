package com.zuhlke.microservices.demo.processing;

public class WidgetTransactionEvent {
    public WidgetTransaction command;

    public WidgetTransactionEvent() {}

    public WidgetTransactionEvent(WidgetTransaction command) {this.command = command;}
}
