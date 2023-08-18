package com.info.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
