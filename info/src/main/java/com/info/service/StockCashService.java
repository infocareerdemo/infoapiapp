package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.entity.StockCash;
import com.info.repository.StockCashRepository;

@Service
public class StockCashService {
	
	
	//add by anu 
	
	@Autowired(required = false)
	private StockCashRepository stockCashRepository;
	

	public List<StockCash> getreliancedata(String symbolname) {
		
		return stockCashRepository.getAllRelianceData(symbolname);
	}
	
	
}
