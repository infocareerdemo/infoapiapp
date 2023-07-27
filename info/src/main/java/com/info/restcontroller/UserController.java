package com.info.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.info.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/list")
	public ResponseEntity<Object> getAllUsers() {
		return new ResponseEntity<Object>(userService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/id")
	public ResponseEntity<Object> getUserById(@RequestParam int id) {
		return new ResponseEntity<Object>(userService.getUserById(id), HttpStatus.OK);
	}

	@GetMapping("/account")
	public ResponseEntity<Object> getMyAccount(@RequestParam int id) {
		return new ResponseEntity<Object>(userService.getMyAccount(id), HttpStatus.OK);
	}
}
