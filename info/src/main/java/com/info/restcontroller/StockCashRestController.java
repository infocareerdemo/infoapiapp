package com.info.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.info.entity.StockCash;
import com.info.service.StockCashService;

@RestController
@RequestMapping("/data")
public class StockCashRestController {
	@Autowired
	private StockCashService service;
	
	
	  @GetMapping("/reliancedata") 
	  public List<StockCash> getreliancedata(@RequestParam String symbolname) {
		  return service.getreliancedata(symbolname); 
		  }	 

}
