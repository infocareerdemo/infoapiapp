package com.info.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.entity.StockCash;
import com.info.entity.StockFuture;
import com.info.repository.StockCashRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class StockCashService {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Object[]> executeCustomQuery(String sqlQuery) {
		Query query = entityManager.createNativeQuery(sqlQuery);
		return query.getResultList();

	}

	// add by anu

	@Autowired(required = false)
	private StockCashRepository stockCashRepository;

	public List<StockCash> getreliancedata(String symbolname) {

		return stockCashRepository.getAllRelianceData(symbolname);
	}

	public Map<String, Object> getStockCashSymbol(String symbol) {
		Map<String, Object> data = new LinkedHashMap<>();
		 
		 if(symbol.equals("") || symbol == null) {
			List<String> symbolList = stockCashRepository.findAllSymbols();
			  data.put("spSymList", symbolList);
		 }
		else {
			List<StockCash> stockFutureList =	stockCashRepository.findBySymbol(symbol);
			  data.put("spList", stockFutureList);
				}
		return data;
	}
		
}

