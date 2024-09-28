package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer quantity;
	private Double unitPrice;
	private Double totalPrice;
	
	 @JsonIgnore
	@ManyToOne
	@JoinColumn(name="cart_id")
	private Cart cart;
	
	 @JsonIgnore
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	 public void setTotalAmount() {
	  Double quantity=this.quantity.doubleValue();
		this.totalPrice = this.unitPrice*quantity;
	}

	 
}
