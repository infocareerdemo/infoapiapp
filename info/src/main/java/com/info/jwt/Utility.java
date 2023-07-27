package com.info.jwt;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.info.entity.Users;
import com.info.repository.UserRepository;


@Component
public class Utility {

	@Autowired
	UserRepository userRepository;
	
	public String getUserName() {
		PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return principalDetails.getName();
	}

	public int getUserId() {
	
		try {
			PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return principalDetails.getId();
		}catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}

	public Users getUser() {
		PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Users userModel = null;
		if (principalDetails != null && principalDetails.getId() != 0) {
			Optional<Users> user = userRepository.findById(principalDetails.getId());
			userModel = user.get();
		}
		return userModel;
	}

	public String getUserRole() {
		PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return principalDetails.getRole();
	}

	public String generateRandom() {
		int randomToken = new Random().nextInt(1000000) % 1000000;
		return String.valueOf(String.format("%06d", randomToken));
	}

	public String generateRequestRandom() {
		int randomToken = new Random().nextInt(10000) % 10000;
		return String.valueOf(String.format("%04d", randomToken));
	}

//	public String getRecieverUserId(String phoneNumber) {
//		Query query = new Query(Criteria.where("mobileNumber").is(phoneNumber));
//		UserDetails userModel = mongoTemplate.findOne(query, UserDetails.class);
//		return userModel != null ? userModel.getId() : null;
//	}

	public Users getUserById(int id) {
		Optional<Users> user = userRepository.findById(id);
		Users userModel = user.get();
		return userModel;
	}
}
