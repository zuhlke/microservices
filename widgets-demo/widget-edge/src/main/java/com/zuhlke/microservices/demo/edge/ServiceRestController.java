package com.zuhlke.microservices.demo.edge;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
class ServiceRestController {
    private static Log logger = LogFactory.getLog(ServiceRestController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private Processor processor;

    @RequestMapping(name = "/transactions", method = RequestMethod.POST)
    public void write(@RequestBody WidgetTransaction c) {
        this.processor.output().send(MessageBuilder.withPayload(c).build());
    }

    public Collection<Widget> getWidgetsFallback() {
        return new ArrayList<>();
    }

    @HystrixCommand(fallbackMethod = "getWidgetsFallback")
    @RequestMapping(value = "/widgets", method = RequestMethod.GET)
    public Collection<Widget> getWidgets() {
        return this.restTemplate
                .exchange("http://widget-processing/widgets", HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Widget>>() {
                })
                .getBody()
                .getContent();
    }

    public Collection<String> getWidgetNamesFallback() {
        return new ArrayList<>();
    }

    @HystrixCommand(fallbackMethod = "getWidgetNamesFallback")
    @RequestMapping(method = RequestMethod.GET, value = "/widgets/names")
    public Collection<String> getWidgetNames() {
        return this.restTemplate
                .exchange("http://widget-processing/widgets", HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Widget>>() {
                })
                .getBody()
                .getContent()
                .stream()
                .map(Widget::getName)
                .collect(Collectors.toList());
    }
}

