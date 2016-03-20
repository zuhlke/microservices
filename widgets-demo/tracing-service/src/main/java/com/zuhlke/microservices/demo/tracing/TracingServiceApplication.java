package com.zuhlke.microservices.demo.tracing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

@EnableZipkinStreamServer
@SpringBootApplication
public class TracingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TracingServiceApplication.class, args);
    }
}
