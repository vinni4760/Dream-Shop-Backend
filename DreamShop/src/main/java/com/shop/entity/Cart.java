package com.shop.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Double totalAmount;
	@OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<CartItem> cartItems = new HashSet<CartItem>();


	@JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
	
    public void addItem(CartItem item) {
        this.cartItems.add(item);
        item.setCart(this);
        setTotalAmount();
    }

    public void removeItem(CartItem item) {
        this.cartItems.remove(item);
        item.setCart(null);
        setTotalAmount();
    }

	
	public void setTotalAmount() {
	totalAmount =	cartItems.stream()
		.mapToDouble(item->item.getTotalPrice())
		.sum();
	}
}
