package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.entity.StockPrice;
import com.info.repository.StockPriceRepository;

@Service
public class SearchService {
	
	@Autowired
	StockPriceRepository stockPriceRepository;

	public List<StockPrice> searchList(String keyword) {
		if (keyword != null) {
			return stockPriceRepository.searchList(keyword);
		}
		return stockPriceRepository.findAll();

	}
	
	public List<StockPrice> searchSymbol(String symbol) {
		if (symbol != null) {
			return stockPriceRepository.searchSymbol(symbol);
		}
		return stockPriceRepository.findAll();

	}
}
