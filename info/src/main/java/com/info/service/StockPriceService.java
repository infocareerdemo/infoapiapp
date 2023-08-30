package com.info.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.info.entity.StockCash;
import com.info.entity.StockPrice;
import com.info.repository.StockCashRepository;
import com.info.repository.StockPriceRepository;

@Service
public class StockPriceService {

	@Autowired(required = false)
	private StockPriceRepository stockPriceRepository;

	@Autowired
	StockCashRepository stockCashRepository;

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

//	public StockPrice updateStock(String symbol, String series, String timestamp) {
//		StockPrice stockPrice = null;
//		StockCash stockCash = stockCashRepository.findBySymbolAndSeriesAndTimestamp(symbol, series, timestamp);
//		if (stockCash != null) {
//			stockPrice = stockPriceRepository.findBySpsymbolAndSpinstrument(stockCash.getSymbol(),
//					stockCash.getSeries());
//			Date date = new Date();
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//			String strDate = formatter.format(date);
//			formatter = new SimpleDateFormat("dd-MMM-yyyy");
//			String date1 = formatter.format(date);
//			if (stockPrice != null) {
//				stockPrice.setTdopen(stockCash.getOpen());
//				stockPrice.setTdclose(stockCash.getClose());
//				stockPrice.setTdlow(stockCash.getLow());
//				stockPrice.setTdhigh(stockCash.getHigh());
//				stockPrice.setTdtimestamp(date1);
//				stockPrice.setSlastupdatedate(strDate);
//				stockPriceRepository.save(stockPrice);
//			}
//		}
//		return stockPrice;
//	}

	public String updateStock(String series, String timestamp) {
		List<StockPrice> stockPrices = new ArrayList<>();

		List<StockCash> stockCashs = stockCashRepository.findBySeriesAndTimestamp(series, timestamp);
		List<StockPrice> stPrices = stockPriceRepository.findBySpinstrument(series);

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter = new SimpleDateFormat("dd-MMM-yyyy");
		String date1 = formatter.format(date);

		if (!stockCashs.isEmpty()) {

			for (StockCash stc : stockCashs) {
				Optional<StockPrice> stList = stPrices.stream().filter(sp -> sp.getSpsymbol().equalsIgnoreCase(stc.getSymbol())).findFirst();
				if (stList.isPresent()) {

					stList.get().setTdopen(stc.getOpen());
					stList.get().setTdclose(stc.getClose());
					stList.get().setTdlow(stc.getLow());
					stList.get().setTdhigh(stc.getHigh());
					stList.get().setTdtimestamp(stc.getTimestamp());
					stList.get().setSlastupdatedate(new Date());
					System.out.println("sp added");
					stockPrices.add(stList.get());
					
				}
			}
			stockPriceRepository.saveAll(stockPrices);
			System.out.println("sp saved");
			
		}

		return "Updated";
	}

	
	public List<StockPrice> getAllStockPriceList() {
        return stockPriceRepository.findAll(); 
    }

//	public List<StockPrice> getUniqueSpSymbols(String spsymbol) {
//		// TODO Auto-generated method stub
//		return stockPriceRepository.getUniqueSpSymbol(spsymbol);
//	}

//	public StockPrice getUniqueSpSymbol(String symbol) {
//		// TODO Auto-generated method stub
//		 return stockPriceRepository.getUniqueSpSymbol(symbol);
//	}
//	
	
	
//	public List<String> getDistinctSpsymbols() {
//        return stockPriceRepository.findDistinctSpsymbols();
//    }
//
//    public List<StockPrice> getDataBySymbol(String symbol) {
//        return stockPriceRepository.findBySpsymbol(symbol);
//    }
//    public List<String> getAllSpsymbols() {
//        return stockPriceRepository.findAllSpsymbols();
//    }
}