package com.info.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.info.entity.StockCash;
import com.info.entity.StockFuture;
import com.info.entity.StockPrice;
import com.info.service.SearchService;

@RestController
public class SearchController {
	
	@Autowired
	SearchService searchService;

	@PostMapping("/search")
    public List<StockPrice> searchList(@RequestParam("keyword") String keyword) {
        return searchService.searchList(keyword);

    }
	
	@PostMapping("/searchSymbol")
    public List<StockPrice> searchSymbol(@RequestParam("symbol") String symbol) {
        return searchService.searchSymbol(symbol);

    }
	
	 @PostMapping("/getUniqueSpSymbol")
	  public List<StockPrice> getUniqueSpSymbol(@RequestParam("symbol") String symbol) {
	        return searchService.getUniqueSpSymbol(symbol);
		 
	 }
	 
	 @PostMapping("/getSymbol")
	 public Map<String, Object> getAllSpsymb(@RequestParam("spSymb") String spSymb){
		 return searchService.getAllSpsymb(spSymb);
	 }
	 
	 
}


