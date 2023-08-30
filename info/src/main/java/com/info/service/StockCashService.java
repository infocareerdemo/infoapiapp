package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.entity.StockCash;
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

	public List<StockCash> getStockCashSymbol(String symbol) {
		List<StockCash> stockList =  stockCashRepository.findBySymbol(symbol);
    	return stockList;
		
	}
}
