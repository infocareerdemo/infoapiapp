package com.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.repository.Get_wdgwheelRepository;

@Service
public class Get_wdgwheelService {
	
	@Autowired
	private Get_wdgwheelRepository get_wdgWheel;
	
	
	public List<Object> get_wdgwheel(int from ,int to){
		return get_wdgWheel.get_wdgwheel(from,to);
	}

	
	public List<Object> get_wdgwheel_deep(int from ,int to){
		return get_wdgWheel.get_wdgwheel_deep(from,to);
	}
}
