package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Cart;

public interface ICartRepository extends JpaRepository<Cart, Integer> {
	 Cart findByUserId(Integer user_id);
}
