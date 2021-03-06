package com.avinash.springboot.microservice;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	@Autowired
	private Environment  enviroment;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExchangeValueRepository repository;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retriveExchangeValue(@PathVariable String from , 
			@PathVariable String to)
	{
		logger.info("from -> {} to  -> {}",from , to);

		//ExchangeValue excahngevalue = new ExchangeValue(1000L, from, to, BigDecimal.valueOf(65));
		ExchangeValue excahngevalue =		repository.findByFromAndTo(from, to);

		excahngevalue.setPort(Integer.parseInt(enviroment.getProperty("local.server.port")));
		return excahngevalue;
	}

}
