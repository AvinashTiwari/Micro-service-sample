package com.avinash.springboot.microservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from , @PathVariable String to,
			                                      @PathVariable BigDecimal quantity)
	{
		Map<String, String> uriVariable = new HashMap();
		uriVariable.put("from", from);
		uriVariable.put("to", to);

		ResponseEntity<CurrencyConversionBean> forEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversionBean.class, uriVariable);
		
		CurrencyConversionBean resposne = forEntity.getBody();
		
		return new CurrencyConversionBean(resposne.getId(),
				from , to ,quantity, resposne.getConversionMultiple() ,
				quantity.multiply(resposne.getConversionMultiple()),
				resposne.getPort() );
		
	}

	@Autowired
	private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from , @PathVariable String to,
			                                      @PathVariable BigDecimal quantity)
	{
		
		/*
		Map<String, String> uriVariable = new HashMap();
		uriVariable.put("from", from);
		uriVariable.put("to", to);

		ResponseEntity<CurrencyConversionBean> forEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversionBean.class, uriVariable);
		*/
		CurrencyConversionBean resposne = currencyExchangeServiceProxy.retriveExchangeValue(from, to);
		
		return new CurrencyConversionBean(resposne.getId(),
				from , to ,quantity, resposne.getConversionMultiple() ,
				quantity.multiply(resposne.getConversionMultiple()),
				resposne.getPort() );
		
	}
	
}
