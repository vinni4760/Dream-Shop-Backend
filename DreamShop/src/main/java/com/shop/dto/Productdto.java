package com.shop.dto;

import java.util.List;

import com.shop.entity.Category;

import lombok.Data;

@Data
public class Productdto {
	
	private Integer id;
	private String name;
	private String brand;
	private Integer inventory;
	private Double price;
	private String description;
	private Category category;

    private List<Imagedto> images;

}
