package com.shop.dto;

import lombok.Data;

@Data
public class CartItemdto {

	 private Integer id;
	 private Integer quantity;
	 private Double unitPrice;
	private Double totalPrice;
	private Productdto productdto;
		
}
