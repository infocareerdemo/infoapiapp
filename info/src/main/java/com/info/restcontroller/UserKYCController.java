package com.info.restcontroller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.info.entity.UserKYC;

import com.info.service.UserKYCService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/userKYC")
public class UserKYCController {

	@Autowired
	UserKYCService userKYCService;

	@PostMapping("/regUser")
	public ResponseEntity<Object> registerUser(@ModelAttribute @Valid UserKYC userKYC,@RequestPart MultipartFile img,@RequestPart MultipartFile vdo) throws IOException {
		return new ResponseEntity<Object>(userKYCService.registerUser(userKYC, img,vdo), HttpStatus.OK);
	}

//	@PostMapping("/users/{id}/profile-image")
//    public ResponseEntity<String> uploadProfileImage(
//            @PathVariable Long id,
//            @RequestParam("file") MultipartFile file
//    ) {
//        return handleFileUpload(id, file, "profileImage");
//    }
//
//    @PostMapping("/users/{id}/profile-video")
//    public ResponseEntity<String> uploadProfileVideo(
//            @PathVariable Long id,
//            @RequestParam("file") MultipartFile file
//    ) {
//        return handleFileUpload(id, file, "profileVideo");
//    }

}
