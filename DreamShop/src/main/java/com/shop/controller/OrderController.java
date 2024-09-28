package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.Orderdto;
import com.shop.entity.Order;
import com.shop.response.ApiResponse;
import com.shop.service.IOrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private IOrderService orderService;
	
	@PostMapping("/placeorder")
	public ResponseEntity<ApiResponse> placeOrderApi(@RequestParam Integer id){
		
		try {
		Order orderdto =	orderService.placeOrder(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Order Place Success !", orderdto),
				HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Order Creation Failed !", e.getMessage()),
					HttpStatus.CONFLICT);
		}		
	}
	
	@GetMapping("/get")
	public ResponseEntity<ApiResponse> getOrderApi(@RequestParam Integer id){
		
		try {
			Orderdto orderdto = orderService.getOrder(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Get Order  Success !", orderdto),
				HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(" Failed !", e.getMessage()),
					HttpStatus.NOT_FOUND);
		}		
	}
	
	@GetMapping("/user/orders")
	public ResponseEntity<ApiResponse> getUserOrdersApi(@RequestParam("id") Integer user_id){
		
		try {
		List<Orderdto> orderdtos =	orderService.getUserOrders(user_id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Orders Success !", orderdtos),
				HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(" Failed !", e.getMessage()),
					HttpStatus.NOT_FOUND);
		}		
	}

}
