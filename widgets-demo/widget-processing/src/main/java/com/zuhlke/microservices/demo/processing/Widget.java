package com.zuhlke.microservices.demo.processing;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Widget {
    @Id
    @GeneratedValue
    public Long id;
    public String name;
    public String credits;

    public Widget() {}

    public Integer add(Integer amount) {
        for (int i=0; i < amount; i++ ) {
            credits += "*";
        }
        return amount;
    }

    public Integer subtract(Integer amount) {
        if (amount <= credits.length()) {
            credits = credits.substring(0,credits.length()-amount);
        } else {
            amount = 0;
        }
        return amount;
    }

    @Override
    public String toString() {
        return "Widget{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                '}';
    }
}
