package com.shop.service;


import java.util.concurrent.atomic.AtomicInteger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.shop.dto.Cartdto;
import com.shop.entity.Cart;
import com.shop.entity.User;
import com.shop.exceptions.ResourceNotFoundException;
import com.shop.repository.ICartItemRepository;
import com.shop.repository.ICartRepository;
import com.shop.repository.IUserRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService implements ICartService{
	
	@Autowired
	private ICartRepository cartRepository;
	
	@Autowired
	private ICartItemRepository cartItemRepository;
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IUserRepository iUserRepository;
	@Autowired
	private ModelMapper mapper;

	private AtomicInteger atomicInteger = new AtomicInteger(1);
 	
	@Override
	public Cart getCart(Integer cart_id) {
		Cart cart = cartRepository.findById(cart_id)
		.orElseThrow(()->new ResourceNotFoundException("Cart Not Found !"));
	       cart.setTotalAmount();
		Double	totalamount = cart.getTotalAmount();
		cart.setTotalAmount(totalamount);
		return cartRepository.save(cart);
	}

	@Override
	public Double getTotalPrice(Integer cart_id) {
		Cart cart = getCart(cart_id);
		return cart.getTotalAmount();
	}


    @Transactional
	@Override
	public void clearCart(Integer cart_id) {
		Cart cart = getCart(cart_id);
		cartItemRepository.deleteAllByCartId(cart_id);
		cart.getCartItems().clear();
		cartRepository.deleteById(cart_id);
	}

	@Override
	public Integer newCart() {
		Integer generatedId = atomicInteger.getAndIncrement();
		 Cart cart = new Cart();
		 cart.setId(generatedId);
		 cartRepository.save(cart);
		return generatedId;
	}

	@Override
	public Cart getCartByUserId(Integer user_id) {
		return cartRepository.findByUserId(user_id);
	}

	@Override
	public Integer newCartwithUserId(User user) {
		Integer generatedId = atomicInteger.getAndIncrement();
		 Cart cart = new Cart();
		 cart.setId(generatedId);
		User user2 = userService.getUserById(user.getId());
		 cart.setUser(user2);
		 cartRepository.save(cart);
		return generatedId;
	}

	@Override
	public Cartdto convertodto(Cart cart) {
		return mapper.map(cart, Cartdto.class);
	}

	@Override
	public User getAuthenticateUser() {
		Authentication authentication = SecurityContextHolder
				.getContext().getAuthentication();
	 String email = 	authentication.getName();
	 return iUserRepository.findByEmail(email);
	 
	}
       
}
