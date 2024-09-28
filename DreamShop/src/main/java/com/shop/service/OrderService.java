package com.shop.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.dto.Orderdto;
import com.shop.entity.Cart;
import com.shop.entity.Order;
import com.shop.entity.OrderItems;
import com.shop.entity.Product;
import com.shop.exceptions.ResourceNotFoundException;
import com.shop.repository.IOrderRepository;
import com.shop.repository.IProductRepository;

@Service
public class OrderService implements IOrderService {

	 @Autowired
	 private IOrderRepository orderRepository;
     @Autowired
     private ICartService cartService;
     @Autowired
     private IProductRepository productRepository;
     
     @Autowired
     private IUserService  userService;
	 
     @Autowired 
     private ModelMapper mapper;
     
	@Override
	public Order placeOrder(Integer user_id) {
    	 Cart cart = cartService.getCartByUserId(user_id);
		 Order order = createOrder(cart);
		 List<OrderItems> orderItems  = 
				 orderItems(order, cart);
		 order.setOrderItems(new HashSet<OrderItems>(orderItems));
    	 order.setTotalamount(calculateTotalAmount(orderItems));
    	 orderRepository.save(order);
    	 cartService.clearCart(cart.getId());
//		 return convertodto(order);
    	 return order;
	}
	
	private Double calculateTotalAmount(List<OrderItems> orderItems) {
		return orderItems.stream()
				.mapToDouble(items->items.getPrice()*items.getQuantity())
				.sum();
	}

	private Order createOrder(Cart cart) {
		
		Order order = new Order();
		order.setUser(cart.getUser());
		order.setOderstatus("PENDING");
		order.setOderdate(LocalDate.now());
		return order;
	}
	
	private List<OrderItems> orderItems(Order order, Cart cart){
		
		return cart.getCartItems().stream()
			    .map(items -> {
			    Product product = 	items.getProduct();
			    product.setInventory(product.getInventory()-items.getQuantity());
			    productRepository.save(product);
			     return new OrderItems(order, product, items.getQuantity(), items.getUnitPrice());	
			    }) 
			    .toList();
		
	}
	public	String getName() {
		return "Vinay";
	}
 
	@Override
	public Orderdto getOrder(Integer order_id) {
		return orderRepository.findById(order_id)
				.map(this::convertodto)
				.orElseThrow(()->new ResourceNotFoundException("Order Not Found !"));
				}

	@Override
	public List<Orderdto> getUserOrders(Integer user_id) {
		userService.getUserById(user_id);
	       List<Order> orderlist = 	orderRepository.findByUserId(user_id);
		return orderlist.stream()
				.map(this::convertodto)
				.toList();
	}
	@Override
	public Orderdto convertodto(Order order) {
		return mapper.map(order, Orderdto.class);
	}
}
