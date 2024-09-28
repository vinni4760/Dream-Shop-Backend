package com.shop.dto;

import java.util.List;


import lombok.Data;

@Data
public class Userdto {
	
	private String firstName;
	private String lastName;
	private String email;
	private List<Orderdto> orders;
	private Cartdto cartdto;

}
