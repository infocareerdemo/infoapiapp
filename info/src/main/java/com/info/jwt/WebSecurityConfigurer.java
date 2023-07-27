package com.info.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.info.repository.UserRepository;

@Configuration
public class WebSecurityConfigurer {

	@Autowired
	private UserRepository userRepository;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(httpCsrf -> httpCsrf.disable()).authorizeHttpRequests(req -> 
		req.requestMatchers("/**").permitAll()
		.anyRequest().authenticated())
		.addFilterBefore(new JwtAuthentication(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)),
				userRepository), BasicAuthenticationFilter.class)
		.exceptionHandling(ex -> ex.authenticationEntryPoint(new UnauthenticatedRequestHandler()));
		return http.build();
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
