package com.zuhlke.microservices.demo.edge;

public class Widget {
    public String name;
    public String credits;

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

