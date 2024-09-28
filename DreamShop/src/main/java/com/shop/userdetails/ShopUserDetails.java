package com.shop.userdetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.shop.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShopUserDetails implements UserDetails{
	
	private Integer id;
	private String email;
	private String password;
	private Collection<SimpleGrantedAuthority> authorities =	new HashSet<SimpleGrantedAuthority>();

	public ShopUserDetails(User user) {
		this.authorities = user.getRoles()
				.stream().map(role->new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());
		this.id= user.getId();
		this.email = user.getEmail();
		this.password= user.getPassword();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

}
