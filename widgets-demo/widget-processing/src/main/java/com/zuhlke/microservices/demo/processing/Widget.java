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
    public int credits;

    public Widget() {}

    public Integer add(Integer amount) {
        credits += amount;
        return amount;
    }

    public Integer subtract(Integer amount) {
        if (amount <= credits) {
            credits -= amount;
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
