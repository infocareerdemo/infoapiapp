package com.info.jwt;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.info.entity.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	private final String secret = "secret";
	
	@Value("${jwt.expirationTime}")
	private long expirationTime;
	
	/*---- jwt login token generation ----*/

//	Date issuedAt = new Date();
//	Date now = new Date();
//	Date expiredAt = new Date(now.getTime() + SecurityConstant.EXPIRATION_TIME * 60000);

	public String doGenerateToken(Users userDetails) {
		Claims claims = Jwts.claims().setSubject(String.valueOf(userDetails.getId()));
		claims.put("email", userDetails.getEmail());
		claims.put("id", userDetails.getId());
		claims.put("role", userDetails.getRole());
		claims.put("userName", userDetails.getUsername());
		
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(SignatureAlgorithm.HS512, secret)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime * 60000))
				.compact();
	}

	public String doGenerateRefreshToken(Users userDetails) {
		Claims claims = Jwts.claims().setSubject(String.valueOf(userDetails.getId()));
		claims.put("email", userDetails.getEmail());
		claims.put("id", userDetails.getId());
		claims.put("role", userDetails.getRole());
		claims.put("userName", userDetails.getUsername());

		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(SignatureAlgorithm.HS512, secret)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime * 60000))
				.compact();
	}
}
