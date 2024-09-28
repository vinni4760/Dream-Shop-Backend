package com.shop.entity;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	private String brand;
	private Integer inventory;
	private String description;
	private Double price;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true )
	private List<Image> image;
	
	

	public Product(String name2, String brand2, Integer inventory2, String description2, Double price2,
			Category category2) {
		this.name=name2;
		this.brand=brand2;
		this.inventory=inventory2;
		this.description=description2;
		this.price=price2;
		this.category=category2;
	}

}
