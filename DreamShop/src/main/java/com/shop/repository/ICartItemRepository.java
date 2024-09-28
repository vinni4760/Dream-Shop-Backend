package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.CartItem;

public interface ICartItemRepository extends JpaRepository<CartItem, Integer>{
        
	void deleteAllByCartId(Integer id);
}
