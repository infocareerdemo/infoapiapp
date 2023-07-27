package com.info.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.info.entity.Users;
import com.info.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthentication extends BasicAuthenticationFilter {
	
	private final String secret = "secret";
	

	UserRepository userRepository;

	public JwtAuthentication(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException, UsernameNotFoundException {
		String header = req.getHeader("Authorization");
		String tokenPrefix = "";
		if (header == null) {
			String[] authHeaderStrings = "noToken".split(",");
			for (String authHeader : authHeaderStrings) {
				header = req.getHeader(authHeader);
			}
		} else {
			tokenPrefix = "Bearer ";
		}
		if (header == null && req.getSession() != null
				&& req.getSession().getAttribute("Authorization") != null) {
			header = req.getSession().getAttribute("Authorization").toString();
			tokenPrefix = "Bearer ";
		}

		if (header == null) {
			chain.doFilter(req, res);
		} else {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(header, tokenPrefix);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			if (authentication != null) {
				MDC.put("traceUser", authentication.getPrincipal().toString());
				MDC.put("traceID", UUID.randomUUID().toString().replace("-", ""));
				chain.doFilter(req, res);
			} else {
				throw new UsernameNotFoundException("Invalid token in the request");
			}
		}
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(String token, String tokenPrefix) {
		PrincipalDetails principalDetails = new PrincipalDetails();
		if (token != null) {
			Jws<Claims> jws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token.replace(tokenPrefix, ""));
			String email = jws.getBody().get("email") == null ? "" : jws.getBody().get("email").toString();
			String name = jws.getBody().get("userName") == null ? "" : jws.getBody().get("userName").toString();
			int id = jws.getBody().get("id") == null ? 0 : (int) jws.getBody().get("id");

			Users details = checkUserAndTokenStatus(email, id, token.replace(tokenPrefix, ""));

			if (details != null) {
				principalDetails.setEmail(email);
				principalDetails.setName(name);
				principalDetails.setId(id);
				principalDetails.setRole((String) jws.getBody().get("role"));
				String roles;
				roles = (String) jws.getBody().get("role");
				List<GrantedAuthority> grantedAuths = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
				return new UsernamePasswordAuthenticationToken(principalDetails, null, grantedAuths);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public Users checkUserAndTokenStatus(String email, int id, String token) {
		Users ed = null;
		try {
			Optional<Users> eid = userRepository.findById(id);
			if (eid.isPresent()) {
				if (email != null && !email.isEmpty()) {
					ed = userRepository.findByEmailIgnoreCase(email);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return ed;
	}
	
}
