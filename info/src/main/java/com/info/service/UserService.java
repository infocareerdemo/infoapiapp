package com.info.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.info.dto.UsersDto;
import com.info.entity.UserKYC;
import com.info.entity.Users;
import com.info.exception.ApplicationErrorCodes;
import com.info.exception.ApplicationRunTimeException;
import com.info.jwt.JwtUtil;
import com.info.jwt.Utility;
import com.info.repository.UserKYCRepository;
import com.info.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
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

	@Autowired
	Environment env;

	@Autowired
	UserKYCRepository userKYCRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Value("${spring.mail.username}")
	private String fromAddrs;

	public List<Users> getAllUsers() {
		List<Users> users = userRepository.findAll();
		return users;

	}

	public Optional<Users> getUserById(int id) {
		Optional<Users> opUser = userRepository.findById(id);
		//if (!opUser.isPresent()) {
			
//			throw new IllegalStateException("User Not Found");
		//}
		return opUser;
	}

	public Optional<Users> getMyAccount() {
		if (utility.getUserId() != 0) {
			Optional<Users> opUser = getUserById(utility.getUserId());
			if (opUser.isPresent()) {
				Optional<UserKYC> usrKyc = getUserKycById(opUser.get().getUserId().getId());
				if (usrKyc.isPresent()) {
					opUser.get().setUserId(usrKyc.get());
				}
			}
			return opUser;
		} else {
			throw new IllegalStateException("UnAuthorized");
		}
	}

	public Optional<UserKYC> getUserKycById(int id) {
		Optional<UserKYC> user = userKYCRepository.findById(id);
		if (user.isPresent()) {
			String url = env.getProperty("file.url");
			user.get().setImage(url + "/infoImages/" + user.get().getImage());
			user.get().setVideo(url + "/infoVideo/" + user.get().getVideo());
		}

		return user;
	}

	public UsernamePasswordAuthenticationToken validateLogin(Map<String, String> credentials) {

		String email = credentials.get("email");
		String password = credentials.get("password");

		String user = null;
		Users details = null;
		boolean emailAuthFlag = false;
		boolean lastLoginFlag = false;

		if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
			emailAuthFlag = true;
			details = userRepository.findByEmail(email);
			if (details == null) {
				throw new UsernameNotFoundException(email + " Not found");
			}
		}

		String role = details.getRole();

		if (password.equals(details.getPassword())) {
			lastLoginFlag = true;
			if (lastLoginFlag == true) {
				user = jwtUtil.doGenerateToken(details);
				details.setLastLogin(LocalDateTime.now());
			}

			request.getSession().setAttribute("LoggedIn", "TRUE");
			request.getSession().setAttribute("userName", details.getUsername());
			request.getSession().setAttribute("Role", details.getRole());
			request.getSession().setAttribute("Id", details.getId());

			userRepository.save(details);

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

	public void registerUser(UserKYC userKYC, String siteURL) throws UnsupportedEncodingException, MessagingException {

		if (userKYC != null) {
			Users users = new Users();
			users.setEmail(userKYC.getEmail());
			users.setUsername(userKYC.getFirstname());
			users.setRole("User");
			users.setUserId(userKYC);
			String randomCode = RandomStringUtils.randomAlphanumeric(64);
			users.setVerificationCode(randomCode);
			users.setEnabled(0);
			userRepository.save(users);
			sendVerificationEmail(users, siteURL);
		}
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

	public boolean verify(String verificationCode) throws UnsupportedEncodingException, MessagingException {
		Users user = userRepository.findByVerificationCode(verificationCode);

		if (user == null && user.getEnabled() == 0) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setEnabled(1);
			String password = RandomStringUtils.randomAlphanumeric(8);
			user.setPassword(password);
			userRepository.save(user);
			sendPasswordEmail(user);

			return true;
		}

	}

	private void sendPasswordEmail(Users user) throws UnsupportedEncodingException, MessagingException {

		String toAddress = user.getEmail();
		String fromAddress = fromAddrs;
		String senderName = "Infocareer Team";
		String subject = "Your Login Details";
		String content = "Dear [[name]],<br>" + "Please Login using this Details:<br>" + "Email:[[email]]<br>"
				+ "Password:[[password]]<br>" + "Kindly change your password at first login.<br>" + "Thank you,<br>"
				+ "Infocareer Team.";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", user.getUsername());
		content = content.replace("[[email]]", user.getEmail());
		content = content.replace("[[password]]", user.getPassword());

		helper.setText(content, true);

		mailSender.send(message);
	}

	public Map<String, Object> changePassword(Map<String, String> changePassword) {

		Map<String, Object> entity = new LinkedHashMap<String, Object>();

		String oldPassword = changePassword.get("oldPassword");
		String newPassword = changePassword.get("newPassword");

		if (utility.getUserId() != 0) {

			Optional<Users> user = userRepository.findById(utility.getUserId());

			if (user.isPresent()) {

				if (oldPassword.equals(user.get().getPassword())) {
					user.get().setPassword(newPassword);
					userRepository.save(user.get());
					entity.put("Message", "Password Changed Successfully");
				} else {
					entity.put("Error", "Old password Mis-Matched");
				}

			} else {
				entity.put("Error", "User Not Found");
			}

		}
		return entity;
	}

	public List<UsersDto> getAllUsersList() {
        String sql = "SELECT id, username, email FROM users";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            UsersDto user = new UsersDto();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            return user;
        });
    }
	
	// add by anu

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
			updateUsers.setPassword(userId.get().getPassword());
//			updateUsers.set

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
		// userRepository.deleteById(users.get());
		userRepository.delete(users.get());
		// userRepository.delete(users.get());
		return "Deleted";

	}

}
