package com.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.repository.IOrderItemRepository;

@Service
public class OrderItemService implements IOrderItemService{
	
	@Autowired
	private IOrderItemRepository orderItemRepository;

}
