package com.info.restcontroller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.info.entity.Users;
import com.info.exception.ApplicationErrorCodes;
import com.info.exception.ApplicationException;
import com.info.exception.ApplicationRunTimeException;
import com.info.jwt.JwtUtil;
import com.info.jwt.Utility;
import com.info.repository.UserRepository;
import com.info.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utility utility;

	@Autowired
	JwtUtil jwtUtil;

//	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	Logger log = LoggerFactory.getLogger(UserController.class);

	@GetMapping("/list")
	public ResponseEntity<Object> getAllUsers() {
		return new ResponseEntity<Object>(userService.getAllUsers(), HttpStatus.OK);

	}

	@GetMapping("/id")
	public ResponseEntity<Object> getUserById(@RequestParam int id) {

		Optional<Users> usr = userService.getUserById(id);
		log.info("id:"+String.valueOf(id));
		if (usr.isPresent()) {
			
			return new ResponseEntity<Object>(usr, HttpStatus.OK);
		} else {
//			log.error("id : ", id);
			throw new ApplicationRunTimeException(HttpStatus.PRECONDITION_FAILED, ApplicationErrorCodes.DB_UNAUTHORIZED,
					new Date(), ApplicationErrorCodes.DB_UNAUTHORIZED_MSG);
		}
	}

	@GetMapping("/account")
	public ResponseEntity<Object> getMyAccount() {

		return new ResponseEntity<Object>(userService.getMyAccount(), HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> validateLogin(@RequestBody Map<String, String> credentials,
			HttpServletRequest response, HttpSession session) throws ApplicationException {

		String email = credentials.get("email");
		Map<String, Object> loginResponse = new HashMap<>();

		String responseMsg = "";
		try {
			Users ud = userRepository.findByEmail(email);

			final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = userService
					.validateLogin(credentials);
//			session.setAttribute("authorization", "Bearer " + usernamePasswordAuthenticationToken.getName());
			User user = (User) usernamePasswordAuthenticationToken.getPrincipal();
			if (user.getAuthorities() != null && user.getAuthorities().size() > 0) {

				String roleName = ud.getRole();
				roleName = user.getAuthorities().toArray()[0].toString();

				if (roleName.equalsIgnoreCase("admin")) {
					loginResponse.put("ResponseText", "Success");
					loginResponse.put("UserToken", usernamePasswordAuthenticationToken.getName());
					return ResponseEntity.ok(loginResponse);
				}
				if (roleName.equalsIgnoreCase("user")) {
					loginResponse.put("ResponseText", "Success");
					loginResponse.put("UserToken", usernamePasswordAuthenticationToken.getName());
					return ResponseEntity.ok(loginResponse);
				}
			}
		} catch (Exception e) {
			responseMsg = "Error:" + e.getMessage();
			loginResponse.put("ResponseText", responseMsg);

			throw new ApplicationRunTimeException(HttpStatus.INTERNAL_SERVER_ERROR,
					ApplicationErrorCodes.DB_UNAUTHORIZED, new Date(), ApplicationErrorCodes.DB_UNAUTHORIZED_MSG);

			// return ResponseEntity.ok(loginResponse);
		}

		return ResponseEntity.ok(responseMsg);
	}

//	@PostMapping("/register")
//	public String processRegister(@RequestBody Users user, HttpServletRequest request)
//			throws UnsupportedEncodingException, MessagingException {
//		userService.registerUser(user, getSiteURL(requ1est));
//		return "Successfully Registered";
//	}

	@PostMapping("/verify")
	public String verifyUser(@RequestParam String code) throws UnsupportedEncodingException, MessagingException {
		if (userService.verify(code)) {
			return "Successfully verified..!!.Details send to your Email";
		} else {
			return "Verification failed..!!";
		}
	}

	@PostMapping("/chngPswd")
	public ResponseEntity<Object> changePassword(@RequestBody Map<String, String> changePassword) {
		return new ResponseEntity<Object>(userService.changePassword(changePassword), HttpStatus.OK);
	}

	// add by anu
	// add users
	@PostMapping("/addUser")
	public Users createUser(@RequestBody Users users) {
		return userService.save(users);
	}

	// update users
	@PostMapping("/updateUser")
	public ResponseEntity<Users> updateUser(@RequestParam int id, @RequestBody Users userDetails) {

		return new ResponseEntity<Users>(userService.getUserDetails(id, userDetails), HttpStatus.OK);
	}

	// delete users
	@DeleteMapping("/deleteUser")
	public String deleteUser(@RequestParam int id) {
		return userService.getUserId(id);

	}

}
