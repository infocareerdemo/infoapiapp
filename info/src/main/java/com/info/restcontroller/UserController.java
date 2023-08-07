package com.info.restcontroller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.info.entity.ChangePassword;
import com.info.entity.LoginDto;
import com.info.entity.LoginResponse;
import com.info.entity.Users;
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

	@GetMapping("/list")
	public ResponseEntity<Object> getAllUsers() {
		return new ResponseEntity<Object>(userService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/id")
	public ResponseEntity<Object> getUserById(@RequestParam int id) {
		return new ResponseEntity<Object>(userService.getUserById(id), HttpStatus.OK);
	}

	@GetMapping("/account")
	public ResponseEntity<Object> getMyAccount() {
		return new ResponseEntity<Object>(userService.getMyAccount(), HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> validateLogin(@RequestBody LoginDto loginDto, HttpServletRequest response,
			HttpSession session) {
		String responseMsg = "";

		Users ud = userRepository.findByEmail(loginDto.getEmail());

		try {

			final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = userService
					.validateLogin(loginDto);
//			session.setAttribute("authorization", "Bearer " + usernamePasswordAuthenticationToken.getName());
			User user = (User) usernamePasswordAuthenticationToken.getPrincipal();
			if (user.getAuthorities() != null && user.getAuthorities().size() > 0) {

				String roleName = ud.getRole();
				roleName = user.getAuthorities().toArray()[0].toString();
				LoginResponse loginResponse = new LoginResponse();

				if (roleName.equalsIgnoreCase("admin")) {
					loginResponse.setResponseText("Success");
					loginResponse.setUserToken(usernamePasswordAuthenticationToken.getName());
					return ResponseEntity.ok(loginResponse);
				}
				if (roleName.equalsIgnoreCase("user")) {
					loginResponse.setResponseText("Success");
					loginResponse.setUserToken(usernamePasswordAuthenticationToken.getName());
					return ResponseEntity.ok(loginResponse);
				}
			}
		} catch (UsernameNotFoundException e) {
			responseMsg = "Error:" + e.getMessage();
			LoginResponse logResponse = new LoginResponse();
			logResponse.setResponseText(responseMsg);
			return ResponseEntity.ok(logResponse);
		}

		return ResponseEntity.ok(responseMsg);
	}

//	@PostMapping("/register")
//	public String processRegister(@RequestBody Users user, HttpServletRequest request)
//			throws UnsupportedEncodingException, MessagingException {
//		userService.registerUser(user, getSiteURL(request));
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
	public ResponseEntity<Object> changePassword(@RequestBody ChangePassword changePassword){
		return new ResponseEntity<Object>(userService.changePassword(changePassword), HttpStatus.OK);
	}
	
	

	//add by anu
	//add users
		@PostMapping("/addUser")
	    public Users createUser(@RequestBody Users users ) {
	        return userService.save(users);
	    }
		
		//update users
		@PostMapping("/updateUser")
		public ResponseEntity <Users> updateUser(@RequestParam int id,@RequestBody Users userDetails){
			
			return new ResponseEntity<Users>(userService.getUserDetails(id,userDetails),HttpStatus.OK);	
		}
		
		//delete users
		@DeleteMapping("/deleteUser")
		public String deleteUser(@RequestParam int id){
			return userService.getUserId(id); 
			
		}
		
		
		
	
	
}
