package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.info.entity.StockPrice;
import com.info.repository.StockPriceRepository;

@Service
public class StockPriceService {

	@Autowired(required = false)
	private StockPriceRepository stockPriceRepository;

	public StockPrice listAllStockPrices(int spid) {
		return stockPriceRepository.listAllStockPrices(spid);
	}

	public StockPrice stockPricesByName(String spName) {
		return stockPriceRepository.getStockPricesByName(spName);
	}

	public List<StockPrice> listAllStockPricesUsingObjectDatatype() {
		return stockPriceRepository.listAllStockPricesObjectDatatype();
	}

	public List<StockPrice> getAllStockPrice(int pgNo, int pgSize) {

		Pageable pageable = PageRequest.of(pgNo, pgSize);
		Page<StockPrice> stk = stockPriceRepository.findAll(pageable);
		return stk.getContent();

	}

	public List<StockPrice> getAllStockPriceByTdclose(int pgNo, int pgSize, int tdclose) {

		Pageable pageable = PageRequest.of(pgNo, pgSize);
		Slice<StockPrice> stk = stockPriceRepository.findAllByTdcloseLesserThen(tdclose, pageable);
		List<StockPrice> tdCls = stk.getContent();
		return tdCls;

	}

}