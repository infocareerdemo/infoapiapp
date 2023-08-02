package com.info.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
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

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromAddrs;

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

	public void registerUser(Users user, String siteURL) throws UnsupportedEncodingException, MessagingException {

		Users users = new Users();
		BeanUtils.copyProperties(user, users);
		String randomCode = RandomStringUtils.randomAlphanumeric(64);
		user.setVerificationCode(randomCode);
		user.setEnabled(0);
		userRepository.save(user);
		sendVerificationEmail(user, siteURL);
	}

	private void sendVerificationEmail(Users user, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmail();
		String fromAddress = fromAddrs;
		String senderName = "Infocareer Team";
		String subject = "Please verify your registration";
		String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "Infocareer Team.";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", user.getUsername());

		String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

		content = content.replace("[[URL]]", verifyURL);

		helper.setText(content, true);

		mailSender.send(message);

	}

	public boolean verify(String verificationCode) {
		Users user = userRepository.findByVerificationCode(verificationCode);

		if (user == null && user.getEnabled() == 0) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setEnabled(1);
			userRepository.save(user);

			return true;
		}

	}
	
	
	//add by anu
	
	public Users save(Users users) {
		// TODO Auto-generated method stub
		Users usersEntity = userRepository.save(users);
		return usersEntity;
	}

	public Users getUserDetails(int id, Users userDetails) {
		// TODO Auto-generated method stub
		Optional<Users> userId = userRepository.findById(userDetails.getId());
		Users updateUsers = new Users();

		if (userId.isPresent()) {

			updateUsers.setId(userDetails.getId());
			updateUsers.setUsername(userDetails.getUsername());
			updateUsers.setEmail(userDetails.getEmail());
			updateUsers.setRole(userDetails.getRole());
			updateUsers.setPassword(userDetails.getPassword());

			userRepository.save(updateUsers);

		} else {
			throw new IllegalStateException("User Not Found");
		}
		return updateUsers;
	}

	public String getUserId(int id) {
		// TODO Auto-generated method stub
	  Optional<Users> users = userRepository.findById(id);
//		Users users = userRepository.findByUserId(id);
		//userRepository.deleteById(users.get());
		userRepository.delete(users.get());
		//userRepository.delete(users.get());
		return "Deleted";
	   
	}
	
	
}
