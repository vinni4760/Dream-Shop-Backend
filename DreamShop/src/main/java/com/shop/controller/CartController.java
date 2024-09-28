package com.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.Cartdto;
import com.shop.entity.Cart;
import com.shop.entity.User;
import com.shop.exceptions.ResourceNotFoundException;
import com.shop.response.ApiResponse;
import com.shop.service.ICartService;
import com.shop.service.IUserService;

@RestController
@RequestMapping("/shop/cart")
public class CartController {
	
	@Autowired
	private ICartService cartService;
	
	@Autowired
	private IUserService  userService;
	
	
	@PostMapping("/new")
	public ResponseEntity<ApiResponse> newCartApi(@RequestParam("id") Integer user_id){
		 try {
			 User user = cartService.getAuthenticateUser();
				Integer cartid = cartService.newCartwithUserId(user);
				return new ResponseEntity<ApiResponse>(new ApiResponse("Cart Created !", cartid),
						HttpStatus.CREATED);
} catch (ResourceNotFoundException e) {
	return new ResponseEntity<ApiResponse>(new ApiResponse("Failed !", e.getMessage()),
			HttpStatus.NOT_FOUND);
}
	
	}

	@GetMapping("/mycart/{id}")
	   public ResponseEntity<ApiResponse> getMyCartApi(@PathVariable("id") Integer card_id){
		   try {
        	 	 	  Cart cart = cartService.getCart(card_id);
        	 	 	  Cartdto cartdto = cartService.convertodto(cart);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Cart :", cart),
					HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Failed !", e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	   }
	
	@GetMapping("/totalprice/{id}")
	public ResponseEntity<ApiResponse> getTotalPriceApi(@PathVariable("id") Integer cart_id){
		try {
			Double totalPrice = cartService.getTotalPrice(cart_id);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Total Price :\s", totalPrice),
					HttpStatus.OK);
		}
		catch (ResourceNotFoundException e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Failed !", e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/clear/{id}")
	public ResponseEntity<ApiResponse> clearCartApi(@PathVariable("id") Integer cart_id){
		try {
			cartService.clearCart(cart_id);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Cleared Success !", null),
					HttpStatus.OK);
		}
		catch (ResourceNotFoundException e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Clear Failed !", e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	}
}
