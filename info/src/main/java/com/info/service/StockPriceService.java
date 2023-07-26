package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.entity.StockPrice;
import com.info.repository.StockPriceRepository;

@Service
public class StockPriceService {
	
	@Autowired(required = false)
	private StockPriceRepository stockPriceRepository;
	
	public StockPrice listAllStockPrices(int spid){
		return stockPriceRepository.listAllStockPrices(spid);
	}
	
	public StockPrice stockPricesByName(String spName){
		return stockPriceRepository.getStockPricesByName(spName);
	}
	
	public List<StockPrice> listAllStockPricesUsingObjectDatatype(){
		return stockPriceRepository.listAllStockPricesObjectDatatype();
	}

	
}