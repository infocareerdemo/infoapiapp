package com.info.restcontroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.info.service.StockFutureService;

@RestController
public class StockFutureController {
	
	 @Autowired
	 StockFutureService stockFutureService;
	
	 @PostMapping("/getStockFutureSymbol")
	 public Map<String, Object> getAllSpsymb(@RequestParam("symbol") String symbol){
		 return stockFutureService.getStockFutureSymbol(symbol);
	 }

}
