package com.info.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.entity.StockCash;
import com.info.entity.StockFuture;
import com.info.entity.StockPrice;
import com.info.repository.StockCashRepository;
import com.info.repository.StockPriceRepository;

@Service
public class SearchService {
	
	@Autowired
	StockPriceRepository stockPriceRepository;
	
	@Autowired
	StockCashRepository stockCashRepository;

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

	public List<StockPrice> getUniqueSpSymbol(String symbol) {
		if (symbol != null) {
			return stockPriceRepository.getUniqueSpSymbol(symbol);
		}
		return stockPriceRepository.findAll();
	}
	
	
	//stockprice symbol
	public Map<String, Object> getAllSpsymb(String spSymb) {

        Map<String, Object> data = new LinkedHashMap<>();

        List<StockPrice> sp = stockPriceRepository.findAll();
        List<String> spSym = sp.stream().map(StockPrice::getSpsymbol).distinct().collect(Collectors.toList());

        if (spSymb.equals("") || spSymb == null) {
            data.put("spSymList", spSym);
        } else {
            List<StockPrice> spS = getStockPriceBySpsymbol(spSymb);
            data.put("spList", spS);
        }

        return data;
    }
	
    public List<StockPrice> getStockPriceBySpsymbol(String spSym) {

        List<StockPrice> sp = stockPriceRepository.findBySpsymbol(spSym);
        return sp;
    }


}
