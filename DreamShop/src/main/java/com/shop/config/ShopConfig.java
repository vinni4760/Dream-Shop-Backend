package com.shop.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shop.jwt.JWTFilter;
import com.shop.jwt.JwtEntryPoint;
import com.shop.userdetails.ShopUserDetailService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class ShopConfig {
	
	@Autowired
	private JWTFilter jwtFilter;
	
	@Bean
	public ModelMapper modelmapper() {
		return new ModelMapper();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		
		security.csrf(AbstractHttpConfigurer::disable)
		.exceptionHandling(ex->ex.authenticationEntryPoint(entryPoint()))
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth->auth.requestMatchers("/product/add","/shop/cart/**","/shop/cartitem/**")
				.authenticated()
				.anyRequest()
				.permitAll());
		security.authenticationProvider(authenticationProvider());
		security.addFilterBefore(jwtFilter, 
				UsernamePasswordAuthenticationFilter.class);
		return security.build();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = 
				new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(shopUserDetailService());
		daoAuthenticationProvider.setPasswordEncoder(encoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public ShopUserDetailService shopUserDetailService() {
		return new  ShopUserDetailService();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean 
	public JwtEntryPoint entryPoint() {
		return new JwtEntryPoint();
	}
	

}
