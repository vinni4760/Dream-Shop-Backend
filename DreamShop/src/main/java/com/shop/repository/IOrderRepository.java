package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.Order;

public interface IOrderRepository extends JpaRepository<Order, Integer>{

	List<Order> findByUserId(Integer user_id);

}
