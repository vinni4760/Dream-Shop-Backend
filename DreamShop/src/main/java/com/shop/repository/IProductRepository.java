package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Product;

public interface IProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByName(String name);


	List<Product> findByCategoryName(String category);


	List<Product> findByBrand(String brand);


	List<Product> findByCategoryNameAndBrand(String category, String brand);


	List<Product> findByBrandAndName(String brand, String name);


	Long countByBrandAndName(String brand, String name);


	boolean existsByName(String name);

}
