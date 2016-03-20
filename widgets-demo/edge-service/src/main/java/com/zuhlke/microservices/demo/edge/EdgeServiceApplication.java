package com.zuhlke.microservices.demo.edge;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication
@EnableBinding(Processor.class)
public class EdgeServiceApplication {

    private static Log logger = LogFactory.getLog(EdgeServiceApplication.class);

    @Value("${trace.sampler.percentage}")
    private Double traceSamplerPercentage;

    @Bean
    @RefreshScope
    Sampler sampler() {
        return (Span s) -> (Math.random() < traceSamplerPercentage);
    }

    // Use this for debugging (or if there is no Zipkin server running on port 9411)
    @Bean
    public ZipkinSpanReporter spanCollector() {
        return new ZipkinSpanReporter() {
            @Override
            public void report(zipkin.Span span) {
                logger.info(span);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(EdgeServiceApplication.class, args);
    }

}


@MessageEndpoint
class EdgeEventsProcessor {
    @ServiceActivator(inputChannel = Processor.INPUT)
    public void accept(String e) {
        System.out.println(e);
    }
}

@RestController
@RequestMapping("/widgets")
class EdgeRestController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Processor processor;

    @RequestMapping(method = RequestMethod.POST)
    public void write(@RequestBody EdgeCommand c, @RequestHeader HttpHeaders headers) {
        this.processor.output().send(MessageBuilder.withPayload(c).setHeader("X-Trace-Id", headers.get("X-Trace-Id")).build());
    }

    public Collection<EdgeWidget> getWidgetsFallback() {
        return new ArrayList<>();
    }

    @HystrixCommand(fallbackMethod = "getWidgetsFallback")
    @RequestMapping(method = RequestMethod.GET)
    public Collection<EdgeWidget> getWidgets() {
        return this.restTemplate
                .exchange("http://processing-service/widgets", HttpMethod.GET, null, new ParameterizedTypeReference<Resources<EdgeWidget>>() {
                })
                .getBody()
                .getContent();
    }

    public Collection<String> getWidgetNamesFallback() {
        return new ArrayList<>();
    }

    @HystrixCommand(fallbackMethod = "getWidgetNamesFallback")
    @RequestMapping(method = RequestMethod.GET, value = "/names")
    public Collection<String> getWidgetNames() {
        return this.restTemplate
                .exchange("http://processing-service/widgets", HttpMethod.GET, null, new ParameterizedTypeReference<Resources<EdgeWidget>>() {
                })
                .getBody()
                .getContent()
                .stream()
                .map(EdgeWidget::getName)
                .collect(Collectors.toList());
    }
}

class EdgeCommand {
    public enum ActionType {MOVE}
    private String from;
    private String to;
    private ActionType action;
    private Integer count;

    public EdgeCommand(){}

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public ActionType getAction() {
        return action;
    }

    public Integer getCount() {
        return count;
    }
}

class EdgeWidget {
    private String name;
    private Integer count;

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }
}
