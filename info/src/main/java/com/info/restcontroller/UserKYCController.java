package com.info.restcontroller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.info.entity.CustomErrorResponse;
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
	public ResponseEntity<Object> registerUser(@Valid UserKYC userKYC, BindingResult result,
			@Valid @RequestPart MultipartFile img, @Valid @RequestPart MultipartFile vdo, HttpServletRequest request)
			throws IOException, MessagingException {

		if (result.hasErrors()) {
			// Handle validation errors here
			Map<String, String> errors = new HashMap<>();
			for (FieldError error : result.getFieldErrors()) {
				System.out.println(error.getDefaultMessage());
				errors.put(error.getField(), error.getDefaultMessage());
				
			}
			return ResponseEntity.badRequest().body(new CustomErrorResponse("Validation errors", errors));
		}
		return new ResponseEntity<>(userKYCService.registerUser(userKYC, img, vdo, getSiteURL(request)), HttpStatus.OK);
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
