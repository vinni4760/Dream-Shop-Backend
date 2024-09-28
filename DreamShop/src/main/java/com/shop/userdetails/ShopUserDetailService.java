package com.shop.userdetails;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shop.entity.User;
import com.shop.exceptions.ResourceNotFoundException;
import com.shop.repository.IUserRepository;

@Service
public class ShopUserDetailService implements UserDetailsService{
	
	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     User user =     Optional.ofNullable(userRepository.findByEmail(username))
           .orElseThrow(()->new ResourceNotFoundException("User Not Found !"));
         return new ShopUserDetails(user);
	}

}
