package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Roles;

public interface IRoleRepository extends JpaRepository<Roles, Integer>{
         
	public Roles findByName(String name);
}
