package com.info.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.entity.StockFuture;
import com.info.entity.StockOption;
import com.info.repository.StockOptionRepository;

@Service
public class StockOptionService {
	
	@Autowired
	StockOptionRepository stockOptionRepository;

	public Map<String, Object> getStockOptionSymbol(String symbol) {
		
      Map<String, Object> data = new LinkedHashMap<>();
		 
		 if(symbol.equals("") || symbol == null) {
			List<String> symbolList = stockOptionRepository.findAllSymbols();
			  data.put("spSymList", symbolList);
		 }
		else {
			List<StockOption> stockOptionList =	stockOptionRepository.findBySymbol(symbol);
			 data.put("spList", stockOptionList);
				}
		return data;
		
	}

}
