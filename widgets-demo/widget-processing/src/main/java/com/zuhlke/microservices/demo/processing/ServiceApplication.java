package com.zuhlke.microservices.demo.processing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@IntegrationComponentScan
@EnableBinding(Processor.class)
public class ServiceApplication {

    private static Log logger = LogFactory.getLog(ServiceApplication.class);

	@Value("${trace.sampler.percentage}")
	private Double traceSamplerPercentage;

	@Bean
	@RefreshScope
	Sampler sampler() {
		return (Span s) -> (Math.random() < traceSamplerPercentage);
	}

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

}
