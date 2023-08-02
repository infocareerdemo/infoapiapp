package com.info.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import com.info.service.StockPriceService;

public class ScheduleController {

	@Autowired
	StockPriceService service;

	@Scheduled(cron = "0 */5 * * * *")
	public ResponseEntity<Object> updateStockSch() {
		System.out.println("scheduled");
		return new ResponseEntity<Object>(service.updateStock(null, null), HttpStatus.OK);

	}

}
