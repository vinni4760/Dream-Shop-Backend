package com.shop.service;

import java.util.List;

import com.shop.dto.Orderdto;
import com.shop.entity.Order;

public interface IOrderService {
          
	 Order placeOrder(Integer user_id);
	 Orderdto getOrder(Integer order_id);
	 List<Orderdto> getUserOrders(Integer user_id);
	 Orderdto convertodto(Order order);
}
