package com.zuhlke.microservices.demo.processing;

public class WidgetTransaction {
    private String from;
    private String to;
    private Integer count;

    public WidgetTransaction(){}

    public WidgetTransaction(String from, String to, Integer count) {
        this.from = from;
        this.to = to;
        this.count = count;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Integer getCount() {
        return count;
    }
}
