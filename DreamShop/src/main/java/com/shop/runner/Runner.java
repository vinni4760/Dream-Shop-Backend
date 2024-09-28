package com.shop.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.shop.entity.Roles;
import com.shop.repository.IRoleRepository;

//@Component
public class Runner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
/*	
	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		Roles role =new Roles();
		role.setName("ROLE_ADMIN");
	   	roleRepository.save(role);
	}
	*/

}
