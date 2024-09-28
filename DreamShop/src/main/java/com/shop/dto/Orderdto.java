package com.shop.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.shop.entity.OrderItems;
import lombok.Data;

@Data
public class Orderdto {
	
	private Integer id;
	private LocalDate oderdate;
	private Double totalamount;
	private String oderstatus;
	private Set<OrderItems> orderItems = new HashSet<OrderItems>();


}
