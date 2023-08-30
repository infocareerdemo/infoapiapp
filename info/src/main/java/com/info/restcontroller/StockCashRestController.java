package com.info.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.info.entity.StockCash;
import com.info.service.StockCashService;

@RestController
@RequestMapping("/data")
public class StockCashRestController {
	@Autowired
	private StockCashService service;
	
	
	@GetMapping("/executeCustomQuery")
    public List<Object[]> executeCustomQuery(@RequestParam("sqlQuery") String sqlQuery) {
        return service.executeCustomQuery(sqlQuery);

    }
	
	  @GetMapping("/reliancedata") 
	  public List<StockCash> getreliancedata(@RequestParam String symbolname) {
		  return service.getreliancedata(symbolname); 
		  }	
	  
	  @PostMapping("/getStockCashSymbol")
		 public List<StockCash> getStockCashSymbol(@RequestParam("symbol") String symbol){
			 return service.getStockCashSymbol(symbol);
		 }
		 	

}
