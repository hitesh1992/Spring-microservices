package com.app.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.microservices.limitsservice.bean.LimitConfiguration;
import com.app.microservices.limitsservice.config.Configuration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitsConfigurationController {
	
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	@HystrixCommand(fallbackMethod="fallbackRetrieveLimits")
	public LimitConfiguration retrieveLimitsFromConfigurations() {
		return new LimitConfiguration(configuration.getMaximum(), 
				configuration.getMinimum());
	}
	
	@GetMapping("/fault-tolerant-limits")
	@HystrixCommand(fallbackMethod="fallbackRetrieveLimits")
	public LimitConfiguration retrieveLimits() {
		throw new RuntimeException("Not Available");
	}
	
	public LimitConfiguration fallbackRetrieveLimits() {
		return new LimitConfiguration(999,9);
	}

}
