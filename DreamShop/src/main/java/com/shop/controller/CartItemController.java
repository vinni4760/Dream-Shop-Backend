package com.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.response.ApiResponse;
import com.shop.service.ICartItemService;

@RestController
@RequestMapping("/shop/cartitem")
public class CartItemController {

	 @Autowired
	 private ICartItemService cartItemService;
	
	 @PostMapping("/add")
	    public ResponseEntity<ApiResponse> addItemtoCartApi(@RequestParam Integer cartid,
	    		@RequestParam Integer productid,@RequestParam Integer quantity){
		 try {
			 cartItemService.addItemtocart(cartid, productid, quantity);
			 return new ResponseEntity<ApiResponse>(new ApiResponse("Item Added Success !", null),
						HttpStatus.OK);
		 }
	    	catch (RuntimeException e) {
	    		return new ResponseEntity<ApiResponse>(new ApiResponse("Failed !", e.getMessage()),
						HttpStatus.NOT_FOUND);
			}
	    }
	 
	 @PutMapping("/update")
	   public ResponseEntity<ApiResponse> updateItemtoCartApi(@RequestParam Integer cartid,
	    		@RequestParam Integer productid,@RequestParam Integer quantity){
		 try {
			 cartItemService.updateItemQuantity(cartid, productid, quantity);
			 return new ResponseEntity<ApiResponse>(new ApiResponse("Item Update Success !", null),
						HttpStatus.OK);
		 }
	    	catch (RuntimeException e) {
	    		return new ResponseEntity<ApiResponse>(new ApiResponse("Updation Failed !", e.getMessage()),
						HttpStatus.NOT_FOUND);
			}
	    }
	 
	 @DeleteMapping("/remove")
	   public ResponseEntity<ApiResponse> removeItemtoCartApi(@RequestParam Integer cartid,
	    		@RequestParam Integer productid){
		 try {
			 cartItemService.removeItemtocart(cartid, productid);
			 return new ResponseEntity<ApiResponse>(new ApiResponse("Item Delete Success !", null),
						HttpStatus.OK);
		 }
	    	catch (RuntimeException e) {
	    		return new ResponseEntity<ApiResponse>(new ApiResponse("Failed !", e.getMessage()),
						HttpStatus.NOT_FOUND);
			}
	    }
}
