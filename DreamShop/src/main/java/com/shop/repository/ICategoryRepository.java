package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Category;

public interface ICategoryRepository extends JpaRepository<Category, Integer> {

	  public Category findByName(String categoryname);

	public boolean existsByName(String name);
}
