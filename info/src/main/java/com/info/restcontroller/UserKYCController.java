package com.info.restcontroller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.info.entity.UserKYC;

import com.info.service.UserKYCService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/userKYC")
public class UserKYCController {

	@Autowired
	UserKYCService userKYCService;

	@PostMapping("/regUser")
	public ResponseEntity<Object> registerUser(@ModelAttribute @Valid UserKYC userKYC, @RequestPart MultipartFile img,
			@RequestPart MultipartFile vdo, HttpServletRequest request) throws IOException, MessagingException {
		return new ResponseEntity<Object>(userKYCService.registerUser(userKYC, img, vdo, getSiteURL(request)),
				HttpStatus.OK);
	}

	@GetMapping("/kycId")
	public ResponseEntity<Object> getUserById(@RequestParam int id) {
		return new ResponseEntity<Object>(userKYCService.getUserById(id), HttpStatus.OK);
	}

	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}


}
