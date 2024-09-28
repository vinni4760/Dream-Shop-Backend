package com.shop.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
 
		 response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		 response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication token was either missing or invalid.");
         
		/* Map<String, Object > map = new HashMap<String, Object>();
		 map.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		 map.put("error", "UnAuthorize");
		 map.put("message", "Authentication token was either missing or invalid !");
		 
		 ObjectMapper mapper = new ObjectMapper();
		 mapper.writeValue(response.getOutputStream(), map);
		 */
		 
	}
	

}
