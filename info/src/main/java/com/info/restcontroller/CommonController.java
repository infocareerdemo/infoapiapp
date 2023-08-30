package com.info.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.info.service.CommonService;


@RestController
public class CommonController {
	
	@Autowired
	CommonService commonService;
	
	@GetMapping("/data")
    public List<Object> getData() {
        return commonService.getDataFromTable();
    }
	
	@GetMapping("/alldata")
    public List<Object[]> getAllData() {
        return commonService.getAllDataFromTable();
    }
}
