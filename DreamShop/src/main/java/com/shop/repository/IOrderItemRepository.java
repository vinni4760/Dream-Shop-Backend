package com.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.entity.OrderItems;

public interface IOrderItemRepository extends JpaRepository<OrderItems, Integer>{

}
