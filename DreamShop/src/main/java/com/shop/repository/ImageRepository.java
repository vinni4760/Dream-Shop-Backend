package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
       
	public List<Image> findByProductId(Integer id);
}
