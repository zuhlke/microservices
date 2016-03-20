package com.zuhlke.microservices.demo.processing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.IOException;

@EnableDiscoveryClient
@SpringBootApplication
@IntegrationComponentScan
@EnableBinding(Processor.class)
public class ProcessingServiceApplication {

    private static Log logger = LogFactory.getLog(ProcessingServiceApplication.class);

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
		SpringApplication.run(ProcessingServiceApplication.class, args);
	}

}

@MessageEndpoint
class CommandProcessor {

	@Autowired
	private Processor messaging;

	@Autowired
	private WidgetRepository widgetRepository;

	@ServiceActivator(inputChannel = Processor.INPUT)
	public void accept(Message<String> msg) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Command command = mapper.readValue(msg.getPayload(), Command.class);
		Widget fromWidget = this.widgetRepository.findByName(command.getFrom());
		Widget toWidget = this.widgetRepository.findByName(command.getTo());
		fromWidget.subtract(command.getCount());
		toWidget.add(command.getCount());
		this.widgetRepository.save(fromWidget);
		this.widgetRepository.save(toWidget);
		Event event = new Event(command);
		messaging.output().send(MessageBuilder.withPayload(event)
				//.setHeader("X-Trace-Id", msg.getHeaders().get("X-Span-Id"))
				//.setHeader("X-Trace-Id", msg.getHeaders().get("X-Trace-Id"))
				.build());
	}
}

@RepositoryRestResource
interface WidgetRepository extends JpaRepository<Widget, Long> {

	Widget findByName(@Param("name") String name);
}

class Event {
	public Command event;

	public Event(Command command) {
		this.event = command;
	}
}

class Command {
	public enum ActionType {MOVE}
	private String from;
	private String to;
	private ActionType action;
	private Integer count;

	public Command(){}

	public Command(String from, String to, ActionType action, Integer count) {
		this.from = from;
		this.to = to;
		this.action = action;
		this.count = count;
	}

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
@Entity
class Widget {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private Integer count;

	public Widget() {
	}

	public Widget(String name) {
		this.name = name;
		this.count = 0;
	}

	public Widget(String name, Integer count) {
		this.name = name;
		this.count = count;
	}

	public Widget(Long id, String name, Integer count) {
		this.id = id;
		this.name = name;
		this.count = count;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getCount() {
		return count;
	}

	public Integer add(Integer amount) {
		return count += amount;
	}

	public Integer subtract(Integer amount) {
		return count -= amount;
	}

	@Override
	public String toString() {
		return "Widget{" +
				"id=" + id +
				", name='" + name + '\'' +
				", count=" + count +
				'}';
	}
}
