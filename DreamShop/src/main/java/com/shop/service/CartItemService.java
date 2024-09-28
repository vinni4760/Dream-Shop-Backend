package com.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.Product;
import com.shop.exceptions.ResourceNotFoundException;
import com.shop.repository.ICartItemRepository;
import com.shop.repository.ICartRepository;

@Service
public class CartItemService implements ICartItemService{

	@Autowired
	private ICartService cartService;
	@Autowired
	private ICartRepository cartRepository;
	@Autowired
	private ICartItemRepository cartItemRepository;
	@Autowired
	private IProductService iProductService;

	@Override
	public void removeItemtocart(Integer cart_id, Integer product_id) {
		
	         Cart cart =	cartService.getCart(cart_id);
	         CartItem cartItem = getItem(cart_id, product_id);
	         cart.removeItem(cartItem);
	         cartRepository.save(cart);
	         
	}

	@Override
	public void updateItemQuantity(Integer cart_id, Integer product_id, Integer quantiry) {
		Cart cart = cartService.getCart(cart_id);
		CartItem cartItem = getItem(cart_id, product_id);
	    cartItem.setQuantity(quantiry);
	    cartItem.setUnitPrice(cartItem.getProduct().getPrice());
	    cartItem.setTotalAmount();
	   Double totalAmount =  cart.getCartItems()
	    .stream()
	    .mapToDouble(item->item.getTotalPrice())
	    .sum();
	    cart.setTotalAmount(totalAmount);
	    cartRepository.save(cart);
	}

	@Override
	public void addItemtocart(Integer cart_id, Integer product_id, Integer quantity) {
                         		
		Cart cart  = cartService.getCart(cart_id);
		Product product = iProductService.getProductByid(product_id);
		CartItem cartItem = cart.getCartItems()
				.stream()
				.filter(item->item.getProduct().getId().equals(product.getId()))//retrive first element from stream that matches given condition
				.findFirst()
				.orElse(new CartItem());
		
		if (cartItem.getId()==null) {
			 cartItem.setCart(cart);
			 cartItem.setProduct(product);		
			 cartItem.setQuantity(quantity);
			 cartItem.setUnitPrice(product.getPrice());
		}
		else
			cartItem.setQuantity(quantity+cartItem.getQuantity());
	      	
		cartItem.setTotalAmount();
		cart.addItem(cartItem);
		cartItemRepository.save(cartItem);
		cartRepository.save(cart);
	}
	
	CartItem getItem(Integer cartid,Integer productid) {
		Cart cart = cartService.getCart(cartid);
		return cart.getCartItems()
				.stream()
				.filter(item->item.getProduct().getId().equals(productid))
				.findFirst()
				.orElseThrow(()->new ResourceNotFoundException("Cart Item Not Found !"));
	}

}
