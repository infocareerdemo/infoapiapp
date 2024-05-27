package com.info.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.info.entity.StockFuture;
import com.info.entity.StockOption;
import com.info.service.StockOptionService;

@RestController
public class StockOptionController {
	
	@Autowired
	StockOptionService stockOptionService;
	
	 @PostMapping("/getStockOptionSymbol")
	 public Map<String, Object> getStockOptionSymbol(@RequestParam("symbol") String symbol){
		 return stockOptionService.getStockOptionSymbol(symbol);
	 }


}
