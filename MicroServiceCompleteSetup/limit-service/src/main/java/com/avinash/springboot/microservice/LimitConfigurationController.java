package com.avinash.springboot.microservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avinash.springboot.microservice.bean.LimitConfiguration;
import com.avinash.springboot.microservice.component.ConfigurationApplicationProp;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitConfigurationController {

	@Autowired
	private ConfigurationApplicationProp configurationApplicationProp;
	
	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitFromConfigurations() {
		return new LimitConfiguration(configurationApplicationProp.getMaximum(),
				configurationApplicationProp.getMinimum());
	}
	
	@GetMapping("/fault-tolerance-example")
	@HystrixCommand(fallbackMethod = "fallbackRetriveConfiguration")
	public LimitConfiguration retriveConfiguration()
	{
		throw new RuntimeException("Not available");
	}
	
	public LimitConfiguration fallbackRetriveConfiguration()
	{
		return new LimitConfiguration(9,999); 
	}
	
	
}
