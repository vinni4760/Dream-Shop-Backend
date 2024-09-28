package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.User;

public interface IUserRepository extends JpaRepository<User, Integer>{

	     Boolean existsByEmail(String email);
	     User findByEmail(String email);
}
