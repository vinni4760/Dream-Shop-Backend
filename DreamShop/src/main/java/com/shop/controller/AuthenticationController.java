package com.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.jwt.JWTFilter;
import com.shop.jwt.JWTService;
import com.shop.response.ApiResponse;
import com.shop.response.JwtResponse;
import com.shop.userdetails.ShopUserDetails;

import jakarta.validation.constraints.Positive;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private JWTFilter filter;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> loginApi(@RequestParam("email")String email,
			@RequestParam("password")String password){
		
			Authentication authentication = 
					authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(email, password)
					    );
			SecurityContextHolder.getContext()
			.setAuthentication(authentication);
			String jwt = jwtService.generateToken(authentication);
			
			ShopUserDetails details = (ShopUserDetails) authentication.getPrincipal();
			JwtResponse jwtResponse = new JwtResponse();
			jwtResponse.setId(details.getId());
			jwtResponse.setToken(jwt);
			return new ResponseEntity<JwtResponse>(jwtResponse,HttpStatus.OK);
		
			

		}

}
