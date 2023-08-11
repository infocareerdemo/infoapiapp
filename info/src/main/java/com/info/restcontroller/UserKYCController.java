package com.info.restcontroller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.info.entity.UserKYC;
import com.info.exception.CustomErrorResponse;
import com.info.service.UserKYCService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@CrossOrigin(origins = {"http://localhost:3000/"})
@RestController
@RequestMapping("/userKYC")
public class UserKYCController {

	@Autowired
	UserKYCService userKYCService;

	@PostMapping("/regUser")
	public ResponseEntity<Object> registerUser(@Valid UserKYC userKYC, BindingResult result,
			@Valid @RequestPart MultipartFile img, @Valid @RequestPart MultipartFile vdo, HttpServletRequest request)
			throws IOException, MessagingException {

		Map<String, String> errors = new HashMap<>();
		if (result.hasErrors()) {
			for (FieldError error : result.getFieldErrors()) {
				System.out.println(error.getDefaultMessage());
				errors.put(error.getField(), error.getDefaultMessage());
			}

			if (img.getOriginalFilename().equals("") || img == null) {
				errors.put("Image", "Image file Required");
			}

			if (vdo.getOriginalFilename().equals("") || vdo == null) {
				errors.put("Video", "Video file Required");
			}

			return ResponseEntity.badRequest().body(new CustomErrorResponse("Validation errors", errors));
		}

		return new ResponseEntity<>(userKYCService.registerUser(userKYC, img, vdo, getSiteURL(request)), HttpStatus.OK);
	}

	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}

}
