package com.info.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.entity.StockFuture;
import com.info.repository.StockFutureRepository;

@Service
public class StockFutureService {
	
	@Autowired
	StockFutureRepository stockFutureRepository;

	public Map<String, Object> getStockFutureSymbol(String symbol) {
	  Map<String, Object> data = new LinkedHashMap<>();
		 
		 if(symbol.equals("") || symbol == null) {
			List<String> symbolList = stockFutureRepository.findAllSymbols();
			  data.put("spSymList", symbolList);
		 }
		else {
			List<StockFuture> stockFutureList =	stockFutureRepository.findBySymbol(symbol);
			 data.put("spList", stockFutureList);
				}
		return data;
	}

}
