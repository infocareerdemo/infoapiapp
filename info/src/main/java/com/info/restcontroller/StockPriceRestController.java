package com.info.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping("/stockList")
	public ResponseEntity<Object> getAllStockPrice(@RequestParam(defaultValue = "0") int pgNo,
			@RequestParam(defaultValue = "10") int pgSize) {
		return new ResponseEntity<Object>(service.getAllStockPrice(pgNo, pgSize), HttpStatus.OK);

	}

	@GetMapping("/tdcloseStk")
	public ResponseEntity<Object> getAllStockPriceByTdclose(@RequestParam int tdclose,
			@RequestParam(defaultValue = "0") int pgNo, @RequestParam(defaultValue = "10") int pgSize) {
		return new ResponseEntity<Object>(service.getAllStockPriceByTdclose(pgNo, pgSize, tdclose), HttpStatus.OK);

	}

	@PostMapping("/updateStock")
	public ResponseEntity<Object> updateStock(@RequestParam String series,
			@RequestParam String timestamp) {

		return new ResponseEntity<Object>(service.updateStock(series, timestamp), HttpStatus.OK);

	}
	
//	@Scheduled(cron = "0 */5 * * * *")
//	public ResponseEntity<Object> updateStockSch() {
//		System.out.println("scheduled");
//		return new ResponseEntity<Object>(service.updateStock(null, null), HttpStatus.OK);
//
//	}

	

	 
	 @GetMapping("/getAllStockPriceList")
	 public ResponseEntity<List<StockPrice>> getAllStockPriceList() {
	     List<StockPrice> stockPrices = service.getAllStockPriceList();
	     return new ResponseEntity<>(stockPrices, HttpStatus.OK);
	 }
	 
	 @PostMapping("/getSymbolUsingInstrument")
	 public List<StockPrice> getSymbolUsingInstrument(@RequestParam String symbol ){
		List<StockPrice> symboldata = service.getSymbolUsingInstrument(symbol);
		return symboldata;
		 
	 }

}
