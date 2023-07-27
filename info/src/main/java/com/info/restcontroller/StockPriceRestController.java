package com.info.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.info.entity.StockPrice;
import com.info.service.StockPriceService;

@RestController
@RequestMapping("/data")
public class StockPriceRestController {
	@Autowired
	private StockPriceService service;

	@GetMapping("/stockprice")
	public StockPrice listAllStockPriceByUSingSelectQuery(@RequestParam(defaultValue = "0") int spid) {
		return service.listAllStockPrices(spid);
	}

	@GetMapping("/allstockprice")
	public List<StockPrice> listAllStockPriceBySelectQuery() {
		return service.listAllStockPricesUsingObjectDatatype();
	}

	@GetMapping("/stockpricename")
	public StockPrice stockPriceByName(@RequestParam String spname) {
		return service.stockPricesByName(spname);
	}

}