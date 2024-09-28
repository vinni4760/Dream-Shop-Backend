package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@NoArgsConstructor
@Setter
@Getter
public class OrderItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer quantity;
	private Double price;
	
	@JsonIgnore
	@ManyToOne         //many oderItems to one oder   
	@JoinColumn(name="order_id")
	private Order order;
	
	@JsonIgnore
	@ManyToOne        //many oderItems to one product
	@JoinColumn(name="product_id")
	private Product product;

	public OrderItems(Order order,Product product,Integer quantity,Double price) {
		this.order=order;
		this.product=product;
		this.price=price;
		this.quantity=quantity;
	}
	
}
