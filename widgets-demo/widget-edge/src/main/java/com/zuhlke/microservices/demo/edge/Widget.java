package com.zuhlke.microservices.demo.edge;

import java.util.stream.IntStream;

public class Widget {
    public String name;
    public int credits;

    public Widget() {};
    public String getName() {return name;}

    @Override
    public String toString() {
        return "EdgeWidget{" +
                "name='" + name + '\'' +
                ", credits=" + credits +
                '}';
    }
}

