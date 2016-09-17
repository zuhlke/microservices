package com.zuhlke.microservices.demo.edge;

public class WidgetTransaction {
    public String from;
    public String to;
    public Integer count;
    public WidgetTransaction() {};

    @Override
    public String toString() {
        return "EdgeCommand{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", count=" + count +
                '}';
    }
}


