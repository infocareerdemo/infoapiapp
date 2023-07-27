package com.info.jwt;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.info.entity.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomHttpRequestInterceptor implements HandlerInterceptor {

	private final String secret = "secret";

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	Utility utility;


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = extractTokenFromRequest(request);

		if (token != null)
			if (isTokenExpired(token)) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setContentType("application/json");
				String errorMessage = "Token has expired";
				response.getWriter().write(errorMessage);
				return false;
			}
		return true;
	}

	public boolean isTokenExpired(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

		Date expiration = claims.getExpiration();
		return expiration.before(new Date());
	}

	private String extractTokenFromRequest(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7); // Exclude "Bearer " prefix to get the token
		}
		return null;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex)
			throws Exception {
		String authorizationHeader = request.getHeader("Authorization");
		String tokenPrefix = "Bearer ";
		if (authorizationHeader != null) {
			Jws<Claims> jws = Jwts.parser().setSigningKey(secret)
					.parseClaimsJws(authorizationHeader.replace(tokenPrefix, ""));
			int id = jws.getBody().get("id") == null ? 0 : (int) jws.getBody().get("id");
			Users userDetails = utility.getUserById(id);
			String regenToken = jwtUtil.doGenerateRefreshToken(userDetails);
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				authorizationHeader = "";
				authorizationHeader = "Bearer " + regenToken;
				System.out.println(authorizationHeader);
			}
		}
	}

}
