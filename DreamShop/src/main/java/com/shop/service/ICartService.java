package com.shop.service;

import com.shop.dto.Cartdto;
import com.shop.entity.Cart;
import com.shop.entity.User;

public interface ICartService {

	 Cart getCart(Integer cart_id);
	 Double getTotalPrice(Integer cart_id);
	 void clearCart(Integer cart_id);
	 Integer newCart();
	Cart getCartByUserId(Integer user_id);
	Integer newCartwithUserId(User user);
	Cartdto convertodto(Cart cart);
	User getAuthenticateUser();
}
