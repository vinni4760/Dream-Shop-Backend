package com.shop.dto;


import lombok.Data;

@Data
public class OrderItemdto {
     
	private Integer id;
	private Integer quantity;
	private Double price;
	private String productName;
}
