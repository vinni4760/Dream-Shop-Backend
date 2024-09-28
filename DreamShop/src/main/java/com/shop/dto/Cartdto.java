package com.shop.dto;

import java.util.Set;

import lombok.Data;

@Data
public class Cartdto {

	 private Integer id;
	 private Double totalAmount;
	 private Set<CartItemdto> cartItemdtos;
	 
}
