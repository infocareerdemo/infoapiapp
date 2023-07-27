package com.info.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.entity.Users;
import com.info.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public List<Users> getAllUsers() {
		List<Users> users = userRepository.findAll();
		return users;

	}

	public Optional<Users> getUserById(int id) {
		Optional<Users> opUser = userRepository.findById(id);
		if (!opUser.isPresent()) {
			throw new IllegalStateException("User Not Found");
		}
		return opUser;
	}

	public Optional<Users> getMyAccount(int id) {
		Optional<Users> opUser = getUserById(id);
		return opUser;
	}

}
