package com.zuhlke.microservices.demo.monitoring;

import com.zuhlke.microservices.demo.monitoring.MonitoringServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MonitoringServiceApplication.class)
public class MonitoringServiceApplicationTests {

	@Test
	public void contextLoads() {
	}

}
