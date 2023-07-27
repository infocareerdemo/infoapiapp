package com.info.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.info.entity.LoginDto;
import com.info.entity.Users;
import com.info.jwt.JwtUtil;
import com.info.jwt.Utility;
import com.info.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	Utility utility;

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

	public Optional<Users> getMyAccount() {
		if (utility.getUserId() != 0) {
			Optional<Users> opUser = getUserById(utility.getUserId());
			return opUser;
		} else {
			throw new IllegalStateException("UnAuthorized");
		}
	}

	public UsernamePasswordAuthenticationToken validateLogin(LoginDto loginDto) {

		String user = null;
		Users details = null;
		boolean emailAuthFlag = false;

		if (loginDto.getEmail() != null && !loginDto.getEmail().isEmpty() && loginDto.getPassword() != null
				&& !loginDto.getPassword().isEmpty()) {

			emailAuthFlag = true;
			details = userRepository.findByEmail(loginDto.getEmail());
			if (details == null) {
				throw new UsernameNotFoundException(loginDto.getEmail() + "Not found");
			}
		}

		String role = details.getRole();

		if (loginDto.getPassword().equals(details.getPassword())) {

			user = jwtUtil.doGenerateToken(details);

			request.getSession().setAttribute("LoggedIn", "TRUE");
			request.getSession().setAttribute("userName", details.getUsername());
			request.getSession().setAttribute("Role", details.getRole());
			request.getSession().setAttribute("Id", details.getId());

			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(role));

			User users = new User(user, "", authorities);
			return new UsernamePasswordAuthenticationToken(users, null, authorities);
		}

		if (emailAuthFlag == false) {
			throw new UsernameNotFoundException("Incorrect User");
		} else {
			throw new UsernameNotFoundException("Incorrect Password");

		}
	}
}
