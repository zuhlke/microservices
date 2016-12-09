package com.zuhlke.microservices.demo.processing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EventCommandProcessed.class, name = "processed"),
        @JsonSubTypes.Type(value = EventWidgetChanged.class, name = "changed")
})
public interface Event {
}
