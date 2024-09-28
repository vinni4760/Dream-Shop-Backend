package com.shop.jwt;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.shop.entity.Roles;
import com.shop.userdetails.ShopUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	private final SecretKey keys = Keys.hmacShaKeyFor("afdocmnftydsfdwtabcksdkiiiiiiiiiiiiiiiskfklsdfksdjfkjsd".getBytes());

	
	@Autowired
	private ShopUserDetails details;
	
   public	String generateToken(Authentication authentication) {
		
		ShopUserDetails userprincipal = (ShopUserDetails) authentication.getPrincipal();
		List<SimpleGrantedAuthority> roles = userprincipal.getAuthorities()
				                              .stream().map(role->new SimpleGrantedAuthority(role.getAuthority()))
				                              .toList();
		
		return Jwts.builder()
		       .subject(userprincipal.getEmail())
		       .claim("id", userprincipal.getId())
		       .claim("roles", roles)
		       .issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 30 * 60 * 60 * 1000))
				.signWith(keys)
				.compact();
		
	}

	public String getUserName(String token) {
		
       Claims claims =     Jwts.parser()
            .verifyWith(keys)
            .build()
            .parseSignedClaims(token)
            .getPayload();
       return claims.getSubject();

	}
	
	public Boolean validateToken(String token) {
		
		try {
			Jwts.parser()
			.verifyWith(keys)
			.build()
			.parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
		
		
	}

	
	
	
}
