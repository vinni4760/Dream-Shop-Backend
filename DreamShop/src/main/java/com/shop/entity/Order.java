package com.shop.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private LocalDate oderdate;
	private Double totalamount;
	private String oderstatus;
	
	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<OrderItems> orderItems = new HashSet<OrderItems>();

	@JsonIgnore
	 @ManyToOne
	 @JoinColumn(name="user_id")
	 private User user;

}
