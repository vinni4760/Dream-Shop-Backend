package com.shop.request;

import com.shop.entity.Category;

import lombok.Data;

@Data
public class ProductRequest {
    private Long id;
    private String name;
    private String brand;
    private Double price;
    private Integer inventory;
    private String description;
    private Category category;

}
