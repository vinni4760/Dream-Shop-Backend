package com.shop.service;

public interface ICartItemService {

	  void addItemtocart(Integer cart_id,Integer product_id,Integer quantity);
	  void removeItemtocart(Integer cart_id,Integer product_id);
	  void updateItemQuantity(Integer cart_id,Integer product_id,Integer quantiry);
}
