package com.shop.jwt;

import java.io.IOException;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shop.userdetails.ShopUserDetails;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter{

	@Autowired
	private JWTService jwtService;
	
	@Autowired
   private org.springframework.context.ApplicationContext	applicationContext;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			
			String authHeader = 	request.getHeader("Authorization");
			   String username = null;
			   String token = null;
			   
			   if(authHeader!=null&&authHeader.startsWith("Bearer")) {
				   System.out.println(authHeader);
				   token = authHeader.substring(7);
				   System.out.println(token);
			     username = jwtService.getUserName(token);
			   }
			 
			   if(StringUtils.hasText(token)&&jwtService.validateToken(token)) {
				   UserDetails userDetails = applicationContext.getBean(UserDetailsService.class)
						    											.loadUserByUsername(username);
				   Authentication authentication = 
						   new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());  
				    SecurityContextHolder.getContext()
				    .setAuthentication(authentication);
			   }
			
		} catch (JwtException e) {
			response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(e.getMessage());
			
		}
		filterChain.doFilter(request, response);   
		
	}

}
