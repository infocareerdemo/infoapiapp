package com.info.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.info.service.Get_wdgwheelService;


@RestController
@RequestMapping("/data")
public class Get_wdgwheelRestController {

	@Autowired
	private Get_wdgwheelService get_wdgwheelService;
	
	
	@GetMapping("/wdgwheel")
	public List<Object> get_wdgwheel(@RequestParam(defaultValue = "1") int from,@RequestParam(defaultValue = "10") int to) {
		return get_wdgwheelService.get_wdgwheel(from, to);
	}
	
	@GetMapping("/wdgwheeldeep")
	public List<Object> get_wdgwheel_deep(@RequestParam(defaultValue = "1") int from,@RequestParam(defaultValue = "10") int to) {
		return get_wdgwheelService.get_wdgwheel(from, to);
	}
	
	

}
