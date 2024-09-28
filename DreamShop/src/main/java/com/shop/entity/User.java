package com.shop.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String firstName;
	private String lastName;
	
	private String email;
	private String password;
	
	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
	private Cart cart;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<Order> orders = new HashSet<Order>();
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	 @JoinTable(
		        name = "User_Roles", joinColumns = 
		        @JoinColumn(name="user_id",referencedColumnName = "id"),
		        inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id")
		    )
	private Collection<Roles> roles = new HashSet<Roles>();

}
